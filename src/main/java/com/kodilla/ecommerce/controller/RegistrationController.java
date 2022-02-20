package com.kodilla.ecommerce.controller;

import com.kodilla.ecommerce.domain.RegistrationRequestDto;
import com.kodilla.ecommerce.exception.*;
import com.kodilla.ecommerce.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegistrationRequestDto request)
            throws EmailNotValidException, PasswordNotMatchException,
            EmailAlreadyExistsInDatabaseException, IllegalPasswordFormatException {
        return ResponseEntity.ok(registrationService.register(request));
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmEmail(@RequestParam String token)
            throws TokenNotFoundException, EmailAlreadyConfirmedException,
            TokenExpiredException {
        return ResponseEntity.ok(registrationService.confirmToken(token));
    }
}
