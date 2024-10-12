package pg.eti.jee.movie.repository.api;

import pg.eti.jee.director.entity.Director;
import pg.eti.jee.movie.entity.Movie;
import pg.eti.jee.repository.api.Repository;
import pg.eti.jee.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MovieRepository extends Repository<Movie, UUID> {

    Optional<Movie> findByIdAndUser(UUID id, User user);

    List<Movie> findAllByUser(User user);

    List<Movie> findAllByDirector(Director director);

}
