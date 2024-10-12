package pg.eti.jee.movie.dto.function;

import pg.eti.jee.movie.dto.GetMoviesResponse;
import pg.eti.jee.movie.entity.Movie;

import java.util.List;
import java.util.function.Function;

public class MoviesToResponseFunction implements Function<List<Movie>, GetMoviesResponse> {

    @Override
    public GetMoviesResponse apply(List<Movie> entities) {
        return GetMoviesResponse.builder()
                .movies(entities.stream()
                        .map(movie -> GetMoviesResponse.Movie.builder()
                                .id(movie.getId())
                                .title(movie.getTitle())
                                .build())
                        .toList())
                .build();
    }
}
