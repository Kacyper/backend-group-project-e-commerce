package com.kodilla.ecommercee.validator;

import com.kodilla.ecommercee.exception.EmailAlreadyConfirmedException;
import com.kodilla.ecommercee.exception.TokenExpiredException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenValidator {
    public void validateConfirmationTime(final LocalDateTime expiresAt)
            throws TokenExpiredException {
        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException();
        }
    }
    public void validateIfAlreadyConfirmed(final LocalDateTime confirmedAt)
            throws EmailAlreadyConfirmedException {
        if (confirmedAt != null) {
            throw new EmailAlreadyConfirmedException();
        }
    }
}
