package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.ConfirmationToken;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.exception.EmailAlreadyExistsInDatabaseException;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.kodilla.ecommercee.exception.UserExistsInRepositoryException;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.repository.CartRepository;
import java.util.UUID;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return appUserRepository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("There is no user with given email in database!"));
    }

    @Transactional
    public void enableAppUser(final String email) {
        appUserRepository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("There is no user with given email in database!"))
                .setEnabled(true);
    }

    @Transactional
    public String signUpUser(final User appUser)
            throws EmailAlreadyExistsInDatabaseException {
        boolean alreadyExists = appUserRepository
                .findByUsername(appUser.getUsername())
                .isPresent();
        if (alreadyExists) throw new EmailAlreadyExistsInDatabaseException();

        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        appUserRepository.save(appUser);

        ConfirmationToken confirmationToken = confirmationTokenService.createConfirmationToken(appUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return confirmationToken.getToken();
    }

    public User getCurrentLoggedInAppUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return  (User) loadUserByUsername(username);
    }

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
