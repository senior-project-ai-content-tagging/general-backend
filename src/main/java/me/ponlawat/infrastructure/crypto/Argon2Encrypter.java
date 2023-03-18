package me.ponlawat.infrastructure.crypto;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Argon2Encrypter implements PasswordEncrypter{
    private static final int ITERATIONS = 2;
    private static final int MEMORY = 20 * 1024; // 20MB
    private static final int PARALLELISM = 1;

    Argon2 argon2 = Argon2Factory.create();

    @Override
    public String hash(String str) {
        return argon2.hash(ITERATIONS, MEMORY, PARALLELISM, str);
    }

    @Override
    public boolean verify(String hash, String str) {
        return argon2.verify(hash, str);
    }
}
