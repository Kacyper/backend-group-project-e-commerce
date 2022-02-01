package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.CartDto;
import com.kodilla.ecommercee.domain.ProductDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createCart(@RequestBody CartDto cartDto) {
    }

    @GetMapping("{idCart}")
    public List<ProductDto> getProductsFromCart(@PathVariable Long idCart) {
        return new ArrayList<>();
    }

    @PutMapping
    public CartDto updateCart(@RequestParam Long idCart, @RequestParam Long idProduct) {
        return new CartDto();
    }

    @DeleteMapping("{idProduct}")
    public void deleteFromCart(@RequestParam Long idCart, @RequestParam Long idProduct) {
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/createOrder")
    public void createOrder(@RequestParam Long idCart) {
    }
}
