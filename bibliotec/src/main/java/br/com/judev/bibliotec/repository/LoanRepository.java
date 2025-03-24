package br.com.judev.bibliotec.repository;

import br.com.judev.bibliotec.entity.Loan;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
