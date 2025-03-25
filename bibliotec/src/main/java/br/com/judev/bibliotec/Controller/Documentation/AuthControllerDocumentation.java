package br.com.judev.bibliotec.controller;

import br.com.judev.bibliotec.dtos.requestDto.CreateUserRequestDTO;
import br.com.judev.bibliotec.dtos.requestDto.LoginRequestDTO;
import br.com.judev.bibliotec.dtos.responseDto.CreateUserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public interface AuthControllerDocumentation {

    @Operation(summary = "Autenticar usuário", description = "Realiza a autenticação do usuário e retorna um token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso."),
            @ApiResponse(responseCode = "401", description = "E-mail ou senha inválidos."),
            @ApiResponse(responseCode = "403", description = "Usuário desativado. Verifique seu e-mail.")
    })
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request);


    @Operation(summary = "Registrar novo usuário", description = "Cria um novo usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados informados."),
            @ApiResponse(responseCode = "409", description = "E-mail já cadastrado.")
    })
    public ResponseEntity<CreateUserResponseDTO> register(@Valid @RequestBody CreateUserRequestDTO dto);


    @Operation(summary = "Registrar novo usuário", description = "Cria um novo usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados informados."),
            @ApiResponse(responseCode = "409", description = "E-mail já cadastrado.")
    })
    public ResponseEntity<CreateUserResponseDTO> registerAdmin(@Valid @RequestBody CreateUserRequestDTO dto);

    }
