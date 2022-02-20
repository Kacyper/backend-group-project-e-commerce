package com.kodilla.ecommerce.controller;

import com.kodilla.ecommerce.exception.*;
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

    @ExceptionHandler(EmptyProductRepositoryException.class)
    public ResponseEntity<Object> handleEmptyProductRepositoryException(EmptyProductRepositoryException emptyProductRepositoryException) {
        return new ResponseEntity<>("There is no available products in the repository", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductExistInRepositoryException.class)
    public ResponseEntity<Object> handleProductExistInRepositoryException(ProductExistInRepositoryException productExistInRepositoryException) {
        return new ResponseEntity<>("Product with given name exist in the repository", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProductNameIsEmptyException.class)
    public ResponseEntity<Object> handleProductNameIsEmptyException(ProductNameIsEmptyException productNameIsEmptyException) {
        return new ResponseEntity<>("Product name can not be empty", HttpStatus.NOT_ACCEPTABLE);
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
        return new ResponseEntity<>("Given userName is already taken", HttpStatus.CONFLICT);
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

    @ExceptionHandler(IllegalPasswordFormatException.class)
    public ResponseEntity<String> handleIllegalPasswordFormatException() {
        return new ResponseEntity<>("Your password should contains at least 6 signs!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ModificationTokenNotValidException.class)
    public ResponseEntity<String> handleModificationTokenNotValidException() {
        return new ResponseEntity<>("Your modification token expired! Get new one!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ModificationTokenNotFoundException.class)
    public ResponseEntity<String> handleModificationTokenNotFoundException() {
        return new ResponseEntity<>("Cannot find modification token! Get new one!", HttpStatus.BAD_REQUEST);
    }
}
