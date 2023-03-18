package me.ponlawat.infrastructure.provider.http;

import com.github.fge.jsonpatch.Iterables;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        ConstraintViolation firstConstraintViolation = exception.getConstraintViolations().stream().findFirst().get();
        String field = firstConstraintViolation.getPropertyPath().toString();
        field = field.substring(field.lastIndexOf(".") + 1);
        ErrorResponse response = new ErrorResponse(field + ": " + firstConstraintViolation.getMessage());

        return Response.ok(response).status(Response.Status.BAD_REQUEST).build();
    }
}
