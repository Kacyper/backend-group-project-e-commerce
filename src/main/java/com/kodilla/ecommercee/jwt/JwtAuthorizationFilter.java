package com.kodilla.ecommercee.jwt;

import com.auth0.jwt.JWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.kodilla.ecommercee.jwt.JwtConstant.ACCESS_TOKEN_HEADER;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtAlgorithm algorithm;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        if (authentication == null){
            filterChain.doFilter(request,response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String token = request.getHeader(ACCESS_TOKEN_HEADER);

        if (token != null){
            String username = JWT.require(algorithm.getAlgorithm())
                    .build()
                    .verify(token)
                    .getSubject();
            System.out.println(username);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            log.warn("Username: " + userDetails.getUsername());
            log.warn("Pass: " + userDetails.getPassword());
            userDetails.getAuthorities().forEach(e -> log.warn(e.getAuthority()));
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null,userDetails.getAuthorities());
        }
        return null;
    }
}
