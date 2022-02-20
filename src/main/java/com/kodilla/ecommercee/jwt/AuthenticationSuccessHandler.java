package com.kodilla.ecommercee.jwt;

import com.auth0.jwt.JWT;
import com.kodilla.ecommercee.repository.UserRepository;
import com.kodilla.ecommercee.service.ModificationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Date;

import static com.kodilla.ecommercee.jwt.JwtConstant.*;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtAlgorithm algorithm;
    private final ModificationTokenService modificationTokenService;
    private final UserRepository userRepository;

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication){
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String modificationToken = modificationTokenService.createModificationTokenAndSaveToDb();
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(java.sql.Date.valueOf(LocalDate.now().plusDays(TOKEN_EXPIRATION_TIME_DAYS)))
                .sign(algorithm.getAlgorithm());

        response.setHeader(ACCESS_TOKEN_HEADER, PREFIX.concat(token));
        response.setHeader("modification_token", modificationToken);
    }

}
