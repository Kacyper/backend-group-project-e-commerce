package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private LocalDate orderDate;
    private BigDecimal shippingPrice;
    private BigDecimal productsTotalPrice;
    private BigDecimal orderTotalPrice;
    private boolean isSent;
    private boolean isPaid;
    private List<Product> products;
}

