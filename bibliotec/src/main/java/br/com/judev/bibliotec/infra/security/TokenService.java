package br.com.judev.bibliotec.infra.security;

import br.com.judev.bibliotec.infra.exceptions.TokenCreationException;
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

    // Método para gerar um token JWT para o usuário.
    public String generateToken(UserDetails userDetails) throws TokenCreationException {
        try {
            if (secret == null || secret.isEmpty()) {
                throw new TokenCreationException("Secret key is not set. Check your application properties.");
            }

            var algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("login-auth-api")
                    .withSubject(userDetails.getUsername())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new TokenCreationException("Error while creating token" +e);
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
