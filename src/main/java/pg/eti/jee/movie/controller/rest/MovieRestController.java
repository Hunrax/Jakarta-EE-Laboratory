package pg.eti.jee.movie.controller.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;
import pg.eti.jee.component.DtoFunctionFactory;
import pg.eti.jee.movie.controller.api.MovieController;
import pg.eti.jee.movie.dto.GetMovieResponse;
import pg.eti.jee.movie.dto.GetMoviesResponse;
import pg.eti.jee.movie.dto.PatchMovieRequest;
import pg.eti.jee.movie.dto.PutMovieRequest;
import pg.eti.jee.movie.service.MovieService;
import jakarta.persistence.OptimisticLockException;

import pg.eti.jee.user.entity.UserRoles;

import java.util.UUID;
import java.util.logging.Level;

@RolesAllowed(UserRoles.USER)
@Path("")
@Log
public class MovieRestController implements MovieController {

    private MovieService service;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public MovieRestController(DtoFunctionFactory factory,
                               @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setService(MovieService service) {
        this.service = service;
    }


    @Override
    public GetMoviesResponse getMovies() {
        return factory.moviesToResponse().apply(service.findAllForCallerPrincipal());
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
        return service.findForCallerPrincipal(uuid)
                .map(factory.movieToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putMovie(UUID directorId, UUID id, PutMovieRequest request) {
        try {
            service.createForCallerPrincipal(factory.requestToMovie().apply(directorId, id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(MovieController.class, "getMovie")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        }
        catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
        }
    }

    @Override
    public void patchMovie(UUID directorId, UUID id, PatchMovieRequest request) {
        service.findForCallerPrincipal(id).ifPresentOrElse(
                entity -> {
                    try {
                        service.update(factory.updateMovie().apply(entity, request));
                    } catch (EJBAccessException ex) {
                        log.log(Level.WARNING, ex.getMessage(), ex);
                        throw new ForbiddenException(ex.getMessage());
                    } catch (EJBException ex) {
                        if (ex.getCause() instanceof OptimisticLockException) {
                            throw new BadRequestException(ex.getCause());
                        }
                    }},
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteMovie(UUID id) {
        service.findForCallerPrincipal(id).ifPresentOrElse(
                entity -> {
                    try {
                        service.delete(id);
                    } catch (EJBAccessException ex) {
                        log.log(Level.WARNING, ex.getMessage(), ex);
                        throw new ForbiddenException(ex.getMessage());
                    }
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
