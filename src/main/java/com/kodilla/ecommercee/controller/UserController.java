package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.UserDto;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping
    public List<UserDto> getUsers(){
        return new ArrayList<>();
    }

    @PostMapping
    public void createUser(@RequestBody UserDto userDto){

    }

    @PutMapping
    public UserDto blockUser (@RequestBody UserDto userDto){
        return new UserDto(
                1L,
                "John1231",
                "john@john.com",
                "password1223",
                LocalDate.of(2022, 1, 1),
                false,
                true);
    }

    @PostMapping(value = "generateKey")
    public int generateKey(@RequestBody UserDto userDto){
        return 1;
    }
}