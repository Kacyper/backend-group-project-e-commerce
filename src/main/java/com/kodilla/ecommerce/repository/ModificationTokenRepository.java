package com.kodilla.ecommerce.repository;

import com.kodilla.ecommerce.domain.ModificationToken;
import com.kodilla.ecommerce.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModificationTokenRepository extends JpaRepository<ModificationToken, Long> {
    Optional<ModificationToken> findByToken(String token);
    List<ModificationToken> findAllByAppUser(User user);
}
