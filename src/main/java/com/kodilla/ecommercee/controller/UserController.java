package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.UserDto;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.service.DbServiceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    public UserDto blockUser (@PathVariable Long id, @RequestBody UserDto userDto) throws UserNotFoundException{
        return userMapper.mapToUserDto(dbServiceUser.blockUser(id, userMapper.mapToUser(userDto)));
    }

    @PostMapping(value = "generateKey")
    public int generateKey(@RequestBody UserDto userDto){
        return 1;
    }
}