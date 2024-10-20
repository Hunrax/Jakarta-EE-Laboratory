package pg.eti.jee.movie.model.function;

import pg.eti.jee.movie.entity.Movie;
import pg.eti.jee.movie.model.MoviesModel;

import java.util.List;
import java.util.function.Function;

public class MoviesToModelFunction implements Function<List<Movie>, MoviesModel> {

    @Override
    public MoviesModel apply(List<Movie> entity) {
        return MoviesModel.builder()
                .movies(entity.stream()
                        .map(movie -> MoviesModel.Movie.builder()
                                .id(movie.getId())
                                .title(movie.getTitle())
                                .build())
                        .toList())
                .build();
    }

}
