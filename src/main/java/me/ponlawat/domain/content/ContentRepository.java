package me.ponlawat.domain.content;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContentRepository implements PanacheRepository<Content> {
}
