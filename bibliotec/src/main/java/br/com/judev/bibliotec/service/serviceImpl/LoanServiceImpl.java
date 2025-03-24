package br.com.judev.bibliotec.service.impl;

import br.com.judev.bibliotec.dtos.mapper.AddressMapper;
import br.com.judev.bibliotec.dtos.mapper.LoanMapper;
import br.com.judev.bibliotec.dtos.mapper.LoansMapper;
import br.com.judev.bibliotec.dtos.requestDto.LoanRequestDTO;
import br.com.judev.bibliotec.dtos.responseDto.LoanResponseDTO;
import br.com.judev.bibliotec.entity.Loan;
import br.com.judev.bibliotec.entity.Book;
import br.com.judev.bibliotec.dto.LoanResponseDTO;
import br.com.judev.bibliotec.dto.LoanRequestDTO;
import br.com.judev.bibliotec.entity.StatusLoan;
import br.com.judev.bibliotec.exception.LoanException;
import br.com.judev.bibliotec.infra.exceptions.BookNotAvaliableForLoanException;
import br.com.judev.bibliotec.infra.exceptions.LoanException;
import br.com.judev.bibliotec.infra.exceptions.UserEmailNotFoundException;
import br.com.judev.bibliotec.repository.LoanRepository;
import br.com.judev.bibliotec.repository.BookRepository;
import br.com.judev.bibliotec.repository.UserRepository;
import br.com.judev.bibliotec.service.LoanService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    @Override
    public LoanResponseDTO makeLoan(LoanRequestDTO loanRequestDTO) {

        var user = userRepository.findByEmail(loanRequestDTO.getUser().getEmail())
                .orElseThrow(() -> new UserEmailNotFoundException("User with this e-mail not found"));
        var book = bookRepository.findById(loanRequestDTO.getBook().getId())
                .orElseThrow(() -> new EntityNotFoundException("Book Not Found"));

        if (!book.isAvailable()) {
            throw new BookNotAvaliableForLoanException("Livro não está disponível para empréstimo");
        }

        var loan = new Loan();
        loan.setStartDate(LocalDate.now());
        loan.setEndDate(loan.getStartDate().plusWeeks(2));
        loan.setBook(book);
        loan.setStatus(StatusLoan.ACTIVE);

        Loan saveLoan =  loanRepository.save(loan);
        book.setAvailable(false); // Marca livro como emprestado

        return LoanMapper.toDto(saveLoan);
    }

    @Override
    public Page<LoanResponseDTO> findAllLoans(Pageable pageable) {
        Page<Loan> loanPages = loanRepository.findAll(pageable);
        List<Loan> loans = loanPages.getContent();
        return LoanMapper.toListDto(loans);
    }

    @Override
    public LoanResponseDTO findLoanById(Long id) {
        var loan = loanRepository.findById(id).orElseThrow(() -> new LoanException("Empréstimo não encontrado"));
        return LoanMapper.toDto(loan);
    }

    @Transactional
    @Override
    public void returnLoan(Long id) {
        var loan = loanRepository.findById(id).orElseThrow((
        ) -> new LoanException("Empréstimo não encontrado"));
        var book = loan.getBook();

        loan.calculateFine();

        loan.setStatus(StatusLoan.ACTIVE);
        book.setAvailable(true); // Marca o livro como disponível
        loanRepository.save(loan);
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteLoan(Long id) {
        var loan = loanRepository.findById(id).orElseThrow(() -> new LoanException("Empréstimo não encontrado"));
        var book = loan.getBook();

        // Verifica se o livro está associado ao empréstimo
        if (book != null) {
            loanRepository.delete(loan); // Deleta o empréstimo
        } else {
            throw new LoanException("Não é possível deletar o empréstimo. O livro está associado ao empréstimo.");
        }
    }
}
