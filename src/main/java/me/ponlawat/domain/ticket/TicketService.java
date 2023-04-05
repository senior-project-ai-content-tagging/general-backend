package me.ponlawat.domain.ticket;

import me.ponlawat.domain.ticket.dto.TicketWeblinkRequest;
import me.ponlawat.domain.ticket.dto.TicketWeblinkResponse;
import me.ponlawat.domain.user.User;

import java.util.List;

public interface TicketService {
    public TicketWeblinkResponse submitWeblink(User user, TicketWeblinkRequest ticketWeblinkRequest);
    public List<Ticket> getTicketByUser(User user);
}
