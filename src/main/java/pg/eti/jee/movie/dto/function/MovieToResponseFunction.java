package pg.eti.jee.movie.dto.function;

import pg.eti.jee.movie.dto.GetMovieResponse;
import pg.eti.jee.movie.entity.Movie;

import java.util.function.Function;

public class MovieToResponseFunction implements Function<Movie, GetMovieResponse> {

    @Override
    public GetMovieResponse apply(Movie entity) {
        return GetMovieResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .releaseDate(entity.getReleaseDate())
                .runningTime(entity.getRunningTime())
                .version(entity.getVersion())
                .director(GetMovieResponse.Director.builder()
                        .id(entity.getDirector().getId())
                        .name(entity.getDirector().getName())
                        .surname(entity.getDirector().getSurname())
                        .build())
                .build();
    }
}
