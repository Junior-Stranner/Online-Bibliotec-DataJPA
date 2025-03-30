package br.com.judev.bibliotec.service.serviceImpl;

import br.com.judev.bibliotec.dtos.mapper.LoanMapper;
import br.com.judev.bibliotec.dtos.requestDto.LoanRequestDTO;
import br.com.judev.bibliotec.dtos.responseDto.LoanResponseDTO;
import br.com.judev.bibliotec.entity.Loan;
import br.com.judev.bibliotec.entity.enums.StatusLoan;
import br.com.judev.bibliotec.infra.exceptions.BookNotAvaliableForLoanException;
import br.com.judev.bibliotec.infra.exceptions.LoanNotExistsException;
import br.com.judev.bibliotec.infra.exceptions.UserEmailNotFoundException;
import br.com.judev.bibliotec.repository.AddressRepository;
import br.com.judev.bibliotec.repository.LoanRepository;
import br.com.judev.bibliotec.repository.BookRepository;
import br.com.judev.bibliotec.repository.UserRepository;
import br.com.judev.bibliotec.service.LoanService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;


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

        var address = addressRepository.findById(loanRequestDTO.getAddress().getId())
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        var loan = new Loan();
        loan.setStartDate(LocalDate.now());
        loan.setEndDate(loan.getStartDate().plusWeeks(2));
        loan.setBook(book);
        loan.setStatus(StatusLoan.REQUESTED);
        loan.setUser(user);
        loan.setAddress(address);


        Loan saveLoan =  loanRepository.save(loan);
        book.markAsUnavailable(); // marked , Book is not avaliable
        bookRepository.save(book);


        return LoanMapper.toDto(saveLoan);
    }

    @Override
    public List<LoanResponseDTO> findAllLoans(Pageable pageable) {
        Page<Loan> loanPages = loanRepository.findAll(pageable);
        List<Loan> loans = loanPages.getContent();
        return LoanMapper.toListDto(loans);
    }

    @Override
    public LoanResponseDTO findLoanById(Long id) {
        var loan = loanRepository.findById(id).orElseThrow(() -> new LoanNotExistsException("Empréstimo não encontrado"));
        return LoanMapper.toDto(loan);
    }

    @Transactional
    @Override
    public void returnLoan(Long id) {
        var loan = loanRepository.findById(id).orElseThrow(
                () -> new LoanNotExistsException("Empréstimo não encontrado"));
        var book = loan.getBook();

        loan.calculateFine();

        loan.setStatus(StatusLoan.REQUESTED);
        book.setAvailable(true); // Marca o livro como disponível
        loanRepository.save(loan);
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteLoan(Long id) {
        var loan = loanRepository.findById(id).orElseThrow(
                () -> new LoanNotExistsException("Empréstimo não encontrado"));
        var book = loan.getBook();
        if (book != null) {
            loanRepository.delete(loan);
        } else {
            throw new LoanNotExistsException("Não é possível deletar o empréstimo. O livro está associado ao empréstimo.");
        }
    }
}
