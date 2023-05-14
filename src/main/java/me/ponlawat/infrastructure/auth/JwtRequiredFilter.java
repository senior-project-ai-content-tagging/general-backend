package me.ponlawat.infrastructure.auth;

import me.ponlawat.domain.user.User;
import me.ponlawat.domain.user.UserService;
import me.ponlawat.domain.user.exception.UserUnauthorizedException;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import javax.json.JsonNumber;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

@JwtRequired
@Provider
@Priority(Interceptor.Priority.APPLICATION)
public class JwtRequiredFilter implements ContainerRequestFilter {

    @Inject
    JsonWebToken jwt;
    @Inject
    UserService userService;
    @Inject
    AuthContextImpl authContext;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        try {
            JsonNumber userId = jwt.getClaim(AuthContextImpl.UserIdKey);
            User user = userService.profile(userId.longValue());

            authContext.setUser(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new UserUnauthorizedException();
        }
    }
}
