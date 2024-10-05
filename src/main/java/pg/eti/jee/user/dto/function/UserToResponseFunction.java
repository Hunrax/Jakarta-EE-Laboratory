package pg.eti.jee.user.dto.function;

import pg.eti.jee.user.dto.GetUserResponse;
import pg.eti.jee.user.entity.User;

import java.util.function.Function;

public class UserToResponseFunction implements Function<User, GetUserResponse> {

    @Override
    public GetUserResponse apply(User user) {
        return GetUserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .name(user.getName())
                .birthDate(user.getBirthDate())
                .surname(user.getSurname())
                .build();
    }

}
