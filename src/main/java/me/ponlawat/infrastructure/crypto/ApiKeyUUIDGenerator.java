package me.ponlawat.infrastructure.crypto;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class ApiKeyUUIDGenerator implements ApiKeyGenerator {
    @Override
    public String generate() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
