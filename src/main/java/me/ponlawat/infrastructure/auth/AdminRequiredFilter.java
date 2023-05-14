package me.ponlawat.infrastructure.auth;

import lombok.Setter;
import me.ponlawat.domain.user.User;
import me.ponlawat.domain.user.UserRole;
import me.ponlawat.domain.user.exception.UserForbiddenRoleException;
import me.ponlawat.domain.user.exception.UserUnauthorizedException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

@Provider
@AdminRequired
@Priority(Interceptor.Priority.APPLICATION + 1)
@Setter
public class AdminRequiredFilter implements ContainerRequestFilter {
    @Inject
    AuthContext authContext;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        User user = authContext.getUser();
        if (user == null) {
            throw new UserUnauthorizedException();
        }

        if (!user.getRole().equals(UserRole.ADMIN)) {
            throw new UserForbiddenRoleException();
        }
    }
}
