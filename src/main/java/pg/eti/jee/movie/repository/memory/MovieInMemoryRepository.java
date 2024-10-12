package pg.eti.jee.movie.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pg.eti.jee.datastore.component.DataStore;
import pg.eti.jee.director.entity.Director;
import pg.eti.jee.movie.entity.Movie;
import pg.eti.jee.movie.repository.api.MovieRepository;
import pg.eti.jee.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class MovieInMemoryRepository implements MovieRepository {

    private final DataStore store;

    @Inject
    public MovieInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Movie> find(UUID id) {
        return store.findAllMovies().stream()
                .filter(character -> character.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Movie> findAll() {
        return store.findAllMovies();
    }

    @Override
    public void create(Movie entity) {
        store.createMovie(entity);
    }

    @Override
    public void delete(UUID id) {
        store.deleteMovie(id);
    }

    @Override
    public void update(Movie entity) {
        store.updateMovie(entity);
    }

    @Override
    public Optional<Movie> findByIdAndUser(UUID id, User user) {
        return store.findAllMovies().stream()
                .filter(character -> character.getUser().equals(user))
                .filter(character -> character.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Movie> findAllByUser(User user) {
        return store.findAllMovies().stream()
                .filter(character -> user.equals(character.getUser()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> findAllByDirector(Director director) {
        return store.findAllMovies().stream()
                .filter(character -> director.equals(character.getDirector()))
                .collect(Collectors.toList());
    }
}
