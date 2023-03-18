package me.ponlawat.domain.user.exception;

import me.ponlawat.infrastructure.provider.http.HttpErrorException;

import javax.ws.rs.core.Response;

public class UserUnauthorizedException extends HttpErrorException {
    public UserUnauthorizedException() {
        super(Response.Status.UNAUTHORIZED, "Unauthorized");
    }
}
