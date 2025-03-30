package br.com.judev.bibliotec.dtos.responseDto;

import br.com.judev.bibliotec.entity.enums.Role;

import lombok.Data;

@Data
public class CreateUserResponseDTO {
     private Long id;
    private String completeName;
    private String email;
    private String password;
//    private String confirmPassword;
    private Role role;
    private boolean emailConfirmation;
    private String confirmationCode;
    private boolean activated;
 //   private AddressDTO addressDTO;
}
