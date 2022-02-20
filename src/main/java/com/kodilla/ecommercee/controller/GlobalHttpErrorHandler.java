package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.exception.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Object> handleCartNotFoundException(CartNotFoundException cartNotFoundException) {
        return new ResponseEntity<>("Cart with given id doesn't exist or can't be found in repository", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException productNotFoundException) {
        return new ResponseEntity<>("Product with given id doesn't exist or can't be found in repository", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotAvailableException.class)
    public ResponseEntity<Object> handleProductNotAvailableException(ProductNotAvailableException productNotAvailableException) {
        return new ResponseEntity<>("Product with given id is not available at the moment", HttpStatus.GONE);
    }

    @ExceptionHandler(ProductNotFoundInCartException.class)
    public ResponseEntity<Object> handleProductNotFoundInCartException(ProductNotFoundInCartException productNotFoundInCartException) {
        return new ResponseEntity<>("Product with given id doesn't exist or can't be found in the Cart with given id", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleObjectNotFoundException(OrderNotFoundException orderNotFoundException) {
        return new ResponseEntity<>("Order with given id doesn't exist or can't be found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>("User with given id doesn't exist or can't be found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserExistsInRepositoryException.class)
    public ResponseEntity<Object> handleUserExistsInRepositoryException(UserExistsInRepositoryException userExistsInRepositoryException) {
        return new ResponseEntity<>("User already exists.", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<Object> handlePasswordNotMatchException() {
        return new ResponseEntity<>("Password and repeat password fields should match!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsInDatabaseException.class)
    public ResponseEntity<Object> handleEmailAlreadyExistsInDatabaseException() {
        return new ResponseEntity<>("You have to pick another login! This one already occupied!", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailNotValidException.class)
    public ResponseEntity<Object> handleEmailNotValidException() {
        return new ResponseEntity<>("Type in valid email address!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<Object> handleTokenNotFoundException() {
        return new ResponseEntity<>("Couldn't find confirmation token in database!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyConfirmedException.class)
    public ResponseEntity<Object> handleEmaiAlreadyConfirmedException() {
        return new ResponseEntity<>("Email has been already confirmed!", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Object> handleTokenExpiredException() {
        return new ResponseEntity<>("Token already expired!", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({IllegalPasswordFormatException.class})
    public ResponseEntity<String> handleIllegalPasswordFormatException(IllegalPasswordFormatException e) {
        return new ResponseEntity<>("Your password should contains at least 6 signs!", HttpStatus.BAD_REQUEST);
    }
}
