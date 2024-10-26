package pg.eti.jee.director.controller.api;

import jakarta.ws.rs.*;
import pg.eti.jee.director.dto.GetDirectorResponse;
import pg.eti.jee.director.dto.GetDirectorsResponse;
import jakarta.ws.rs.core.MediaType;
import pg.eti.jee.director.dto.PatchDirectorRequest;
import pg.eti.jee.director.dto.PutDirectorRequest;
import pg.eti.jee.movie.dto.PatchMovieRequest;
import pg.eti.jee.movie.dto.PutMovieRequest;

import java.util.UUID;

@Path("")
public interface DirectorController {
    @GET
    @Path("/directors")
    @Produces(MediaType.APPLICATION_JSON)
    GetDirectorsResponse getDirectors();

    @GET
    @Path("/directors/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetDirectorResponse getDirector(@PathParam("id") UUID id);

    @PUT
    @Path("/directors/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putDirector(@PathParam("id") UUID id, PutDirectorRequest request);

    @PATCH
    @Path("/directors/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchDirector(@PathParam("id")UUID id, PatchDirectorRequest request);

    @DELETE
    @Path("/directors/{id}")
    void deleteDirector(@PathParam("id") UUID id);

}
