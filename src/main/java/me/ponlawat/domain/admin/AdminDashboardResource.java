package me.ponlawat.domain.admin;

import me.ponlawat.domain.admin.dto.AdminStatusResponse;
import me.ponlawat.domain.admin.dto.AdminTicketResponse;
import me.ponlawat.infrastructure.auth.AdminRequired;
import me.ponlawat.infrastructure.auth.JwtRequired;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/admin/dashboard")
@JwtRequired
@AdminRequired
@Produces(MediaType.APPLICATION_JSON)
public class AdminDashboardResource {

    @Inject
    AdminDashboardService adminStatusService;

    @GET
    @Path("/status")
    public AdminStatusResponse getAdminStatus() {
        return adminStatusService.getAdminStatus();
    }

    @GET
    @Path("/ticket")
    public List<AdminTicketResponse> getTickets() {
        return adminStatusService.getTicket().stream().map(ticket -> AdminTicketResponse.fromTicket(ticket)).collect(Collectors.toList());
    }
}
