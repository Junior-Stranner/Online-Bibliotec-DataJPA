package br.com.judev.bibliotec.infra.config;

import br.com.judev.bibliotec.infra.security.CustomUserDetailsService;
import br.com.judev.bibliotec.infra.security.SecurityFilter;
import br.com.judev.bibliotec.infra.security.TokenService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@SecurityScheme(
        name = "Bearer Auth",
        description = "JWT Authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SecurityConfig {

    private final TokenService tokenService;
    private final CustomUserDetailsService userDetailsService;

    private static final String[] DOCUMENTATION_OPENAPI = {
            "/docs/index.html", "/docs-park.html", "/docs-park/**",
            "/v3/api-docs/**", "/swagger-ui-custom.html", "/swagger-ui.html",
            "/swagger-ui/**", "/**.html", "/webjars/**",
            "/configuration/**", "/swagger-resources/**", "/h2-console/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/register/client").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/register/admin").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()      // Permitir acesso sem autenticação
                        .requestMatchers(DOCUMENTATION_OPENAPI).permitAll()  // Permitir acesso à documentação
                        .anyRequest().authenticated() // Exigir autenticação para as outras rotas
                )
                .addFilterAfter(securityFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public SecurityFilter securityFilter() {
        return new SecurityFilter(tokenService, userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
