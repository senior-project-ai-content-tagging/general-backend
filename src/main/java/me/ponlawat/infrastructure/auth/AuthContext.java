package me.ponlawat.infrastructure.auth;

import me.ponlawat.domain.user.User;

public interface AuthContext {
    User getUser();
}
