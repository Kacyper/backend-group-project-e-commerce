package com.kodilla.ecommercee.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    @NotNull
    private String username;
    private String email;
    @NotNull
    private String password;
    private LocalDateTime createDate;
    @NotNull
    private boolean isActive;
    @NotNull
    private boolean isEnabled;
}
