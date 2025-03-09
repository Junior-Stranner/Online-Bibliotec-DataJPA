package br.com.judev.bibliotec.dtos.responseDto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token;

    public LoginResponseDTO(String token) {
        this.token = token;
    }
}
