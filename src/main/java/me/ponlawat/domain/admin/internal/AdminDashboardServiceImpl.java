package me.ponlawat.domain.admin.internal;

import io.quarkus.panache.common.Sort;
import me.ponlawat.domain.admin.AdminDashboardService;
import me.ponlawat.domain.admin.dto.AdminStatusResponse;
import me.ponlawat.domain.ticket.Ticket;
import me.ponlawat.domain.ticket.TicketRepository;
import me.ponlawat.domain.ticket.TicketStatus;
import me.ponlawat.domain.user.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class AdminDashboardServiceImpl implements AdminDashboardService {

    @Inject
    TicketRepository ticketRepository;
    @Inject
    UserRepository userRepository;

    @Override
    public AdminStatusResponse getAdminStatus() {
        long openCount = ticketRepository.countByStatus(TicketStatus.OPEN);
        long processingCount = ticketRepository.countByStatus(TicketStatus.PROCESSING);
        long doneCount = ticketRepository.countByStatus(TicketStatus.DONE);
        long userCount = userRepository.count();

        AdminStatusResponse response = new AdminStatusResponse();
        response.setOpenCount(openCount);
        response.setProcessingCount(processingCount);
        response.setDoneCount(doneCount);
        response.setUserCount(userCount);

        return response;
    }

    @Override
    public List<Ticket> getTicket() {
        List<Ticket> tickets = ticketRepository.listAll(Sort.by("id", Sort.Direction.Descending));
        return tickets;
    }
}
