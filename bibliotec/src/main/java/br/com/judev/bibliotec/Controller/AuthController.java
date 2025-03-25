package br.com.judev.bibliotec.Controller;

import br.com.judev.bibliotec.dtos.requestDto.CreateUserRequestDTO;
import br.com.judev.bibliotec.dtos.requestDto.LoginRequestDTO;
import br.com.judev.bibliotec.dtos.responseDto.CreateUserResponseDTO;
import br.com.judev.bibliotec.dtos.responseDto.LoginResponseDTO;
import br.com.judev.bibliotec.infra.security.TokenService;
import br.com.judev.bibliotec.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController implements br.com.judev.bibliotec.controller.AuthControllerDocumentation {
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            final String jwt = tokenService.generateToken(userDetails);
            return ResponseEntity.ok(new LoginResponseDTO(jwt));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha inválidos.");
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário desativado. Verifique seu e-mail.");
        }
    }

    @PostMapping("/register/client")
    public ResponseEntity<CreateUserResponseDTO> register(@Valid @RequestBody CreateUserRequestDTO dto) {
        CreateUserResponseDTO responseDTO = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<CreateUserResponseDTO> registerAdmin(@Valid @RequestBody CreateUserRequestDTO dto) {
        CreateUserResponseDTO responseDTO = userService.createUserAdmin(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
