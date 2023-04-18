package me.ponlawat.infrastructure.auth;

import me.ponlawat.domain.user.User;
import me.ponlawat.domain.user.UserRepository;
import me.ponlawat.domain.user.exception.UserUnauthorizedException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.util.Optional;

@ApiKeyRequired
@Provider
public class ApiKeyRequiredFilter implements ContainerRequestFilter {

    @Inject
    UserRepository userRepository;
    @Inject
    AuthContextImpl authContext;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String apiKey = requestContext.getHeaderString("X-API-KEY");
        if (apiKey.isEmpty()) {
            throw new UserUnauthorizedException();
        }

        Optional<User> optionalUser = userRepository.findByApiKey(apiKey);
        if (optionalUser.isEmpty()) {
            throw new UserUnauthorizedException();
        }

        authContext.setUser(optionalUser.get());
    }
}
