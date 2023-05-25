package me.ponlawat.domain.admin;

import me.ponlawat.domain.admin.dto.AdminStatusResponse;
import me.ponlawat.domain.admin.dto.AdminTicketResponse;
import me.ponlawat.domain.ticket.Ticket;

import java.util.List;

public interface AdminDashboardService {
    AdminStatusResponse getAdminStatus();
    List<Ticket> getTicket();
}
