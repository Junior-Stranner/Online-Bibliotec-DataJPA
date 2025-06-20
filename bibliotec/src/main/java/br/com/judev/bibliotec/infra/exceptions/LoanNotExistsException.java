package br.com.judev.bibliotec.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoanNotExistsException extends RuntimeException {
    public LoanNotExistsException(String message) {
        super(message);
    }
}
