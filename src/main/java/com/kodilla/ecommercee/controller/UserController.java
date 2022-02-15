package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.UserDto;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.service.DbServiceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.UUID;

@EnableScheduling
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final DbServiceUser dbServiceUser;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(){
        return ResponseEntity.ok(userMapper.mapToListDto(dbServiceUser.getUsers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) throws UserNotFoundException{
        return ResponseEntity.ok(userMapper.mapToUserDto(dbServiceUser.getUser(id)));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userMapper.mapToUserDto(dbServiceUser.createUser(userMapper.mapToUser(userDto))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> blockUser (@PathVariable Long id) throws UserNotFoundException{
        return ResponseEntity.ok(userMapper.mapToUserDto(dbServiceUser.blockUser(id)));
    }

    //do przekminy
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> unblockUser (@PathVariable Long id, @RequestParam String key) throws UserNotFoundException{
        UserDto userDto1 = userMapper.mapToUserDto(dbServiceUser.getUser(id));
        return ResponseEntity.ok(userMapper.mapToUserDto(dbServiceUser.unblockUser(id, key)));
    }

    @PostMapping("/generateKey/{id}")
    public String generateKey(@PathVariable Long id, @RequestParam String username, @RequestParam String password)
            throws UserNotFoundException {
        UserDto userFromDB = userMapper.mapToUserDto(dbServiceUser.getUser(id));
        Timer timer = new Timer();
        Long delay = 1000L;

        if (
                username == userFromDB.getUsername()
                        && password == userFromDB.getPassword()
        ) {
            if (userFromDB.isEnabled() == true) {
                return "this user is enabled, no need for a key";
            } else {
                String myKey = UUID.randomUUID().toString();


                return myKey;
            }
        } else {
            return "Wrong user credentials";
        }
    }
}