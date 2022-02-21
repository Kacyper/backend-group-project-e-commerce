package com.kodilla.ecommerce.mapper;

import com.kodilla.ecommerce.domain.User;
import com.kodilla.ecommerce.domain.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    public UserDto mapToUserDto(final User user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .password(user.getPassword())
                .createDate(user.getCreateDate())
                .enabled(user.isEnabled())
                .idCart(user.getCart().getId())
                .build();
    }
}
