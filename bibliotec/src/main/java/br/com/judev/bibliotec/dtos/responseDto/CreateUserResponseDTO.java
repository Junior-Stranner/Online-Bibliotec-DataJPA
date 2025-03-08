package br.com.judev.bibliotec.dtos.responseDto;

import br.com.judev.bibliotec.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserResponseDTO {
    private Long id;
    private String completeName;
    private String email;
    private String password;
    private String confirmPassword;
    private Role role;
    private boolean emailConfirmation;
    private String confirmationCode;
}
