package pg.eti.jee.movie.controller.rest;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pg.eti.jee.component.DtoFunctionFactory;
import pg.eti.jee.movie.controller.api.MovieController;
import pg.eti.jee.movie.dto.GetMovieResponse;
import pg.eti.jee.movie.dto.GetMoviesResponse;
import pg.eti.jee.movie.dto.PatchMovieRequest;
import pg.eti.jee.movie.dto.PutMovieRequest;
import pg.eti.jee.movie.service.MovieService;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.UUID;

@Path("")
public class MovieRestController implements MovieController {

    private final MovieService service;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public MovieRestController(MovieService service, DtoFunctionFactory factory,
                               @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
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
    public void putMovie(UUID directorId, UUID id, PutMovieRequest request) {
        try {
            service.create(factory.requestToMovie().apply(directorId, id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(MovieController.class, "getMovie")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        }
        catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchMovie(UUID directorId, UUID id, PatchMovieRequest request) {
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
