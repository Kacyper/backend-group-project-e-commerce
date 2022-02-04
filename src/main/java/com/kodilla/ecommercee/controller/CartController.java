package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.CartDto;
import com.kodilla.ecommercee.domain.ProductDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createCart(@RequestBody CartDto cartDto) {
        System.out.println("Cart with id: " + cartDto.getIdCart() + " created");
    }

    @GetMapping("/{idCart}")
    public List<ProductDto> getProductsFromCart(@PathVariable Long idCart) {
        return new ArrayList<>();
    }

    @PutMapping("/{idCart}/{idProduct}")
    public CartDto updateCart(@PathVariable Long idCart, @PathVariable Long idProduct) {
        return new CartDto(1L, "Cart1", 300.5);
    }

    @DeleteMapping("/{idCart}/{idProduct}")
    public void deleteFromCart(@PathVariable Long idCart, @PathVariable Long idProduct) {
        System.out.println("Product with id: " + idProduct + " delated from cart with id: " + idCart);
    }

    @PostMapping(value = "/createOrder/{idCart}")
    public void createOrder(@PathVariable Long idCart) {
        System.out.println("Order for cart with id: " + idCart + " created");
    }
}