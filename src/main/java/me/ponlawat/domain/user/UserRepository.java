package me.ponlawat.domain.user;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    public Optional<User> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    public Optional<User> findByApiKey(String apiKey) {
        return find("api_key", apiKey).firstResultOptional();
    }
}
