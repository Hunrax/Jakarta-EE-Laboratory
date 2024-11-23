package pg.eti.jee.user.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pg.eti.jee.component.ModelFunctionFactory;
import pg.eti.jee.user.model.UsersModel;
import pg.eti.jee.user.service.UserService;

@RequestScoped
@Named
public class UserList {

    private final UserService service;

    private UsersModel users;

    private final ModelFunctionFactory factory;

    @Inject
    public UserList(UserService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public UsersModel getUsers() {
        if (users == null) {
            users = factory.usersToModel().apply(service.findAll());
        }
        return users;
    }

    public void deleteAction(UsersModel.User user) {
        service.delete(user.getId());
        users = null;
    }
}
