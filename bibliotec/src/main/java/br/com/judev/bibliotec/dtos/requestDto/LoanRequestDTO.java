package br.com.judev.bibliotec.dtos.requestDto;



import br.com.judev.bibliotec.entity.Address;
import br.com.judev.bibliotec.entity.Book;
import br.com.judev.bibliotec.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanRequestDTO {
    private LocalDate inicio = LocalDate.now();
    private LocalDate fim = LocalDate.of(2025, 12, 31);
    @JsonIgnore
    private Book book;
    @JsonIgnore
    private User user;
    @JsonIgnore
    private Address address;
}
