package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public User blockUser(final Long idUser, final User user) throws UserNotFoundException{
        User userFromDb = userRepository.findById(idUser).orElseThrow(UserNotFoundException::new);

        userFromDb.setEnabled(false);
        return userFromDb;
    }




}
