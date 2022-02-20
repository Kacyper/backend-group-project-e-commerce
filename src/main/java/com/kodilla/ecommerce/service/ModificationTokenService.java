package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.ModificationToken;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.exception.ModificationTokenNotFoundException;
import com.kodilla.ecommercee.exception.ModificationTokenNotValidException;
import com.kodilla.ecommercee.repository.ModificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ModificationTokenService {
    private final ModificationTokenRepository modificationTokenRepository;
    private final DbServiceUser serviceUser;

    public String createModificationTokenAndSaveToDb() {
        User currentlyLoggedInUser = serviceUser.getCurrentlyLoggedInUser();
        String token = UUID.randomUUID().toString();
        ModificationToken modificationToken = ModificationToken.builder()
                .appUser(currentlyLoggedInUser)
                .token(token)
                .expiresAt(LocalDateTime.now().plusMinutes(60))
                .build();
        modificationTokenRepository.save(modificationToken);
        return token;
    }

    public void checkIfModificationTokenValid(final String token)
            throws ModificationTokenNotFoundException, ModificationTokenNotValidException {
        User currentlyLoggedInUser = serviceUser.getCurrentlyLoggedInUser();
        ModificationToken modificationToken = modificationTokenRepository.findAllByAppUser(currentlyLoggedInUser)
                .stream()
                .filter(e -> e.getToken().equals(token))
                .findFirst()
                .orElseThrow(ModificationTokenNotFoundException::new);

        if(modificationToken.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new ModificationTokenNotValidException();
    }
}
