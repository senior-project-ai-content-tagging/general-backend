package me.ponlawat.infrastructure.auth;

import me.ponlawat.domain.user.User;

public interface AuthToken {
    String sign(User user);
    User getUser();
}
