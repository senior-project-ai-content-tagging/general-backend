package me.ponlawat.domain.ticket;

import me.ponlawat.domain.ticket.dto.TicketWeblinkRequest;
import me.ponlawat.domain.ticket.dto.TicketWeblinkResponse;
import me.ponlawat.domain.user.User;
import me.ponlawat.infrastructure.auth.AuthToken;
import me.ponlawat.infrastructure.auth.JwtToken;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
}
