package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.exception.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Object> handleCartNotFoundException(CartNotFoundException cartNotFoundException) {
        return new ResponseEntity<>("Cart with given id doesn't exist or can't be found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException productNotFoundException) {
        return new ResponseEntity<>("Product with given id doesn't exist or can't be found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>("User with given id doesn't exist or can't be found", HttpStatus.NOT_FOUND);
    }
}
