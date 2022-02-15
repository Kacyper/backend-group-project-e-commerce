package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DbServiceUser {

    private final UserRepository userRepository;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUser(final Long id) throws UserNotFoundException{
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User createUser(final User user){
        return userRepository.save(user);
    }

    public User blockUser(final Long idUser) throws UserNotFoundException{
        User userFromDb;
        userFromDb = userRepository.findById(idUser).orElseThrow(UserNotFoundException::new);
        userFromDb.setEnabled(false);
        userRepository.save(userFromDb);
        return userFromDb;
    }

//    public String unblockUser(final Long idUser, final String key) throws UserNotFoundException{
//        User userFromDb = userRepository.findById(idUser).orElseThrow(UserNotFoundException::new);
//        if(key.equals(userFromDb.getUserKey()) &&
//                (System.currentTimeMillis() - userFromDb.getKeyGenerationTime()) < 600000000L)
//        userFromDb.setEnabled(true);
//        userRepository.save(userFromDb);
//        return "xs";
//    }

    public String generateKey(Long id, String username, String password) throws UserNotFoundException{
        User userFromDb = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (username.equals(userFromDb.getUsername()) && password.equals(userFromDb.getPassword())) {
            if (userFromDb.isEnabled()) {
                return "Account already active!";
            }
            userFromDb.setKeyGenerationTime(System.currentTimeMillis());
            userFromDb.setUserKey(UUID.randomUUID().toString());
            userRepository.save(userFromDb);
            return userFromDb.getUserKey();
        }
        return "Wrong user credentials";
    }
}
