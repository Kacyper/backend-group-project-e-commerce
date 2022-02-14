package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.OrderDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMapper {

    public Order mapToOrder(final OrderDto orderDto) {
        return Order.builder()
                .id(orderDto.getId())
                .orderDate(orderDto.getOrderDate())
                .shippingPrice(orderDto.getShippingPrice())
                .isSent(orderDto.isSent())
                .isPaid(orderDto.isPaid())
                .build();
    }

    public OrderDto mapToOrderDto(final Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .shippingPrice(order.getShippingPrice())
                .isSent(order.isSent())
                .isPaid(order.isPaid())
                .build();
    }

    public List<OrderDto> mapToOrderDtoList(final List<Order> orders) {
        return orders.stream().map(this::mapToOrderDto)
                .collect(Collectors.toList());
    }
}
