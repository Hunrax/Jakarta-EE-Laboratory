package pg.eti.jee.movie.controller.api;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import pg.eti.jee.movie.dto.GetMovieResponse;
import pg.eti.jee.movie.dto.GetMoviesResponse;
import pg.eti.jee.movie.dto.PatchMovieRequest;
import pg.eti.jee.movie.dto.PutMovieRequest;

import java.util.UUID;

@Path("")
public interface MovieController {
    @GET
    @Path("/movies")
    @Produces(MediaType.APPLICATION_JSON)
    GetMoviesResponse getMovies();

    @GET
    @Path("/directors/{id}/movies")
    @Produces(MediaType.APPLICATION_JSON)
    GetMoviesResponse getDirectorMovies(@PathParam("id") UUID id);

    @GET
    @Path("/users/{id}/movies/")
    @Produces(MediaType.APPLICATION_JSON)
    GetMoviesResponse getUserMovies(@PathParam("id") UUID id);

    @GET
    @Path("/movies/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetMovieResponse getMovie(@PathParam("id") UUID id);

    @PUT
    @Path("/directors/{directorId}/movies/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putMovie(@PathParam("directorId") UUID directorId, @PathParam("id") UUID id, PutMovieRequest request);


    @PATCH
    @Path("/directors/{directorId}/movies/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchMovie(@PathParam("directorId") UUID directorId, @PathParam("id")  UUID id, PatchMovieRequest request);

    @DELETE
    @Path("/movies/{id}")
    void deleteMovie(@PathParam("id") UUID id);

}
