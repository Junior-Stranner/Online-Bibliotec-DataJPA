package br.com.judev.bibliotec.service.serviceImpl;

import br.com.judev.bibliotec.dtos.mapper.UserMapper;
import br.com.judev.bibliotec.dtos.requestDto.CreateUserRequestDTO;
import br.com.judev.bibliotec.dtos.responseDto.CreateUserResponseDTO;
import br.com.judev.bibliotec.entity.Role;
import br.com.judev.bibliotec.entity.User;
import br.com.judev.bibliotec.infra.exceptions.EmailAlreadyExistsException;
import br.com.judev.bibliotec.repository.UserRepository;
import br.com.judev.bibliotec.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public CreateUserResponseDTO createUser(CreateUserRequestDTO request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("E-mail já está cadastrado.");
        }

        User newUser = UserMapper.toUser(request);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(Role.Client);

        User savedUser = userRepository.save(newUser);
        return UserMapper.toDto(savedUser);
    }

}
