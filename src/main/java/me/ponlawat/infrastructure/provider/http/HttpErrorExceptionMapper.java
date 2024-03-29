package me.ponlawat.infrastructure.provider.http;

import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class HttpErrorExceptionMapper implements ExceptionMapper<HttpErrorException> {
    @Override
    public Response toResponse(HttpErrorException exception) {
        ErrorResponse response = new ErrorResponse(exception.getMessage());

        return Response.ok(response).status(exception.getHttpStatus()).build();
    }
}