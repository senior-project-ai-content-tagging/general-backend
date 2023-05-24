package me.ponlawat.domain.admin;

import me.ponlawat.domain.user.User;
import me.ponlawat.domain.user.UserService;
import me.ponlawat.domain.user.dto.UserEditRequest;
import me.ponlawat.infrastructure.auth.AdminRequired;
import me.ponlawat.infrastructure.auth.JwtRequired;
import org.apache.http.HttpStatus;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/admin/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@JwtRequired
@AdminRequired
public class AdminUserResource {

    @Inject
    UserService userService;

    @GET
    @Path("")
    public List<User> listUser() {
        return userService.listUser();
    }

    @GET
    @Path("/{id}")
    public User detailUser(@PathParam("id") long id) {
        return userService.getUser(id);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") long id) {
        userService.removeUser(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/{id}")
    public User editUser(@PathParam("id") long id, @Valid UserEditRequest userEditRequest) {
        return userService.updateUser(id, userEditRequest);
    }
}
