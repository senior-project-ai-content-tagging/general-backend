package me.ponlawat.domain.ticket;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class TicketRepository implements PanacheRepository<Ticket> {
    public List<Ticket> findByUserId(long userId) {
        return list("user_id", Sort.by("id"), userId);
    }

    public long countByStatus(TicketStatus status) {
        return count("status", status);
    }
}
