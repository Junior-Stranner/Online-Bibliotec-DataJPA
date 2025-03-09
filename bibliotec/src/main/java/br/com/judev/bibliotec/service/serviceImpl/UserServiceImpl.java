package br.com.judev.bibliotec.service.serviceImpl;

import br.com.judev.bibliotec.dtos.requestDto.CreateUserRequestDTO;
import br.com.judev.bibliotec.dtos.responseDto.CreateUserResponseDTO;
import br.com.judev.bibliotec.repository.UserRepository;
import br.com.judev.bibliotec.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public CreateUserResponseDTO createUser(CreateUserRequestDTO request) {
    if(userRepository.findByEmail(request.getEmail())){

    }
        return null;
    }
}
