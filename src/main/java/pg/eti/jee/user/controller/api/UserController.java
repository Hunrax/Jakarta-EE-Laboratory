package pg.eti.jee.user.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pg.eti.jee.user.dto.GetUserResponse;
import pg.eti.jee.user.dto.GetUsersResponse;
import pg.eti.jee.user.dto.PatchUserRequest;
import pg.eti.jee.user.dto.PutUserRequest;

import java.io.InputStream;
import java.util.UUID;

@Path("")
public interface UserController {
    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetUserResponse getUser(@PathParam("id") UUID id);

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    GetUsersResponse getUsers();

    @PATCH
    @Path("/users/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void updateUser(@PathParam("id") UUID id, PatchUserRequest patchUserRequest);

    @PUT
    @Path("/users/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void createUser(@PathParam("id") UUID id, PutUserRequest putUserRequest);

    @DELETE
    @Path("/users/{id}")
    void deleteUser(@PathParam("id") UUID id);


    @GET
    @Path("/users/{id}/portrait")
    @Produces("image/png")
    byte[] getUserPortrait(@PathParam("id") UUID id);

    @PUT
    @Path("/users/{id}/portrait")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    void putUserPortrait(@PathParam("id") UUID id, InputStream portrait);

    @DELETE
    @Path("/users/{id}/portrait")
    void deleteUserPortrait(@PathParam("id") UUID id);

}
