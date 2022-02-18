package com.kodilla.ecommercee.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createDate = LocalDateTime.now();
    private boolean active = false;
    private boolean enabled = false;
    private Long idCart;
}