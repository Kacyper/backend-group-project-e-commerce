package com.kodilla.ecommerce.mapper;

import com.kodilla.ecommerce.domain.User;
import com.kodilla.ecommerce.domain.UserDto;
import com.kodilla.ecommerce.exception.CartNotFoundException;
import com.kodilla.ecommerce.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final CartRepository cartRepository;

    public User mapToUser(final UserDto userDto) throws CartNotFoundException {
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .fullName(userDto.getFullName())
                .password(userDto.getPassword())
                .createDate(userDto.getCreateDate())
                .active(userDto.isActive())
                .enabled(userDto.isEnabled())
                .cart(cartRepository.findById(userDto.getIdCart()).orElseThrow(CartNotFoundException::new))
                .build();
    }

    public UserDto mapToUserDto(final User user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .password(user.getPassword())
                .createDate(user.getCreateDate())
                .active(user.isActive())
                .enabled(user.isEnabled())
                .idCart(user.getCart().getId())
                .build();
    }
}
