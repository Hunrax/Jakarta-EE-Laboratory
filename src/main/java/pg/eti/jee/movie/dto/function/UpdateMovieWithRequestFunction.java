package pg.eti.jee.movie.dto.function;

import pg.eti.jee.movie.dto.PatchMovieRequest;
import pg.eti.jee.movie.entity.Movie;

import java.util.function.BiFunction;

public class UpdateMovieWithRequestFunction implements BiFunction<Movie, PatchMovieRequest, Movie> {

    @Override
    public Movie apply(Movie entity, PatchMovieRequest request) {
        return Movie.builder()
                .id(entity.getId())
                .title(request.getTitle())
                .releaseDate(request.getReleaseDate())
                .director(entity.getDirector())
                .user(entity.getUser())
                .runningTime(request.getRunningTime())
                .build();
    }
}
