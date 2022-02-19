package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.LoginCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public ResponseEntity<Void> login(@RequestBody LoginCredentials request){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
