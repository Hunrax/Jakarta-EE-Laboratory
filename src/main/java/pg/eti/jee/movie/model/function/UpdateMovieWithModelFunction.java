package pg.eti.jee.movie.model.function;

import lombok.SneakyThrows;
import pg.eti.jee.movie.entity.Movie;
import pg.eti.jee.movie.model.MovieEditModel;
import pg.eti.jee.user.entity.User;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateMovieWithModelFunction implements BiFunction<Movie, MovieEditModel, Movie>, Serializable {

    @Override
    @SneakyThrows
    public Movie apply(Movie entity, MovieEditModel request) {
        return Movie.builder()
                .id(entity.getId())
                .title(request.getTitle())
                .runningTime(request.getRunningTime())
                .releaseDate(request.getReleaseDate())
                .director(entity.getDirector())
                .user(User.builder()
                        .id(request.getUser().getId())
                        .build())
                .build();
    }

}
