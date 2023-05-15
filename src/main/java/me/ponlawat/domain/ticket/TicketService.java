package me.ponlawat.domain.ticket;

import me.ponlawat.domain.ticket.dto.TicketContentRequest;
import me.ponlawat.domain.ticket.dto.TicketWeblinkRequest;
import me.ponlawat.domain.ticket.dto.TicketRequestResponse;
import me.ponlawat.domain.user.User;

import java.util.List;

public interface TicketService {
    public TicketRequestResponse submitWeblink(User user, TicketWeblinkRequest ticketWeblinkRequest);
    public TicketRequestResponse submitContent(User user, TicketContentRequest ticketContentRequest);
    public List<Ticket> getTicketByUser(User user);
    public Ticket getTicketById(Long id);
}
