package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.ModificationToken;
import com.kodilla.ecommercee.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModificationTokenRepository extends JpaRepository<ModificationToken, Long> {
    Optional<ModificationToken> findByToken(String token);
    List<ModificationToken> findAllByAppUser(User user);
}
