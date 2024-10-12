package pg.eti.jee.datastore.component;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pg.eti.jee.director.entity.Director;
import pg.eti.jee.movie.entity.Movie;
import pg.eti.jee.user.entity.User;
import pg.eti.jee.serialization.component.CloningUtility;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {

    private final Set<Director> directors = new HashSet<>();

    private final Set<Movie> movies = new HashSet<>();

    private final Set<User> users = new HashSet<>();

    private final CloningUtility cloningUtility;

    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    public synchronized List<Director> findAllDirectors() {
        return directors.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createDirector(Director value) throws IllegalArgumentException {
        if (directors.stream().anyMatch(Director -> Director.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The Director id \"%s\" is not unique".formatted(value.getId()));
        }
        directors.add(cloningUtility.clone(value));
    }

    public synchronized List<Movie> findAllMovies() {
        return movies.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createMovie(Movie value) throws IllegalArgumentException {
        if (movies.stream().anyMatch(Movie -> Movie.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The Movie id \"%s\" is not unique".formatted(value.getId()));
        }
        Movie entity = cloneWithRelationships(value);
        movies.add(entity);
    }

    public synchronized void updateMovie(Movie value) throws IllegalArgumentException {
        Movie entity = cloneWithRelationships(value);
        if (movies.removeIf(Movie -> Movie.getId().equals(value.getId()))) {
            movies.add(entity);
        } else {
            throw new IllegalArgumentException("The Movie with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteMovie(UUID id) throws IllegalArgumentException {
        if (!movies.removeIf(Movie -> Movie.getId().equals(id))) {
            throw new IllegalArgumentException("The Movie with id \"%s\" does not exist".formatted(id));
        }
    }

    public synchronized List<User> findAllUsers() {
        return users.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createUser(User value) throws IllegalArgumentException {
        if (users.stream().anyMatch(user -> user.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(value.getId()));
        }
        users.add(cloningUtility.clone(value));
    }

    public synchronized void updateUser(User value) throws IllegalArgumentException {
        if (users.removeIf(user -> user.getId().equals(value.getId()))) {
            users.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The user with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteUser(UUID id) throws IllegalArgumentException{
        if(!users.removeIf(user -> user.getId().equals(id))){
            throw new IllegalArgumentException("The user with id \"%s\" does not exist".formatted(id));
        }
    }

    private Movie cloneWithRelationships(Movie value) {
        Movie entity = cloningUtility.clone(value);

        if (entity.getUser() != null) {
            entity.setUser(users.stream()
                    .filter(user -> user.getId().equals(value.getUser().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No user with id \"%s\".".formatted(value.getUser().getId()))));
        }

        if (entity.getDirector() != null) {
            entity.setDirector(directors.stream()
                    .filter(Director -> Director.getId().equals(value.getDirector().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No Director with id \"%s\".".formatted(value.getDirector().getId()))));
        }

        return entity;
    }
}
