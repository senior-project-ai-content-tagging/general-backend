package me.ponlawat.domain.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.ponlawat.domain.ticket.Ticket;
import me.ponlawat.domain.ticket.TicketStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
    long id;
    String title;
    TicketStatus status;
    List<String> categories;
    long contentId;

    public static TicketResponse fromTicket(Ticket ticket) {
        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setId(ticket.getId());
        ticketResponse.setTitle(ticket.getContent().getTitleTH());
        ticketResponse.setStatus(ticket.getStatus());
        ticketResponse.setCategories(ticket.getContent().getCategories().stream().map(category -> category.getName()).toList());
        ticketResponse.setContentId(ticket.getContent().getId());

        return ticketResponse;
    }
}