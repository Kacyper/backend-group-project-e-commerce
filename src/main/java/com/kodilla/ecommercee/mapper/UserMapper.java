package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {
    public User mapToUser(final UserDto userDto){
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .createDate(userDto.getCreateDate())
                .active(userDto.isActive())
                .enabled(userDto.isEnabled())
                .build();
    }

    public UserDto mapToUserDto(final User user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .createDate(user.getCreateDate())
                .active(user.isActive())
                .enabled(user.isEnabled())
                .build();
    }

    public List<UserDto> mapToListDto(final List<User> users){
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }
}
