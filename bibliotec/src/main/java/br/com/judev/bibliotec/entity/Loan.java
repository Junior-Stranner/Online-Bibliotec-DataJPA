package br.com.judev.bibliotec.entity;

import br.com.judev.bibliotec.entity.enums.StatusLoan;
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
    private LocalDate startDate;
    private LocalDate endDate;
    private Double fine = 0.00;


    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @Enumerated(EnumType.STRING)
    private StatusLoan status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    private boolean isAvailable;


    public Loan(LocalDate startDate, Book book, StatusLoan status, User user, Address address, boolean isAvailable) {
        this.startDate = startDate;
        this.endDate = startDate.plusWeeks(2); // Calcula a data de devolução automaticamente
        this.fine = 0.00; // Inicializa com multa 0
        this.book = book;
        this.status = status;
        this.user = user;
        this.address = address;
        this.isAvailable = status == StatusLoan.REQUESTED;
    }

    public void calculateFine() {
        LocalDate dueDate = this.startDate.plusWeeks(2);
        if (LocalDate.now().isAfter(dueDate)) {
            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(dueDate, LocalDate.now());

            long weeksLate = daysLate / 7;

            if (weeksLate > 0) {
                this.fine += (this.fine * 0.02) * weeksLate;
            }
        }
    }
    public void updateAvailability() {
        this.isAvailable = this.status == StatusLoan.RETURNED;
    }

}
