package me.ponlawat.infrastructure.auth;

import io.smallrye.jwt.build.Jwt;
import me.ponlawat.domain.user.User;
import me.ponlawat.domain.user.UserService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonNumber;
import java.time.Duration;

@RequestScoped
public class JwtToken implements AuthToken {

    @Inject
    JsonWebToken jwt;
    @Inject
    UserService userService;

    public static String UserIdKey = "userId";

    @Override
    public String sign(User user) {
        return Jwt.issuer("thai-content")
                .upn(user.getEmail())
                .claim(UserIdKey, user.getId())
                .expiresIn(Duration.ofDays(7))
                .groups(user.getRole().toString())
                .sign();
    }

    @Override
    public User getUser() {
        JsonNumber userId = jwt.getClaim(JwtToken.UserIdKey);
        User user = userService.profile(userId.longValue());
        return user;
    }
}
