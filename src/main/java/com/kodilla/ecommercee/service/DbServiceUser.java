package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.ConfirmationToken;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.exception.EmailAlreadyExistsInDatabaseException;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.repository.CartRepository;
import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("userDetailsService")
@RequiredArgsConstructor
public class DbServiceUser implements UserDetailsService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("There is no user with given email in database!"));
    }

    @Transactional
    public void enableAppUser(final String email) {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("There is no user with given email in database!"))
                .setEnabled(true);
    }

    @Transactional
    public String signUpUser(final User user)
            throws EmailAlreadyExistsInDatabaseException {
        boolean alreadyExists = userRepository
                .findByEmail(user.getUsername())
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

    public User blockUser(final Long idUser) throws UserNotFoundException {
        User userFromDb = userRepository.findById(idUser).orElseThrow(UserNotFoundException::new);
        userFromDb.setEnabled(false);
        userRepository.save(userFromDb);
        return userFromDb;
    }

    public String generateKey(String username, String password) throws UserNotFoundException {
        User userFromDb = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);
        if (password.equals(userFromDb.getPassword())) {
            userFromDb.setKeyGenerationTime(System.currentTimeMillis());
            userFromDb.setUserKey(UUID.randomUUID().toString());
            userRepository.save(userFromDb);
            return userFromDb.getUserKey();
        }
        return "Wrong user credentials.";
    }
}
