package br.com.judev.bibliotec.infra.config;

import br.com.judev.bibliotec.infra.security.CustomUserDetailsService;
import br.com.judev.bibliotec.infra.security.TokenService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@SecurityScheme(
        name = "Bearer Auth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SecurityConfig {

    private static final String[] DOCUMENTATION_OPENAPI = {
            "/docs/index.html", "/docs-park.html", "/docs-park/**",
            "/v3/api-docs/**", "/swagger-ui-custom.html", "/swagger-ui.html",
            "/swagger-ui/**", "/**.html", "/webjars/**",
            "/configuration/**", "/swagger-resources/**","/h2-console/**"

    };
    private  TokenService tokenService;
    private  CustomUserDetailsService userDetailsService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http.csrf(csrf -> csrf.disable())//(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .formLogin(form -> form.disable())  // Desabilita o formulário de login padrão do Spring Security
                .httpBasic(basic -> basic.disable())  // Desabilita a autenticação básica HTTP
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/index").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuario/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuario/login").permitAll()
                        .requestMatchers("/docs/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(DOCUMENTATION_OPENAPI).permitAll()
                        .anyRequest().authenticated()
                )
                /*  .logout(logout -> logout
                          .logoutUrl("/logout")
                          .logoutSuccessUrl("/index")
                          .invalidateHttpSession(true)
                          .deleteCookies("JSESSIONID")*/

                .addFilterAfter(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter(userDetailsService, tokenService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean    // Define o gerenciador de autenticação, permitindo a integração com o processo de autenticação configurado
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(Cus) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
