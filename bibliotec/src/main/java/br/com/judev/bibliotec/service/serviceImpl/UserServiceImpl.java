package br.com.judev.bibliotec.service.serviceImpl;

import br.com.judev.bibliotec.dtos.mapper.UserMapper;
import br.com.judev.bibliotec.dtos.requestDto.CreateUserRequestDTO;
import br.com.judev.bibliotec.dtos.responseDto.CreateUserResponseDTO;
import br.com.judev.bibliotec.entity.Role;
import br.com.judev.bibliotec.entity.User;
import br.com.judev.bibliotec.repository.UserRepository;
import br.com.judev.bibliotec.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public CreateUserResponseDTO createUser(CreateUserRequestDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail já cadastrado.");
        }
        User newUser = UserMapper.toUser(request);
        String encryptedPassword  = passwordEncoder.encode(request.getPassword());
        newUser.setPassword(encryptedPassword);
        newUser.setRole(Role.Client);
        User saveUser = userRepository.save(newUser);

        return UserMapper.toDto(saveUser);

    }
}
