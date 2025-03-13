package br.com.judev.bibliotec.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    // Geração do token JWT
    public String generateToken(UserDetails userDetails) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-api") // O emissor do token
                    .withSubject(userDetails.getUsername()) // O assunto do token (geralmente o email do usuário)
                    .withExpiresAt(generateExpirationDate()) // Data de expiração
                    .sign(algorithm); // Assinatura com algoritmo HMAC256
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar o token", exception);
        }
    }

    // Método para gerar a data de expiração do token.
    public Instant generateExpirationDate() {
        // Define a expiração do token para 12 horas a partir do momento atual.
        return LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.of("-3"));
    }

    // Método para validar o token JWT.
    public String validateToken(String token) {
        try {
            // Algoritmo de assinatura HMAC com a chave secreta.
            var algorithm = Algorithm.HMAC256(secret);

            // Configura a validação do token com o algoritmo HMAC256 e o emissor esperado.
            return JWT.require(algorithm)
                    .withIssuer("login-auth-api") // Verifica se o emissor do token é o esperado.
                    .build() // Constrói a verificação do token.
                    .verify(token) // Verifica a validade do token.
                    .getSubject(); // Retorna o assunto do token (geralmente o email do usuário).
        } catch (JWTVerificationException e) {
            // Caso o token não seja válido ou ocorra algum erro de verificação, retorna null.
            return null;
        }
    }

}
