package com.kodilla.ecommerce.service;

import com.kodilla.ecommerce.domain.ConfirmationToken;
import com.kodilla.ecommerce.domain.RegistrationRequestDto;
import com.kodilla.ecommerce.email.EmailBuilder;
import com.kodilla.ecommerce.email.EmailService;
import com.kodilla.ecommerce.exception.*;
import com.kodilla.ecommerce.mapper.AppUserMapper;
import com.kodilla.ecommerce.validator.EmailValidator;
import com.kodilla.ecommerce.validator.PasswordEqualityValidator;
import com.kodilla.ecommerce.validator.PasswordFormatValidator;
import com.kodilla.ecommerce.validator.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    public static final String EMAIL_WITH_LINK_JUST_SEND = "Email with confirmation request just send";
    public static final String EMAIL_SUCCESSFULLY_CONFIRMED = "Email successfully confirmed!";
    private final EmailValidator emailValidator;
    private final PasswordEqualityValidator passwordEqualityValidator;
    private final PasswordFormatValidator passwordFormatValidator;
    private final DbServiceUser appUserService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private final TokenValidator tokenValidator;


    public String register(RegistrationRequestDto request)
            throws EmailNotValidException, PasswordNotMatchException, IllegalPasswordFormatException,
            EmailAlreadyExistsInDatabaseException {
        emailValidator.validate(request.getEmail());
        passwordFormatValidator.validate(request.getPassword());
        passwordEqualityValidator.validate(request.getPassword(), request.getRepeatPassword());

        String link = "http://localhost:8080/api/v1/registration/confirm?token="
                + appUserService.signUpUser(AppUserMapper.mapToAppUser(request));
        emailService.send(
                request.getEmail(),
                EmailBuilder.buildEmail("Stranger", link));
        return EMAIL_WITH_LINK_JUST_SEND;
    }

    @Transactional
    public String confirmToken(final String token)
            throws TokenNotFoundException, EmailAlreadyConfirmedException,
            TokenExpiredException {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(TokenNotFoundException::new);
        tokenValidator.validateIfAlreadyConfirmed(confirmationToken.getConfirmedAt());
        tokenValidator.validateConfirmationTime(confirmationToken.getExpiresAt());
        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getAppUser().getUsername());
        return EMAIL_SUCCESSFULLY_CONFIRMED;
    }
}

