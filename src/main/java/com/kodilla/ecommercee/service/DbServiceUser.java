package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.ConfirmationToken;
import com.kodilla.ecommercee.domain.User;

import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.repository.CartRepository;
import java.util.UUID;

import java.util.regex.Pattern;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DbServiceUser implements UserDetailsService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("There is no user with given email in database!"));
    }

    @Transactional
    public void enableAppUser(final String email) {
        userRepository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("There is no user with given email in database!"))
                .setEnabled(true);
    }

    @Transactional
    public String signUpUser(final User user)
            throws EmailAlreadyExistsInDatabaseException {
        boolean alreadyExists = userRepository
                .findByUsername(user.getUsername())
                .isPresent();
        if (alreadyExists) throw new EmailAlreadyExistsInDatabaseException();

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Cart userCart = Cart.builder().build();
        user.setCart(userCart);
        cartRepository.save(userCart);
        userRepository.save(user);

        ConfirmationToken confirmationToken = confirmationTokenService.createConfirmationToken(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return confirmationToken.getToken();
    }

    public User blockUser(final Long idUser) throws UserNotFoundException, UserAlreadyBlockedException {
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

    private void validateRequest(UserDto userDto) throws UserExistByEmailException, InvalidEmailException, UserNameIsEmptyException, UserExistsInRepositoryException, InvalidPasswordException {
        validateEmail(userDto.getEmail());
        validateUserName(userDto.getUsername());
        validatePassword(userDto.getPassword());
    }

    private void validateEmail(String emailAddress) throws InvalidEmailException, UserExistByEmailException {
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

    private void validateUserName(String userName) throws UserExistsInRepositoryException, UserNameIsEmptyException {
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
