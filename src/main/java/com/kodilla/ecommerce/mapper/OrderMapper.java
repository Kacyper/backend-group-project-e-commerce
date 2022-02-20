package com.kodilla.ecommerce.mapper;

import com.kodilla.ecommerce.domain.Order;
import com.kodilla.ecommerce.domain.OrderDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMapper {

    public OrderDto mapToOrderDto(final Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .shippingPrice(order.getShippingPrice())
                .productsTotalPrice(order.getProductsTotalPrice())
                .orderTotalPrice(order.getOrderTotalPrice())
                .isSent(order.isSent())
                .isPaid(order.isPaid())
                .products(order.getProducts())
                .build();
    }

    public List<OrderDto> mapToOrderDtoList(final List<Order> orders) {
        return orders.stream().map(this::mapToOrderDto)
                .collect(Collectors.toList());
    }
}
