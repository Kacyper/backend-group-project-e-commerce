package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.OrderDto;
import com.kodilla.ecommercee.exception.CartNotFoundException;
import com.kodilla.ecommercee.exception.OrderNotFoundException;
import com.kodilla.ecommercee.mapper.OrderMapper;
import com.kodilla.ecommercee.service.DbServiceOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final DbServiceOrder dbServiceOrder;
    private final OrderMapper orderMapper;

    @GetMapping
    public List<OrderDto> getOrders() {
        return orderMapper.mapToOrderDtoList(dbServiceOrder.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) throws OrderNotFoundException {
       return ResponseEntity.ok(orderMapper.mapToOrderDto(dbServiceOrder.getOrder(id)));
    }

    @PostMapping("/{idCart}")
    public ResponseEntity<OrderDto> createOrder(@PathVariable Long idCart) throws CartNotFoundException {
        Order order = dbServiceOrder.createOrder(idCart);
        return ResponseEntity.ok(orderMapper.mapToOrderDto(order));
    }

    @PutMapping("/{id}/{shippingCompany}")
    public ResponseEntity<OrderDto> updateOrderChooseShippingCompany(@PathVariable Long id, @PathVariable int shippingCompany) throws OrderNotFoundException {
        return ResponseEntity.ok(orderMapper.mapToOrderDto(dbServiceOrder.updateOrderChooseShippingCompany(id, shippingCompany)));
    }

    @PutMapping("/send/{id}")
    public ResponseEntity<OrderDto> updateOrderIsSent(@PathVariable Long id) throws OrderNotFoundException {
        return ResponseEntity.ok(orderMapper.mapToOrderDto(dbServiceOrder.updateOrderIsSent(id)));
    }

    @PutMapping("/pay/{id}")
    public ResponseEntity<OrderDto> updateOrderIsPaid(@PathVariable Long id) throws OrderNotFoundException {
        return ResponseEntity.ok(orderMapper.mapToOrderDto(dbServiceOrder.updateOrderIsPaid(id)));
    }
}
