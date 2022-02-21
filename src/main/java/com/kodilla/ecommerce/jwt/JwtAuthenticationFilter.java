package com.kodilla.ecommerce.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodilla.ecommerce.domain.LoginCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginCredentials credentials = objectMapper.readValue(request.getInputStream(), LoginCredentials.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());
            setDetails(request, token);

            return this.getAuthenticationManager().authenticate(token);

        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
