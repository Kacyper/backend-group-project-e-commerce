package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.OrderDto;

public class OrderMapper {
    public OrderDto mapToOrderDto(final Order order) {
        return OrderDto.builder()
                .orderDate(order.getOrderDate())
                .shippingPrice(order.getShippingPrice().doubleValue())
                .totalPrice(order.getTotalPrice().doubleValue())
                .isSent(order.isSent())
                .isPaid(order.isPaid())
                .build();
    }
}
