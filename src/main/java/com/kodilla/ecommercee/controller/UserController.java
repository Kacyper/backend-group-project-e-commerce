package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.UserDto;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.service.DbServiceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@EnableScheduling
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final DbServiceUser dbServiceUser;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getUsers(){
        return userMapper.mapToListDto(dbServiceUser.getUsers());
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) throws UserNotFoundException{
        return userMapper.mapToUserDto(dbServiceUser.getUser(id));
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto){
        return userMapper.mapToUserDto(dbServiceUser.createUser(userMapper.mapToUser(userDto)));
    }

    @PutMapping("/{id}")
    public UserDto blockUser (@PathVariable Long id) throws UserNotFoundException{
        UserDto userDto = userMapper.mapToUserDto(dbServiceUser.getUser(id));
        return userMapper.mapToUserDto(dbServiceUser.blockUser(id, userMapper.mapToUser(userDto)));
    }

    //do przekminy
//    @PutMapping("/{id}")
//    public UserDto unblockUser (@PathVariable Long id, @RequestParam Long key) throws UserNotFoundException{
//        UserDto userDto1 = userMapper.mapToUserDto(dbServiceUser.getUser(id));
//        return userMapper.mapToUserDto(dbServiceUser.unblockUser(id, userMapper.mapToUser(userDto)));
//    }

    @PostMapping("/generateKey/{id}")
    public String generateKey(@PathVariable Long id, @RequestParam String username, @RequestParam String password)
            throws UserNotFoundException {
        UserDto userFromDB = userMapper.mapToUserDto(dbServiceUser.getUser(id));
        if (
                username == userFromDB.getUsername()
                        && password == userFromDB.getPassword()
        ) {
            if (userFromDB.isEnabled() == true) {
                return "this user is enabled, no need for a key";
            } else {
                return UUID.randomUUID().toString();
            }
        } else {
            return "Wrong user credentials";
        }
    }
}