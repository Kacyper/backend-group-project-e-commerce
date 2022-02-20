package com.kodilla.ecommerce.controller;

import com.kodilla.ecommerce.domain.UserDto;
import com.kodilla.ecommerce.exception.*;
import com.kodilla.ecommerce.mapper.UserMapper;
import com.kodilla.ecommerce.service.DbServiceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final DbServiceUser dbServiceUser;
    private final UserMapper userMapper;

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> blockUser (@PathVariable Long id) throws UserNotFoundException, UserAlreadyBlockedException {
        return ResponseEntity.ok(userMapper.mapToUserDto(dbServiceUser.blockUser(id)));
    }

    @GetMapping("/{username}")
    public ResponseEntity<String> generateKey(@PathVariable String username, @RequestParam String password) throws UserNotFoundException {
        return ResponseEntity.ok(dbServiceUser.generateKey(username, password));
    }
}