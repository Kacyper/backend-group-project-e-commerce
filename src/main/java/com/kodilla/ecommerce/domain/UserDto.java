package com.kodilla.ecommerce.domain;

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
    private String fullName;
    private String password;
    private LocalDateTime createDate = LocalDateTime.now();
    private boolean active = true;
    private boolean enabled = false;
    private Long idCart;
}