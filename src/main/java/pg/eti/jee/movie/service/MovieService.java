package pg.eti.jee.movie.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.NoArgsConstructor;
import pg.eti.jee.director.repository.api.DirectorRepository;
import pg.eti.jee.movie.entity.Movie;
import pg.eti.jee.movie.repository.api.MovieRepository;
import pg.eti.jee.user.entity.User;
import pg.eti.jee.user.entity.UserRoles;
import pg.eti.jee.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class MovieService {

    private final MovieRepository movieRepository;

    private final DirectorRepository directorRepository;

    private final UserRepository userRepository;

    private final SecurityContext securityContext;

    @Inject
    public MovieService(MovieRepository movieRepository, DirectorRepository directorRepository, UserRepository userRepository,
                        @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;
        this.userRepository = userRepository;
        this.securityContext = securityContext;
    }
    @RolesAllowed(UserRoles.USER)
    public Optional<Movie> find(UUID id) {
        return movieRepository.find(id);
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<Movie> find(User user, UUID id) {
        return movieRepository.findByIdAndUser(id, user);
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<Movie> findForCallerPrincipal(UUID id) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return find(id);
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return find(user, id);
    }

    @RolesAllowed(UserRoles.USER)
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @RolesAllowed(UserRoles.USER)
    public List<Movie> findAll(User user) {
        return movieRepository.findAllByUser(user);
    }

    @RolesAllowed(UserRoles.USER)
    public List<Movie> findAllForCallerPrincipal() {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return findAll();
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return findAll(user);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void create(Movie movie) {
        if (movieRepository.find(movie.getId()).isPresent()) {
            throw new IllegalArgumentException("Movie already exists.");
        }
        if (directorRepository.find(movie.getDirector().getId()).isEmpty()) {
            throw new IllegalArgumentException("Director does not exist.");
        }
        movieRepository.create(movie);

        directorRepository.find(movie.getDirector().getId())
                .ifPresent(director -> director.getMovies().add(movie));
//        userRepository.find(movie.getUser().getId())
//                .ifPresent(user -> user.getMovies().add(movie));
    }

    @RolesAllowed(UserRoles.USER)
    public void createForCallerPrincipal(Movie movie) {
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);

        movie.setUser(user);
        create(movie);
    }

    @RolesAllowed(UserRoles.USER)
    public void update(Movie movie) {
        checkAdminRoleOrOwner(movieRepository.find(movie.getId()));
        movieRepository.update(movie);
    }

    @RolesAllowed(UserRoles.USER)
    public void delete(UUID id) {
        checkAdminRoleOrOwner(movieRepository.find(id));
        movieRepository.delete(id);
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<List<Movie>> findAllByDirector(UUID id) {
        return directorRepository.find(id)
                .map(movieRepository::findAllByDirector);
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<List<Movie>> findAllByUser(UUID id) {
        Optional<User> user = userRepository.find(id);

        if (user.isPresent()) {
            List<Movie> movies = movieRepository.findAllByUser(user.get());
            movies.forEach(movie -> checkAdminRoleOrOwner(Optional.of(movie)));

            return Optional.of(movies);
        }
        return Optional.empty();
    }

    private void checkAdminRoleOrOwner(Optional<Movie> movie) throws EJBAccessException {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return;
        }
        if (securityContext.isCallerInRole(UserRoles.USER)
                && movie.isPresent()
                && movie.get().getUser().getLogin().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new EJBAccessException("Caller not authorized.");
    }
}
