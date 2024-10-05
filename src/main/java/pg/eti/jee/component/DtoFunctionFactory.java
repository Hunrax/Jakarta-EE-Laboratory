package pg.eti.jee.component;

import pg.eti.jee.user.dto.function.*;

public class DtoFunctionFactory {
    public RequestToUserFunction requestToUser() {
        return new RequestToUserFunction();
    }
    
    public UpdateUserWithRequestFunction updateUser() {
        return new UpdateUserWithRequestFunction();
    }
    
    public UpdateUserPasswordWithRequestFunction updateUserPassword() {
        return new UpdateUserPasswordWithRequestFunction();
    }
    
    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }
    
    public UserToResponseFunction userToResponse() {
        return new UserToResponseFunction();
    }

}
