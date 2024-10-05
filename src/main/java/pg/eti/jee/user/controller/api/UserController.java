package pg.eti.jee.user.controller.api;

import pg.eti.jee.user.dto.GetUserResponse;
import pg.eti.jee.user.dto.GetUsersResponse;
import pg.eti.jee.user.dto.PatchUserRequest;
import pg.eti.jee.user.dto.PutUserRequest;

import java.io.InputStream;
import java.util.UUID;

public interface UserController {
    GetUserResponse getUser(UUID id);

    GetUsersResponse getUsers();

    void updateUser(UUID id, PatchUserRequest patchUserRequest);

    void createUser(UUID id, PutUserRequest putUserRequest);

    void deleteUser(UUID id);

    byte[] getUserPortrait(UUID id);

    void putUserPortrait(UUID id, InputStream portrait);

    void deleteUserPortrait(UUID id);

}
