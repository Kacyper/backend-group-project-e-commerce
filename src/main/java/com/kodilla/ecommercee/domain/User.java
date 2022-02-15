package com.kodilla.ecommercee.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "ID_USER", unique = true)
    private Long id;

    @NotNull
    @Column(name = "USERNAME")
    private String username;

    @NotNull
    @Column(name = "EMAIL")
    private String email;

    @NotNull
    @Column(name = "PASSWORD")
    private String password;

    @JsonFormat(pattern = "YYYY-MM-DD")
    @NotNull
    @Column(name = "CREATE_DATE")
    private LocalDate createDate;

    @NotNull
    @Column(name = "ACTIVE")
    private boolean active;

    @NotNull
    @Column(name = "ENABLED")
    private boolean enabled;

    private String userKey = null;
    private Long KeyGenerationTime = null;

    @OneToMany(
            targetEntity = Order.class,
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private final List<Order> orders = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CART")
    private Cart cart;
}
