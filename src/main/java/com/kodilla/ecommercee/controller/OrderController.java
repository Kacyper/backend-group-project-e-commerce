package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.OrderDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/orders")
public class OrderController {

    @GetMapping
    public List<OrderDto> getOrders() {
        return new ArrayList<>();
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable Long id) {
        return OrderDto.builder()
                .id(id)
                .orderDate(LocalDate.now())
                .shippingPrice(8.99)
                .totalPrice(119.99)
                .isSent(false)
                .isPaid(true)
                .build();
    }

    //temporary method for tests, will be void later
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
        return OrderDto.builder()
                .id(1L)
                .orderDate(orderDto.getOrderDate())
                .shippingPrice(orderDto.getShippingPrice())
                .totalPrice(orderDto.getTotalPrice())
                .isSent(orderDto.isSent())
                .isPaid(orderDto.isPaid())
                .build();
    }

    @PutMapping
    public OrderDto updateOrder(@RequestBody OrderDto orderDto) {
        return OrderDto.builder()
                .id(orderDto.getId())
                .orderDate(orderDto.getOrderDate())
                .shippingPrice(orderDto.getShippingPrice())
                .totalPrice(orderDto.getTotalPrice())
                .isSent(orderDto.isSent())
                .isPaid(orderDto.isPaid())
                .build();
    }

    //temporary method for tests, will be void later
    @DeleteMapping("/{id}")
    public Long deleteOrder(@PathVariable Long id) {
        return id;
    }
}
