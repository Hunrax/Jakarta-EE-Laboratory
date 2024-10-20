package pg.eti.jee.movie.model.function;


import pg.eti.jee.movie.entity.Movie;
import pg.eti.jee.movie.model.MovieEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class MovieToEditModelFunction implements Function<Movie, MovieEditModel>, Serializable {

    @Override
    public MovieEditModel apply(Movie entity) {
        return MovieEditModel.builder()
                .title(entity.getTitle())
                .runningTime(entity.getRunningTime())
                .releaseDate(entity.getReleaseDate())
                .build();
    }

}
