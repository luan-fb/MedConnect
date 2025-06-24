package com.medconecct.medconecct.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.replace("Bearer ", "");

            if (jwtUtil.isTokenValid(token)) {
                String email = jwtUtil.getUsername(token);
                String role = jwtUtil.getRole(token); // Ex: "ROLE_PACIENTE"

                // Criamos a lista de permissões a partir da 'role' lida do token
                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

                // Passamos a lista de permissões ao criar o objeto de autenticação
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null,
                        authorities);

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
