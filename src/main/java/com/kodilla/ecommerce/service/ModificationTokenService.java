package com.kodilla.ecommerce.service;

import com.kodilla.ecommerce.domain.ModificationToken;
import com.kodilla.ecommerce.repository.ModificationTokenRepository;
import com.kodilla.ecommerce.domain.User;
import com.kodilla.ecommerce.exception.ModificationTokenNotFoundException;
import com.kodilla.ecommerce.exception.ModificationTokenNotValidException;
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

    public void checkIfModificationTokenValid(final String token) throws ModificationTokenNotFoundException, ModificationTokenNotValidException {
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
