package me.ponlawat.domain.content.exception;

import me.ponlawat.infrastructure.provider.http.HttpErrorException;

import javax.ws.rs.core.Response;

public class ContentNotFoundException extends HttpErrorException {
    public ContentNotFoundException(long id) {
        super(Response.Status.NOT_FOUND, "Content id: " + id +" not found.");
    }
}
