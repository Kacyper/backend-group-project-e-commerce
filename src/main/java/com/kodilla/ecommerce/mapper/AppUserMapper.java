package com.kodilla.ecommerce.mapper;

import com.kodilla.ecommerce.domain.AppUserRole;
import com.kodilla.ecommerce.domain.RegistrationRequestDto;
import com.kodilla.ecommerce.domain.User;

import java.time.LocalDateTime;

public class AppUserMapper {
    public static User mapToAppUser(final RegistrationRequestDto dto) {
        return User.builder()
                .username(dto.getEmail())
                .fullName(dto.getFullName())
                .password(dto.getPassword())
                .appUserRole(AppUserRole.USER)
                .enabled(false)
                .createDate(LocalDateTime.now())
                .build();
    }
}
