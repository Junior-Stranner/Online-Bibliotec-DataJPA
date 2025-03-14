package br.com.judev.bibliotec.dtos.requestDto;



import java.time.LocalDate;

public class LoanRequestDTO {
    private LocalDate inicio = LocalDate.now();
    private LocalDate fim = LocalDate.of(2025, 12, 31);
}
