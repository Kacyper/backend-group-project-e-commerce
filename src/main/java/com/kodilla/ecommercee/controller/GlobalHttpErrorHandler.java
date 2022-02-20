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
    public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException orderNotFoundException) {
        return new ResponseEntity<>("Order with given id doesn't exist or can't be found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<Object> handleGroupNotFoundException(GroupNotFoundException groupNotFoundException ) {
        return new ResponseEntity<>("Group with given id doesn't exist or can't be found in the repository", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GroupExistInRepositoryException.class)
    public ResponseEntity<Object> handleExistInRepositoryException(GroupExistInRepositoryException groupExistInRepositoryException) {
        return new ResponseEntity<>("Group with given name exist in the repository", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GroupNameIsEmptyException.class)
    public ResponseEntity<Object> handleGroupNameIsEmptyExceptionException(GroupNameIsEmptyException groupNameIsEmptyException) {
        return new ResponseEntity<>("Group name can not be empty", HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>("User with given id doesn't exist or can't be found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserExistsInRepositoryException.class)
    public ResponseEntity<Object> handleUserExistsInRepositoryException(UserExistsInRepositoryException userExistsInRepositoryException) {
        return new ResponseEntity<>("User already exists.", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Object> handleInvalidEmailException(InvalidEmailException invalidEmailException) {
        return new ResponseEntity<>("Given e-mail adress is not valid", HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UserExistByEmailException.class)
    public ResponseEntity<Object> handleUserExistByEmailException(UserExistByEmailException userExistByEmailException) {
        return new ResponseEntity<>("This email is connected to another user account", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNameIsEmptyException.class)
    public ResponseEntity<Object> handleUserNameIsEmptyException(UserNameIsEmptyException userNameIsEmptyException) {
        return new ResponseEntity<>("User name is obligatory", HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Object> handleInvalidPasswordException(InvalidPasswordException invalidPasswordException) {
        return new ResponseEntity<>("Password needs to consists of at least 8 characters", HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UserAlreadyBlockedException.class)
    public ResponseEntity<Object> handleUserAlreadyBlockedException(UserAlreadyBlockedException userAlreadyBlockedException) {
        return new ResponseEntity<>("User with given id is already blocked", HttpStatus.CONFLICT);
    }
}
