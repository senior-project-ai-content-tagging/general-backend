package me.ponlawat.domain.user.exception;

import me.ponlawat.infrastructure.provider.http.HttpErrorException;

import javax.ws.rs.core.Response;

public class UserNotFoundException extends HttpErrorException {
    public UserNotFoundException(long id) {
        super(Response.Status.NOT_FOUND, "User id: " + id +" not found.");
    }
}
