package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor

public class CartDto {

    private int idCart;
    private String name;
    private BigDecimal total;

}
