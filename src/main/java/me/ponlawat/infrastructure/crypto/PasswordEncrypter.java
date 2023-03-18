package me.ponlawat.infrastructure.crypto;

public interface PasswordEncrypter {
    String hash(String str);
    boolean verify(String hash, String str);
}
