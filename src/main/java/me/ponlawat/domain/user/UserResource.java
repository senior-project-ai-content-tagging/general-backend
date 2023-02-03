package me.ponlawat.domain.user;

import me.ponlawat.domain.user.dto.UserRegisterDto;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/users")
public class UserResource {

    @Inject
    UserService userService;

    @POST
    @Path("/register")
    public User register(@Valid UserRegisterDto userRegisterDto) {
        return userService.register(userRegisterDto);
    }
}
