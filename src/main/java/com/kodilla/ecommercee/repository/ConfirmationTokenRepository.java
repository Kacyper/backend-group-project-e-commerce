package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);
}
