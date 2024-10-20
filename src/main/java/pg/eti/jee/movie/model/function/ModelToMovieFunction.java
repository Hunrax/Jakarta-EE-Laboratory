package pg.eti.jee.movie.model.function;

import lombok.SneakyThrows;
import pg.eti.jee.director.entity.Director;
import pg.eti.jee.movie.entity.Movie;
import pg.eti.jee.movie.model.MovieCreateModel;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToMovieFunction implements Function<MovieCreateModel, Movie>, Serializable {

    @Override
    @SneakyThrows
    public Movie apply(MovieCreateModel model) {
        return Movie.builder()
                .id(model.getId())
                .title(model.getTitle())
                .releaseDate(model.getReleaseDate())
                .runningTime(model.getRunningTime())
                .director(Director.builder()
                        .id(model.getDirector().getId())
                        .build())
                .build();
    }
}
