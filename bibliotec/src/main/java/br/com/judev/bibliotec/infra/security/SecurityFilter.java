package br.com.judev.bibliotec.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor

public class SecurityFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UserDetailsService userDetailsService;


    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getServletPath();

        // Permitir acesso sem autenticação para essas rotas
        if (path.equals("/api/v1/auth/register") || path.equals("/api/v1/auth/login") ||
                path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") || path.equals("/favicon.ico")) {
            filterChain.doFilter(request, response);
            return;
        }
        // A partir daqui, o código de verificação do token
        var token = this.recoverToken(request);
        if (token != null) {
            String login = tokenService.validateToken(token);
            if (login != null) {
                UserDetails user = userDetailsService.loadUserByUsername(login);
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }


    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }



}
