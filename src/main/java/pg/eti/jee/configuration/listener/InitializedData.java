package pg.eti.jee.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;
import pg.eti.jee.user.entity.User;
import pg.eti.jee.user.entity.UserRoles;
import pg.eti.jee.user.service.UserService;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@WebListener
public class InitializedData implements ServletContextListener {

    private UserService userService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        userService = (UserService) event.getServletContext().getAttribute("userService");
        init();
    }

    @SneakyThrows
    private void init() {
        User admin = User.builder()
                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                .login("admin")
                .name("Administrator")
                .surname("Administratowicz")
                .birthDate(LocalDate.of(2000, 10, 10))
                .password("adminadmin")
                .roles(List.of(UserRoles.ADMIN, UserRoles.USER))
                .portrait(getResourceAsByteArray("../avatars/green.jpg"))
                .build();

        User radek = User.builder()
                .id(UUID.fromString("81e1c2a9-7f57-439b-b53d-6db88b071e4e"))
                .login("hunrax")
                .name("Radek")
                .surname("Gajewski")
                .birthDate(LocalDate.of(2003, 1, 20))
                .password("useruser")
                .roles(List.of(UserRoles.ADMIN))
                .portrait(getResourceAsByteArray("../avatars/blue.jpg"))
                .build();

        User anonim = User.builder()
                .id(UUID.fromString("ed6cfb2a-cad7-47dd-9b56-9d1e3c7a4197"))
                .login("Anonim")
                .name("Gal")
                .surname("Anonim")
                .birthDate(LocalDate.of(1212, 12, 12))
                .password("AnonimowyAnonim")
                .roles(List.of(UserRoles.USER))
                .portrait(getResourceAsByteArray("../avatars/red.jpg"))
                .build();

        User studentPG = User.builder()
                .id(UUID.fromString("f5875513-bf7b-4ae1-b8a5-5b70a1b90e76"))
                .login("student")
                .name("Pawel")
                .surname("Nowak")
                .birthDate(LocalDate.of(2001, 6, 16))
                .password("Pawel123")
                .roles(List.of(UserRoles.USER))
                .portrait(getResourceAsByteArray("../avatars/blackscreen.jpg"))
                .build();

        User janusz = User.builder()
                .id(UUID.fromString("5d1da2ae-6a14-4b6d-8b4f-d117867118d4"))
                .login("JanuszPL")
                .name("Janusz")
                .surname("Nosacz")
                .birthDate(LocalDate.of(1975, 4, 15))
                .password("PolskaGurom")
                .roles(List.of(UserRoles.USER))
                .portrait(getResourceAsByteArray("../avatars/blackscreen.jpg"))
                .build();

        userService.create(admin);
        userService.create(radek);
        userService.create(anonim);
        userService.create(studentPG);
        userService.create(janusz);
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            if (is != null) {
                return is.readAllBytes();
            } else {
                throw new IllegalStateException("Unable to get resource %s".formatted(name));
            }
        }
    }

}
