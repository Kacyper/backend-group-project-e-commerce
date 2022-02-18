package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.UserDto;
import com.kodilla.ecommercee.exception.CartNotFoundException;
import com.kodilla.ecommercee.exception.UserExistsInRepositoryException;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.mapper.UserMapper;
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
    private final UserMapper userMapper;

    public User createUser(final UserDto userDto) throws UserExistsInRepositoryException, CartNotFoundException {
        if (userRepository.existsUserByUsername(userDto.getUsername())) {
            throw  new UserExistsInRepositoryException();
        } else {
            Cart userCart = Cart.builder().build();
            cartRepository.save(userCart);
            userDto.setIdCart(userCart.getId());
            User user = userMapper.mapToUser(userDto);
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
