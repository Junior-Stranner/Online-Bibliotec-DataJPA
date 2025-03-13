package br.com.judev.bibliotec.infra.exceptions;

import br.com.judev.bibliotec.dtos.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorDetails> handleInvalidTokenException(InvalidTokenException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }
}

/*
 * A anotação @ControllerAdvice no Spring Framework é usada para criar uma classe centralizada
 * que trata exceções e fornece funcionalidades compartilhadas para todos os controladores (@Controller)
 *  de uma aplicação. É parte do pacote org.springframework.web.bind.annotation e facilita a separação de
 * responsabilidades no código, especialmente em relação ao tratamento de erros e configuração global de controladores.*/

