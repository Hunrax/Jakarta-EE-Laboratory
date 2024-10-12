package pg.eti.jee.movie.controller.api;

import pg.eti.jee.movie.dto.GetMovieResponse;
import pg.eti.jee.movie.dto.GetMoviesResponse;
import pg.eti.jee.movie.dto.PatchMovieRequest;
import pg.eti.jee.movie.dto.PutMovieRequest;

import java.util.UUID;

public interface MovieController {
    GetMoviesResponse getMovies();

    GetMoviesResponse getDirectorMovies(UUID id);

    GetMoviesResponse getUserMovies(UUID id);

    GetMovieResponse getMovie(UUID uuid);

    void putMovie(UUID id, PutMovieRequest request);

    void patchMovie(UUID id, PatchMovieRequest request);

    void deleteMovie(UUID id);
}
