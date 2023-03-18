package me.ponlawat.domain.ticket;

import me.ponlawat.domain.ticket.dto.TicketWeblinkRequest;
import me.ponlawat.domain.ticket.dto.TicketWeblinkResponse;
import me.ponlawat.domain.user.User;

public interface TicketService {
    public TicketWeblinkResponse submitWeblink(User user, TicketWeblinkRequest ticketWeblinkRequest);
}
