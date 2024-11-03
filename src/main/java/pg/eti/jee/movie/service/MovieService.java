package pg.eti.jee.movie.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import pg.eti.jee.director.repository.api.DirectorRepository;
import pg.eti.jee.movie.entity.Movie;
import pg.eti.jee.movie.repository.api.MovieRepository;
import pg.eti.jee.user.entity.User;
import pg.eti.jee.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class MovieService {

    private final MovieRepository movieRepository;

    private final DirectorRepository directorRepository;

    private final UserRepository userRepository;

    @Inject
    public MovieService(MovieRepository movieRepository, DirectorRepository directorRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;
        this.userRepository = userRepository;
    }
    public Optional<Movie> find(UUID id) {
        return movieRepository.find(id);
    }

    public Optional<Movie> find(User user, UUID id) {
        return movieRepository.findByIdAndUser(id, user);
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public List<Movie> findAll(User user) {
        return movieRepository.findAllByUser(user);
    }

    @Transactional
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

    @Transactional
    public void update(Movie movie) {
        movieRepository.update(movie);
    }

    @Transactional
    public void delete(UUID id) {
        movieRepository.delete(id);
    }

    public Optional<List<Movie>> findAllByDirector(UUID id) {
        return directorRepository.find(id)
                .map(movieRepository::findAllByDirector);
    }

    public Optional<List<Movie>> findAllByUser(UUID id) {
        return userRepository.find(id)
                .map(movieRepository::findAllByUser);
    }
}
