package me.ponlawat.domain.user.exception;

import me.ponlawat.infrastructure.provider.HttpErrorException;

import javax.ws.rs.core.Response;

public class UserAlreadyExistException extends HttpErrorException {
    public UserAlreadyExistException() {
        super("User Already Exist", Response.Status.UNAUTHORIZED);
    }
}