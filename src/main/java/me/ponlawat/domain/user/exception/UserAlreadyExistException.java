package me.ponlawat.domain.user.exception;

import me.ponlawat.infrastructure.provider.http.HttpErrorException;

import javax.ws.rs.core.Response;

public class UserAlreadyExistException extends HttpErrorException {
    public UserAlreadyExistException() {
        super(Response.Status.UNAUTHORIZED, "User already exist");
    }
}