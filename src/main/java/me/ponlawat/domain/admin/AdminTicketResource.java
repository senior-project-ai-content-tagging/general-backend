package me.ponlawat.domain.admin;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.oracle.svm.core.annotate.Inject;
import me.ponlawat.domain.ticket.TicketService;
import me.ponlawat.infrastructure.auth.AdminRequired;
import me.ponlawat.infrastructure.auth.JwtRequired;

@Path("/admin/tickets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@JwtRequired
@AdminRequired
public class AdminTicketResource {

    @Inject
    TicketService ticketService;

    @DELETE
    @Path("/{id}")
    public Response deleteTicket(@PathParam("id") long id) {
        ticketService.removeTicket(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
