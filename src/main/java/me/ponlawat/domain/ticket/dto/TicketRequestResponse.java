package me.ponlawat.domain.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.ponlawat.domain.ticket.TicketStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketRequestResponse {
    public Long ticketId;
    public TicketStatus status;
    public Long contentId;
}
