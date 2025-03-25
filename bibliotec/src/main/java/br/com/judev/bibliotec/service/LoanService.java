package br.com.judev.bibliotec.service;

import br.com.judev.bibliotec.dtos.requestDto.LoanRequestDTO;
import br.com.judev.bibliotec.dtos.responseDto.LoanResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LoanService {
    // Criar um novo empréstimo
    LoanResponseDTO makeLoan(LoanRequestDTO loanRequestDTO);

    // Buscar todos os empréstimos
    List<LoanResponseDTO> findAllLoans(Pageable pageable);

    // Buscar empréstimos por ID
    LoanResponseDTO findLoanById(Long id);

    // Devolver empréstimo
    void returnLoan(Long id);

    // Deletar empréstimo
    void deleteLoan(Long id);
}
