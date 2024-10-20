package pg.eti.jee.movie.model.function;

import lombok.SneakyThrows;
import pg.eti.jee.movie.entity.Movie;
import pg.eti.jee.movie.model.MovieEditModel;

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
                .build();
    }

}
