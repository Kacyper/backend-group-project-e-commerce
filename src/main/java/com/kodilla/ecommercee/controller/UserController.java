package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.UserDto;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.service.DbServiceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userMapper.mapToUserDto(dbServiceUser.createUser(userMapper.mapToUser(userDto))));
    }

    @PutMapping("/block/{id}")
    public ResponseEntity<UserDto> blockUser (@PathVariable Long id) throws UserNotFoundException{
        return ResponseEntity.ok(userMapper.mapToUserDto(dbServiceUser.blockUser(id)));
    }

    @PutMapping("/unblock/{id}")
    public ResponseEntity<String> unblockUser(@PathVariable Long id, @RequestParam String key) throws UserNotFoundException{
        return ResponseEntity.ok(dbServiceUser.unblockUser(id, key));
    }

    @GetMapping("/generateKey/{id}")
    public ResponseEntity<String> generateKey(@PathVariable Long id, @RequestParam String username, @RequestParam String password) throws UserNotFoundException {
        return ResponseEntity.ok(dbServiceUser.generateKey(id, username, password));
    }
}