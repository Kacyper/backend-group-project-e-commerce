package com.kodilla.ecommerce.domain;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "APP_USER_ID"
    )
    private User appUser;
}
