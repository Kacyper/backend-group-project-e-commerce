package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.AppUserRole;
import com.kodilla.ecommercee.domain.RegistrationRequestDto;
import com.kodilla.ecommercee.domain.User;

import java.time.LocalDateTime;

public class AppUserMapper {
    public static User mapToAppUser(final RegistrationRequestDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .fullName(dto.getFullName())
                .password(dto.getPassword())
                .appUserRole(AppUserRole.USER)
                .enabled(false)
                .createDate(LocalDateTime.now())
                .build();
    }
}
