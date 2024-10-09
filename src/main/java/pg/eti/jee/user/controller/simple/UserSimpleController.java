package pg.eti.jee.user.controller.simple;

import pg.eti.jee.component.DtoFunctionFactory;
import pg.eti.jee.user.controller.api.UserController;
import pg.eti.jee.user.dto.GetUserResponse;
import pg.eti.jee.user.dto.GetUsersResponse;
import pg.eti.jee.user.dto.PatchUserRequest;
import pg.eti.jee.user.dto.PutUserRequest;
import pg.eti.jee.user.entity.User;
import pg.eti.jee.user.service.UserService;
import pg.eti.jee.controller.servlet.exception.NotFoundException;
import pg.eti.jee.controller.servlet.exception.BadRequestException;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

public class UserSimpleController implements UserController {

    private final UserService service;

    private final DtoFunctionFactory factory;

    public UserSimpleController(UserService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
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
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
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
                return service.getPortrait(id);
        }
        else
            throw new NotFoundException();
    }

    @Override
    public void putUserPortrait(UUID id, InputStream portrait) {
        service.find(id).ifPresentOrElse(
                entity -> service.updatePortrait(id, portrait),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteUserPortrait(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.deletePortrait(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
