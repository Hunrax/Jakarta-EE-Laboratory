package pg.eti.jee.movie.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pg.eti.jee.component.DtoFunctionFactory;
import pg.eti.jee.controller.servlet.exception.BadRequestException;
import pg.eti.jee.movie.controller.api.MovieController;
import pg.eti.jee.movie.dto.GetMovieResponse;
import pg.eti.jee.movie.dto.GetMoviesResponse;
import pg.eti.jee.movie.dto.PatchMovieRequest;
import pg.eti.jee.movie.dto.PutMovieRequest;
import pg.eti.jee.movie.service.MovieService;
import pg.eti.jee.controller.servlet.exception.NotFoundException;

import java.util.UUID;

@RequestScoped
public class MovieSimpleController implements MovieController {

    private final MovieService service;

    private final DtoFunctionFactory factory;

    @Inject
    public MovieSimpleController(MovieService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetMoviesResponse getMovies() {
        return factory.moviesToResponse().apply(service.findAll());
    }

    @Override
    public GetMoviesResponse getDirectorMovies(UUID id) {
        return service.findAllByDirector(id)
                .map(factory.moviesToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetMoviesResponse getUserMovies(UUID id) {
        return service.findAllByUser(id)
                .map(factory.moviesToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetMovieResponse getMovie(UUID uuid) {
        return service.find(uuid)
                .map(factory.movieToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putMovie(UUID id, PutMovieRequest request) {
        try {
            service.create(factory.requestToMovie().apply(id, request));
        }
        catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchMovie(UUID id, PatchMovieRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateMovie().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteMovie(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
