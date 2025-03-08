package br.com.judev.bibliotec.service;

import br.com.judev.bibliotec.dtos.requestDto.CreateUserRequestDTO;
import br.com.judev.bibliotec.dtos.responseDto.CreateUserResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

 CreateUserResponseDTO createUser(CreateUserRequestDTO request);
}
