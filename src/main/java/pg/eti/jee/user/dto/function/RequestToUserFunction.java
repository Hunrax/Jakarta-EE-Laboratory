package pg.eti.jee.user.dto.function;

import java.util.UUID;
import java.util.function.BiFunction;

import pg.eti.jee.user.dto.PutUserRequest;
import pg.eti.jee.user.entity.User;

public class RequestToUserFunction implements BiFunction<UUID, PutUserRequest, User> {

    @Override
    public User apply(UUID id, PutUserRequest request) {
        return User.builder()
                .id(id)
                .login(request.getLogin())
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .surname(request.getSurname())
                .password(request.getPassword())
                .build();
    }

}
