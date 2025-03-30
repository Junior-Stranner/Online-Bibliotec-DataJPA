package br.com.judev.bibliotec.service;

import br.com.judev.bibliotec.entity.Loan;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    public void sendOrderConfirmation(Loan loan);

    public void sendConfirmationCode(String email, String confirmationCode);

    }
