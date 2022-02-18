package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.UserDto;
import com.kodilla.ecommercee.exception.*;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class DbServiceUser {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final UserMapper userMapper;

    public User createUser(final UserDto userDto) throws Exception {
        validateRequest(userDto);
        Cart userCart = Cart.builder().build();
        cartRepository.save(userCart);
        userDto.setIdCart(userCart.getId());
        User user = userMapper.mapToUser(userDto);
        return userRepository.save(user);
    }

    public User blockUser(final Long idUser) throws Exception {
        User userFromDb = userRepository.findById(idUser).orElseThrow(UserNotFoundException::new);

        if (!userFromDb.isEnabled()) {
            throw new UserAlreadyBlockedException();
        }

        userFromDb.setEnabled(false);
        userFromDb.setActive(false);
        userRepository.save(userFromDb);
        return userFromDb;
    }

    public String generateKey(String username, String password) throws UserNotFoundException {
        User userFromDb = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        if (password.equals(userFromDb.getPassword())) {
            userFromDb.setKeyGenerationTime(System.currentTimeMillis());
            userFromDb.setUserKey(UUID.randomUUID().toString());
            userRepository.save(userFromDb);
            return userFromDb.getUserKey();
        }
        return "Wrong user credentials.";
    }

    private void validateRequest(UserDto userDto) throws Exception {
        validateEmail(userDto.getEmail());
        validateUserName(userDto.getUsername());
        validatePassword(userDto.getPassword());
    }

    private void validateEmail(String emailAddress) throws Exception {
        String regexPattern = "^(.+)@(\\S+)$";
        boolean isMatch = Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();

        if (!isMatch) {
            throw new InvalidEmailException();

        } else if (userRepository.existsUserByEmail(emailAddress)) {
            throw new UserExistByEmailException();
        }
    }

    private void validateUserName(String userName) throws Exception {
        if (userRepository.existsUserByUsername(userName)) {
            throw  new UserExistsInRepositoryException();

        } else if (userName.isEmpty()) {
            throw new UserNameIsEmptyException();
        }
    }

    private void validatePassword(String password) throws InvalidPasswordException {
        if (password.length() < 8) {
            throw new InvalidPasswordException();
        }
    }
}
