package me.ponlawat.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.ponlawat.domain.ticket.Ticket;
import me.ponlawat.domain.ticket.TicketStatus;
import me.ponlawat.domain.user.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminTicketResponse {
    long id;
    String title;
    User user;
    TicketStatus status;
    List<String> categories;
    long contentId;

    public static AdminTicketResponse fromTicket(Ticket ticket) {
        AdminTicketResponse ticketResponse = new AdminTicketResponse();
        ticketResponse.setId(ticket.getId());
        ticketResponse.setTitle(ticket.getContent().getTitleTH());
        ticketResponse.setStatus(ticket.getStatus());
        ticketResponse.setCategories(ticket.getContent().getCategories().stream().map(category -> category.getName()).toList());
        ticketResponse.setContentId(ticket.getContent().getId());
        ticketResponse.setUser(ticket.getUser());

        return ticketResponse;
    }
}