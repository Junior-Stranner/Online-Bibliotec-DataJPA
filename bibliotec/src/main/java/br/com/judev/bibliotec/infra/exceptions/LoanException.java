package br.com.judev.bibliotec.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoanException extends RuntimeException {
    public LoanException(String message) {
        super(message);
    }
}
