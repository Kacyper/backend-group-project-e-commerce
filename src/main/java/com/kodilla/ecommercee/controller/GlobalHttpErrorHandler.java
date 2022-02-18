package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.exception.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    //Cart entity exception handlers
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Object> handleCartNotFoundException(CartNotFoundException cartNotFoundException) {
        return new ResponseEntity<>("Cart with given id doesn't exist or can't be found in repository", HttpStatus.NOT_FOUND);
    }

    //Product entity exception handlers
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

    //Order entity exception handlers
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleObjectNotFoundException(OrderNotFoundException orderNotFoundException) {
        return new ResponseEntity<>("Order with given id doesn't exist or can't be found", HttpStatus.NOT_FOUND);
    }

    //Group entity exception handlers
    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<Object> handleGroupNotFoundException(GroupNotFoundException groupNotFoundException ) {
        return new ResponseEntity<>("Group with given id doesn't exist or can't be found in the repository", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GroupExistInRepositoryException.class)
    public ResponseEntity<Object> handleExistInRepositoryException(GroupExistInRepositoryException groupExistInRepositoryException) {
        return new ResponseEntity<>("Group with given name exist in the repository", HttpStatus.CONFLICT);
    }

    //User entity exception handlers
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>("User with given id doesn't exist or can't be found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserExistsInRepositoryException.class)
    public ResponseEntity<Object> handleUserExistsInRepositoryException(UserExistsInRepositoryException userExistsInRepositoryException) {
        return new ResponseEntity<>("User already exists.", HttpStatus.CONFLICT);
    }
}
