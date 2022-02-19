package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.RegistrationRequestDto;
import com.kodilla.ecommercee.exception.*;
import com.kodilla.ecommercee.service.RegistrationService;
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
            throws TokenNotFoundException, EmaiAlreadyConfirmedException,
            TokenExpiredException {
        return ResponseEntity.ok(registrationService.confirmToken(token));
    }
}
