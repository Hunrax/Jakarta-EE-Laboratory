package pg.eti.jee.movie.dto.function;

import pg.eti.jee.director.entity.Director;
import pg.eti.jee.function.TriFunction;
import pg.eti.jee.movie.dto.PutMovieRequest;
import pg.eti.jee.movie.entity.Movie;

import java.util.UUID;

public class RequestToMovieFunction implements TriFunction<UUID, UUID, PutMovieRequest, Movie> {

    @Override
    public Movie apply(UUID directorId, UUID id, PutMovieRequest request) {
        return Movie.builder()
                .id(id)
                .title(request.getTitle())
                .releaseDate(request.getReleaseDate())
                .director(Director.builder()
                        .id(directorId)
                        .build())
                .runningTime(request.getRunningTime())
                .build();
    }
}
