package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.UserDto;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.repository.UserRepository;
import com.kodilla.ecommercee.service.DbServiceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private final UserRepository userRepository;

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

    @PutMapping("/block/{id}")
    public ResponseEntity<UserDto> blockUser (@PathVariable Long id) throws UserNotFoundException{
        return ResponseEntity.ok(userMapper.mapToUserDto(dbServiceUser.blockUser(id)));
    }

    //do przekminy
//    @PutMapping("/unblock/{id}")
//    public ResponseEntity<UserDto> unblockUser (@PathVariable Long id, @RequestParam String key) throws UserNotFoundException{
//        UserDto userDto1 = userMapper.mapToUserDto(dbServiceUser.getUser(id));
//        return ResponseEntity.ok(userMapper.mapToUserDto(dbServiceUser.unblockUser(id, key)));
//    }

    @PutMapping("/unblock/{id}")
    public String unblockUser(@PathVariable Long id, @RequestParam String key) throws UserNotFoundException{
        User userFromDb = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if(key.equals(userFromDb.getUserKey()) && System.currentTimeMillis() - userFromDb.getKeyGenerationTime() < 30000L){
            userFromDb.setEnabled(true);
            userRepository.save(userFromDb);
            return "Account was activated";
        }
        return "Invalid key or time run out" + userFromDb.getUserKey();
    }

    @GetMapping("/generateKey/{id}")
    public String generateKey(@PathVariable Long id, @RequestParam String username, @RequestParam String password) throws UserNotFoundException {
        //UserDto userFromDb = userMapper.mapToUserDto(dbServiceUser.getUser(id));
        return dbServiceUser.generateKey(id, username, password);

    }
}