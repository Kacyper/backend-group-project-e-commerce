package com.kodilla.ecommercee.domain;

import jdk.jfr.Unsigned;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Unsigned
    @Column(name = "ID_ORDER", unique = true)
    private Long id;

    @NotNull
    @Column(name = "ORDER_DATE")
    private LocalDate orderDate;

    @NotNull
    @Unsigned
    @Column(name = "SHIPPING_PRICE")
    private double shippingPrice;

    @NotNull
    @Unsigned
    @Column(name = "TOTAL_PRICE")
    private double totalPrice;

    @NotNull
    @Column(name = "IS_SENT")
    private boolean isSent;

    @NotNull
    @Column(name = "IS_PAID")
    private boolean isPaid;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CART")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private User user;
}
