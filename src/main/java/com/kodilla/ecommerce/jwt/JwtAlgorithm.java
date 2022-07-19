package com.kodilla.ecommerce.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtAlgorithm {
    private final Algorithm algorithm;

    public JwtAlgorithm() {
        this.algorithm = Algorithm.HMAC512("secret");
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }
}
