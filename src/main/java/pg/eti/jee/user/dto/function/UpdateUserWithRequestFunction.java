package pg.eti.jee.user.dto.function;

import pg.eti.jee.user.dto.PatchUserRequest;
import pg.eti.jee.user.entity.User;

import java.util.function.BiFunction;

public class UpdateUserWithRequestFunction implements BiFunction<User, PatchUserRequest, User> {

    @Override
    public User apply(User entity, PatchUserRequest request) {
        return User.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .surname(request.getSurname())
                .password(entity.getPassword())
                .build();
    }

}
