package pg.eti.jee.configuration.listener;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import pg.eti.jee.director.entity.Director;
import pg.eti.jee.director.service.DirectorService;
import pg.eti.jee.movie.entity.Movie;
import pg.eti.jee.movie.service.MovieService;
import pg.eti.jee.user.entity.User;
import pg.eti.jee.user.entity.UserRoles;
import pg.eti.jee.user.service.UserService;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class InitializedData {

    private final MovieService movieService;

    private final UserService userService;

    private final DirectorService directorService;

    private final RequestContextController requestContextController;

    private String portraitPath;

    @Inject
    public InitializedData(
            MovieService movieService,
            UserService userService,
            DirectorService directorService,
            RequestContextController requestContextController
    ) {
        this.movieService = movieService;
        this.userService = userService;
        this.directorService = directorService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    @SneakyThrows
    private void init() {
        requestContextController.activate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        Files.write(Path.of("userPortrait/c4804e0f-769e-4ab9-9ebe-0578fb4f00a6.jpg"), getResourceAsByteArray("../avatars/Admin.jpg"));
        Files.write(Path.of("userPortrait/81e1c2a9-7f57-439b-b53d-6db88b071e4e.jpg"), getResourceAsByteArray("../avatars/Hunrax.jpg"));
        Files.write(Path.of("userPortrait/ed6cfb2a-cad7-47dd-9b56-9d1e3c7a4197.jpg"), getResourceAsByteArray("../avatars/Anonim.jpg"));
        Files.write(Path.of("userPortrait/f5875513-bf7b-4ae1-b8a5-5b70a1b90e76.jpg"), getResourceAsByteArray("../avatars/StudentPG.jpg"));
        Files.write(Path.of("userPortrait/5d1da2ae-6a14-4b6d-8b4f-d117867118d4.jpg"), getResourceAsByteArray("../avatars/JanuszPL.jpg"));

        User admin = User.builder()
                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                .login("admin")
                .name("Administrator")
                .surname("Administratowicz")
                .birthDate(LocalDate.of(2000, 10, 10))
                .password("adminadmin")
                .roles(List.of(UserRoles.ADMIN, UserRoles.USER))
                .portrait("userPortrait/c4804e0f-769e-4ab9-9ebe-0578fb4f00a6.jpg")
                .build();

        User radek = User.builder()
                .id(UUID.fromString("81e1c2a9-7f57-439b-b53d-6db88b071e4e"))
                .login("hunrax")
                .name("Radek")
                .surname("Gajewski")
                .birthDate(LocalDate.of(2003, 1, 20))
                .password("useruser")
                .roles(List.of(UserRoles.ADMIN))
                .portrait("userPortrait/81e1c2a9-7f57-439b-b53d-6db88b071e4e.jpg")
                .build();

        User anonim = User.builder()
                .id(UUID.fromString("ed6cfb2a-cad7-47dd-9b56-9d1e3c7a4197"))
                .login("Anonim")
                .name("Gal")
                .surname("Anonim")
                .birthDate(LocalDate.of(1212, 12, 12))
                .password("AnonimowyAnonim")
                .roles(List.of(UserRoles.USER))
                .portrait("userPortrait/ed6cfb2a-cad7-47dd-9b56-9d1e3c7a4197.jpg")
                .build();

        User studentPG = User.builder()
                .id(UUID.fromString("f5875513-bf7b-4ae1-b8a5-5b70a1b90e76"))
                .login("student")
                .name("Pawel")
                .surname("Nowak")
                .birthDate(LocalDate.of(2001, 6, 16))
                .password("Pawel123")
                .roles(List.of(UserRoles.USER))
                .portrait("userPortrait/f5875513-bf7b-4ae1-b8a5-5b70a1b90e76.jpg")
                .build();

        User janusz = User.builder()
                .id(UUID.fromString("5d1da2ae-6a14-4b6d-8b4f-d117867118d4"))
                .login("JanuszPL")
                .name("Janusz")
                .surname("Nosacz")
                .birthDate(LocalDate.of(1975, 4, 15))
                .password("PolskaGurom")
                .roles(List.of(UserRoles.USER))
                .portrait("userPortrait/5d1da2ae-6a14-4b6d-8b4f-d117867118d4.jpg")
                .build();

        userService.create(admin);
        userService.create(radek);
        userService.create(anonim);
        userService.create(studentPG);
        userService.create(janusz);

        Director peterJackson = Director.builder()
                .id(UUID.fromString("11111111-1111-1111-1111-d117867118e3"))
                .name("Peter")
                .surname("Jackson")
                .oscarAwards(3)
                .birthDate(LocalDate.parse("31.10.1961", formatter))
                .build();

        Director quentinTarantino = Director.builder()
                .id(UUID.fromString("22222222-2222-2222-2222-d117867118e3"))
                .name("Quentin")
                .surname("Tarantino")
                .oscarAwards(2)
                .birthDate(LocalDate.parse("27.03.1963", formatter))
                .build();

        directorService.create(peterJackson);
        directorService.create(quentinTarantino);

        Movie lotr1 = Movie.builder()
                .id(UUID.fromString("5d1da2ae-6a14-4b6d-8b4f-d117867118e3"))
                .title("The Lord of the Rings: Fellowship of the Ring")
                .releaseDate(LocalDate.parse("10.12.2001", formatter))
                .runningTime(178)
                .director(peterJackson)
                .user(radek)
                .build();

        Movie lotr2 = Movie.builder()
                .id(UUID.fromString("6d1da2ae-3333-4444-5555-d117867118e3"))
                .title("The Lord of the Rings: The Two Towers")
                .releaseDate(LocalDate.parse("05.12.2002", formatter))
                .runningTime(179)
                .director(peterJackson)
                .user(radek)
                .build();

        Movie lotr3 = Movie.builder()
                .id(UUID.fromString("7d1da2ae-1234-2345-1234-d117867118e3"))
                .title("The Lord of the Rings: Return of the King")
                .releaseDate(LocalDate.parse("17.12.2003", formatter))
                .runningTime(201)
                .director(peterJackson)
                .user(admin)
                .build();

        Movie pulpFiction = Movie.builder()
                .id(UUID.fromString("1d1da2a4-1122-3344-5566-d117867118e3"))
                .title("Pulp Fiction")
                .releaseDate(LocalDate.parse("14.10.1994", formatter))
                .runningTime(154)
                .director(quentinTarantino)
                .user(studentPG)
                .build();

        movieService.create(lotr1);
        movieService.create(lotr2);
        movieService.create(lotr3);
        movieService.create(pulpFiction);

        requestContextController.deactivate();
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
