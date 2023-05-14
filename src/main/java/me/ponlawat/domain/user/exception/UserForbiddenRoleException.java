package me.ponlawat.domain.user.exception;

import me.ponlawat.infrastructure.provider.http.HttpErrorException;

import javax.ws.rs.core.Response;

public class UserForbiddenRoleException extends HttpErrorException {
    public UserForbiddenRoleException() {
        super(Response.Status.FORBIDDEN, "Forbidden");
    }
}
