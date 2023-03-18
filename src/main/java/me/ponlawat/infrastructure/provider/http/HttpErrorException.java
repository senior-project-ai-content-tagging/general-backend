package me.ponlawat.infrastructure.provider.http;

import lombok.Getter;

import javax.ws.rs.core.Response;

@Getter
public class HttpErrorException extends RuntimeException {
    public Response.Status httpStatus;
    public String message;

    public HttpErrorException(Response.Status httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
