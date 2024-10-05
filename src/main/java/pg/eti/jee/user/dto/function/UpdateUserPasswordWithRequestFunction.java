package pg.eti.jee.user.dto.function;

import pg.eti.jee.user.dto.PutPasswordRequest;
import pg.eti.jee.user.entity.User;

import java.util.function.BiFunction;

public class UpdateUserPasswordWithRequestFunction implements BiFunction<User, PutPasswordRequest, User> {

    @Override
    public User apply(User entity, PutPasswordRequest request) {
        return User.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .name(entity.getName())
                .birthDate(entity.getBirthDate())
                .surname(entity.getSurname())
                .password(request.getPassword())
                .build();
    }

}
