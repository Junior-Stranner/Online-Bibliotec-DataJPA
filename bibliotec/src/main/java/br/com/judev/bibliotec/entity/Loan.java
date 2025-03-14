package br.com.judev.bibliotec.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate inicio;
    private LocalDate fim;
    private Double multa = 0.00;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @Enumerated(EnumType.STRING)
    private StatusLoan status;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User user;

    public Loan(LocalDate inicio, LocalDate fim, Double multa, Book book, StatusLoan status, User user) {
        this.inicio = inicio;
        this.fim = LocalDate.now().plusWeeks(2);
        this.multa = multa;
        this.book = book;
        this.status = status;
        this.user = user;
    }

    public void multaPagar() {
        LocalDate dataLimite = this.inicio.plusWeeks(2);
        if (LocalDate.now().isAfter(dataLimite)) {
            long diasDeAtraso = java.time.temporal.ChronoUnit.DAYS.between(dataLimite, LocalDate.now());

            long semanasDeAtraso = diasDeAtraso / 7;

            if (semanasDeAtraso > 0) {
                this.multa += (this.multa * 0.02) * semanasDeAtraso;
            }
        }
    }


}
