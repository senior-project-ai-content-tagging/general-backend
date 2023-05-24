package me.ponlawat.domain.category.exception;

import me.ponlawat.infrastructure.provider.http.HttpErrorException;

import javax.ws.rs.core.Response;

public class CategoryNotFoundException extends HttpErrorException {
    public CategoryNotFoundException(long id) {
        super(Response.Status.NOT_FOUND, "Category id: " + id +" not found.");
    }
}
