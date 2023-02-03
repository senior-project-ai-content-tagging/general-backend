package me.ponlawat.infrastructure.provider;

import lombok.Getter;

import javax.ws.rs.core.Response;

@Getter
public abstract class HttpErrorException extends RuntimeException {
    public String message;
    public Response.Status httpStatus;

    public HttpErrorException(String message, Response.Status httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
