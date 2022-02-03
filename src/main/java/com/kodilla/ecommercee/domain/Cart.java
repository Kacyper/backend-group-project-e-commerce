package com.kodilla.ecommercee.domain;


import jdk.jfr.Unsigned;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity(name = "CARTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Cart {


    @Id
    @GeneratedValue
    @NotNull
    @Unsigned
    @NotNull
    @Column(name = "ID_CART")
    private Long idCart;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "TOTAL")
    private BigDecimal total;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ORDER")
    private Order order;




}