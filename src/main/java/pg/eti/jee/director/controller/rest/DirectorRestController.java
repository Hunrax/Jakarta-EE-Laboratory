package pg.eti.jee.director.controller.rest;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pg.eti.jee.component.DtoFunctionFactory;
import pg.eti.jee.director.controller.api.DirectorController;
import pg.eti.jee.director.dto.GetDirectorResponse;
import pg.eti.jee.director.dto.GetDirectorsResponse;
import pg.eti.jee.director.dto.PatchDirectorRequest;
import pg.eti.jee.director.dto.PutDirectorRequest;
import pg.eti.jee.director.service.DirectorService;
import pg.eti.jee.movie.controller.api.MovieController;
import pg.eti.jee.movie.dto.PatchMovieRequest;
import pg.eti.jee.movie.dto.PutMovieRequest;

import java.util.UUID;

@Path("")
public class DirectorRestController implements DirectorController {

    private final DirectorService service;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public DirectorRestController(DirectorService service, DtoFunctionFactory factory,
                                  @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetDirectorsResponse getDirectors() {
        return factory.directorsToResponse().apply(service.findAll());
    }

    @Override
    public GetDirectorResponse getDirector(UUID id) {
        return service.find(id)
                .map(factory.directorToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putDirector(UUID id, PutDirectorRequest request) {
        try {
            service.create(factory.requestToDirector().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(DirectorController.class, "getDirector")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        }
        catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchDirector(UUID id, PatchDirectorRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateDirector().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteDirector(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
