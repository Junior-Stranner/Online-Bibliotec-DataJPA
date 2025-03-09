package br.com.judev.bibliotec.dtos.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "E-mail is required.")
    @Email(message = "Invalid email format. Please enter a valid email address.",
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;
    @NotBlank(message = "Password cannot be empty or just be wihgt spaces")
    @Size(min = 6, max = 30)
    @NotBlank(message = "Password is required.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "Password must have at least 6 characters, " +
            "one uppercase letter, one lowercase letter and one number. Ex: Password123")
    private String password;
}
