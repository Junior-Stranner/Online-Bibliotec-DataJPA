package br.com.judev.bibliotec.infra.exceptions;

public class UserEmailNotFoundException extends RuntimeException {
    public UserEmailNotFoundException(String message) {
        super(message);
    }
}
