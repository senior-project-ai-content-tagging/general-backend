package me.ponlawat.domain.ticket;

import me.ponlawat.domain.ticket.dto.TicketResponse;
import me.ponlawat.domain.ticket.dto.TicketWeblinkRequest;
import me.ponlawat.domain.ticket.dto.TicketWeblinkResponse;
import me.ponlawat.domain.user.User;
import me.ponlawat.infrastructure.auth.AuthToken;
import me.ponlawat.infrastructure.auth.JwtToken;

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
    AuthToken auth;

    @POST
    @Path("/weblink")
    public TicketWeblinkResponse weblink(@Valid TicketWeblinkRequest ticketWeblinkRequest) {
        User user = auth.getUser();

        return ticketService.submitWeblink(user, ticketWeblinkRequest);
    }

    @GET
    @Path("")
    public List<TicketResponse> myTicket() {
        User user = auth.getUser();

        return ticketService.getTicketByUser(user).stream().map(ticket -> TicketResponse.fromTicket(ticket)).collect(Collectors.toList());
    }
}
