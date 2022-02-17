package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.exception.UserExistsInRepositoryException;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DbServiceUser {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public User createUser(final User user) throws UserExistsInRepositoryException {
        if (userRepository.existsUserByUsername(user.getUsername())) {
            throw  new UserExistsInRepositoryException();
        } else {
            Cart userCart = Cart.builder().build();
            user.setCart(userCart);
            cartRepository.save(userCart);
            return userRepository.save(user);
        }
    }

    public User blockUser(final Long idUser) throws UserNotFoundException {
        User userFromDb = userRepository.findById(idUser).orElseThrow(UserNotFoundException::new);
        userFromDb.setEnabled(false);
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
}
