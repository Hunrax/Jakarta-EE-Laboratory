package pg.eti.jee.movie.dto.function;

import pg.eti.jee.director.entity.Director;
import pg.eti.jee.movie.dto.PutMovieRequest;
import pg.eti.jee.movie.entity.Movie;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToMovieFunction implements BiFunction<UUID, PutMovieRequest, Movie> {

    @Override
    public Movie apply(UUID id, PutMovieRequest request) {
        return Movie.builder()
                .id(id)
                .title(request.getTitle())
                .releaseDate(request.getReleaseDate())
                .director(Director.builder()
                        .id(request.getDirectorId())
                        .build())
                .runningTime(request.getRunningTime())
                .build();
    }
}
