package me.ponlawat.domain.ticket;

import me.ponlawat.domain.ticket.dto.TicketContentRequest;
import me.ponlawat.domain.ticket.dto.TicketResponse;
import me.ponlawat.domain.ticket.dto.TicketWeblinkRequest;
import me.ponlawat.domain.ticket.dto.TicketRequestResponse;
import me.ponlawat.domain.user.User;
import me.ponlawat.infrastructure.auth.ApiKeyRequired;
import me.ponlawat.infrastructure.auth.AuthContext;
import me.ponlawat.infrastructure.auth.JwtRequired;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/tickets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TicketResource {

    @Inject
    TicketService ticketService;
    @Inject
    AuthContext auth;

    @POST
    @JwtRequired
    @Path("/weblink")
    public TicketRequestResponse weblink(@Valid TicketWeblinkRequest ticketWeblinkRequest) {
        User user = auth.getUser();

        return ticketService.submitWeblink(user, ticketWeblinkRequest);
    }

    @POST
    @JwtRequired
    @Path("/content")
    public TicketRequestResponse content(@Valid TicketContentRequest ticketContentRequest) {
        User user = auth.getUser();

        return ticketService.submitContent(user, ticketContentRequest);
    }

    @POST
    @ApiKeyRequired
    @Path("/api-key/weblink")
    public TicketRequestResponse submitWeblinkByApiKey(@Valid TicketWeblinkRequest ticketWeblinkRequest) {
        User user = auth.getUser();

        return ticketService.submitWeblink(user, ticketWeblinkRequest);
    }

    @GET
    @JwtRequired
    @Path("")
    public List<TicketResponse> myTicket() {
        User user = auth.getUser();

        return ticketService.getTicketByUser(user).stream().map(ticket -> TicketResponse.fromTicket(ticket))
                .collect(Collectors.toList());
    }
}
