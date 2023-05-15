package me.ponlawat.domain.ticket.exception;

import me.ponlawat.infrastructure.provider.http.HttpErrorException;

import javax.ws.rs.core.Response;

public class TicketNotFoundException extends HttpErrorException {
    public TicketNotFoundException(long id) {
        super(Response.Status.NOT_FOUND, "Ticket id: " + id +" not found.");
    }
}
