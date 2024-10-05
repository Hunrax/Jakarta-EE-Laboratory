package pg.eti.jee.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pg.eti.jee.component.DtoFunctionFactory;
import pg.eti.jee.user.controller.simple.UserSimpleController;
import pg.eti.jee.user.service.UserService;

@WebListener//using annotation does not allow configuring order
public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        UserService userService = (UserService) event.getServletContext().getAttribute("userService");

        event.getServletContext().setAttribute("userController", new UserSimpleController(
                userService,
                new DtoFunctionFactory()
        ));
    }
}
