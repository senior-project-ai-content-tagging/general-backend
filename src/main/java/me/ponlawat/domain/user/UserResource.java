package me.ponlawat.domain.user;

import me.ponlawat.domain.user.dto.UserLoginRequest;
import me.ponlawat.domain.user.dto.UserLoginResponse;
import me.ponlawat.domain.user.dto.UserRegisterRequest;
import me.ponlawat.infrastructure.auth.AuthToken;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    UserService userService;
    @Inject
    AuthToken auth;

    @POST
    @Path("/register")
    public User register(@Valid UserRegisterRequest userRegisterRequest) {
        return userService.register(userRegisterRequest);
    }

    @POST
    @Path("/login")
    public UserLoginResponse login(@Valid UserLoginRequest userLoginRequest) {
        return userService.login(userLoginRequest);
    }

    @GET
    @RolesAllowed({ "MEMBER", "ADMIN" })
    @Path("/profile")
    public User profile() {
        User user = auth.getUser();

        return user;
    }
}
