package me.ponlawat.infrastructure.provider.http;

import io.quarkus.security.ForbiddenException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {
    @Override
    public Response toResponse(ForbiddenException exception) {
        ErrorResponse response = new ErrorResponse("No permission");

        return Response.ok(response).status(Response.Status.FORBIDDEN).build();
    }
}
