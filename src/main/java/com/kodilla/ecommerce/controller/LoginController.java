package com.kodilla.ecommerce.controller;

import com.kodilla.ecommerce.domain.LoginCredentials;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public void login(@RequestBody LoginCredentials request) {
    }
}
