package pg.eti.jee.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pg.eti.jee.crypto.component.Pbkdf2PasswordHash;
import pg.eti.jee.datastore.component.DataStore;
import pg.eti.jee.user.repository.api.UserRepository;
import pg.eti.jee.user.repository.memory.UserInMemoryRepository;
import pg.eti.jee.user.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebListener
public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataSource = (DataStore) event.getServletContext().getAttribute("datasource");

        UserRepository userRepository = new UserInMemoryRepository(dataSource);

        String portraitPath = event.getServletContext().getInitParameter("portraitPath");

        try {
            Files.createDirectories(Path.of(portraitPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        event.getServletContext().setAttribute("userService", new UserService(userRepository, new Pbkdf2PasswordHash(), portraitPath));
    }

}
