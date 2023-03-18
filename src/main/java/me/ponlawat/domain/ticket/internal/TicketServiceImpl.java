package me.ponlawat.domain.ticket.internal;

import lombok.Setter;
import me.ponlawat.domain.content.Content;
import me.ponlawat.domain.content.ContentRepository;
import me.ponlawat.domain.ticket.*;
import me.ponlawat.domain.ticket.dto.TicketWeblinkRequest;
import me.ponlawat.domain.ticket.dto.TicketWeblinkResponse;
import me.ponlawat.domain.user.User;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
@Setter
public class TicketServiceImpl implements TicketService {

    @Inject
    TicketRepository ticketRepository;
    @Inject
    ContentRepository contentRepository;

    private static final Logger LOG = Logger.getLogger(TicketServiceImpl.class);

    @Override
    @Transactional
    public TicketWeblinkResponse submitWeblink(User user, TicketWeblinkRequest ticketWeblinkRequest) {
        Content content = new Content();
        content.setOriginalUrl(ticketWeblinkRequest.getUrl());
        contentRepository.persistAndFlush(content);

        Ticket ticket = new Ticket();
        ticket.setContent(content);
        ticket.setUser(user);
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setType(TicketType.USER);
        ticketRepository.persist(ticket);

        TicketWeblinkResponse response = new TicketWeblinkResponse(ticket.getId(), ticket.getStatus());
        LOG.info("create ticket success" + ticket);

        return response;
    }
}
