package com.kodilla.ecommercee.domain;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDto {
    private String username;
    private String email;
    private String password;
    private String repeatPassword;
}
