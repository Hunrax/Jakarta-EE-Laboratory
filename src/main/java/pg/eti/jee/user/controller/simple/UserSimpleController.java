package pg.eti.jee.user.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;
import pg.eti.jee.component.DtoFunctionFactory;
import pg.eti.jee.movie.controller.api.MovieController;
import pg.eti.jee.user.controller.api.UserController;
import pg.eti.jee.user.dto.GetUserResponse;
import pg.eti.jee.user.dto.GetUsersResponse;
import pg.eti.jee.user.dto.PatchUserRequest;
import pg.eti.jee.user.dto.PutUserRequest;
import pg.eti.jee.user.entity.User;
import pg.eti.jee.user.service.PortraitService;
import pg.eti.jee.user.service.UserService;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
public class UserSimpleController implements UserController {

    private final UserService service;

    private final PortraitService portraitService;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public UserSimpleController(UserService service, PortraitService portraitService, DtoFunctionFactory factory,
                                @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.service = service;
        this.portraitService = portraitService;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetUserResponse getUser(UUID id) {
        return service.find(id)
                .map(factory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetUsersResponse getUsers() {
        return factory.usersToResponse().apply(service.findAll());
    }

    @Override
    public void updateUser(UUID id, PatchUserRequest patchUserRequest) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateUser().apply(entity, patchUserRequest)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void createUser(UUID id, PutUserRequest putUserRequest) {
        try {
            service.create(factory.requestToUser().apply(id, putUserRequest));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(UserController.class, "getUser")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        }
        catch (TransactionalException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
        }
    }

    @Override
    public void deleteUser(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public byte[] getUserPortrait(UUID id) {
        Optional<User> u = service.find(id);
        if(u.isPresent()){
            if(u.get().getPortrait() == null)
                throw new NotFoundException();
            else
                return portraitService.getPortrait(id);
        }
        else
            throw new NotFoundException();
    }

    @Override
    public void putUserPortrait(UUID id, InputStream portrait) {
        service.find(id).ifPresentOrElse(
                entity -> portraitService.updatePortrait(id, portrait),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteUserPortrait(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> portraitService.deletePortrait(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
