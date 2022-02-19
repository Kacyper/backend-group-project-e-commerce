package com.kodilla.ecommercee.domain;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDto {
    private String fullName;
    private String username;
    private String password;
    private String repeatPassword;
}
