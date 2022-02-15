package com.kodilla.ecommercee.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "ID_ORDER", unique = true)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @Column(name = "ORDER_DATE")
    private LocalDate orderDate;

    @NotNull
    @Column(name = "SHIPPING_PRICE")
    private BigDecimal shippingPrice;

    @NotNull
    @Column(name = "TOTAL_PRICE")
    private BigDecimal totalPrice;

    @NotNull
    @Column(name = "IS_SENT")
    private boolean isSent;

    @NotNull
    @Column(name = "IS_PAID")
    private boolean isPaid;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_USER")
    private User user;
}