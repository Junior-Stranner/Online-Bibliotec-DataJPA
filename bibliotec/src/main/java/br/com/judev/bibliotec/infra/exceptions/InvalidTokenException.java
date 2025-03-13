package br.com.judev.bibliotec.infra.exceptions;

public class InvalidTokenException extends Exception {
    public InvalidTokenException(String message) {
        super(message);
    }
}
