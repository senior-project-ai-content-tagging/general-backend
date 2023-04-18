package me.ponlawat.infrastructure.auth;

import me.ponlawat.domain.user.User;
import me.ponlawat.domain.user.UserService;
import me.ponlawat.domain.user.exception.UserUnauthorizedException;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.json.JsonNumber;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@JwtRequired
@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    JsonWebToken jwt;
    @Inject
    UserService userService;
    @Inject
    AuthContextImpl auth;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        try {
            JsonNumber userId = jwt.getClaim(AuthContextImpl.UserIdKey);
            User user = userService.profile(userId.longValue());

            auth.setUser(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new UserUnauthorizedException();
        }
    }
}
