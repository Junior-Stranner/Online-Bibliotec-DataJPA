package br.com.judev.bibliotec.dtos.responseDto;

import br.com.judev.bibliotec.entity.Book;
import br.com.judev.bibliotec.entity.User;

import java.time.LocalDate;

public class LoanResponseDTO {
    private Long Id;
    private LocalDate inicio;
    private LocalDate fim;
    private Book book;
    private User user;

}
