package me.ponlawat.infrastructure.auth;

import io.smallrye.jwt.build.Jwt;
import me.ponlawat.domain.user.User;

import javax.enterprise.context.RequestScoped;
import java.time.Duration;

@RequestScoped
public class AuthContextImpl implements AuthContext {
    public static final String UserIdKey = "userId";

    private User user;

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
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
