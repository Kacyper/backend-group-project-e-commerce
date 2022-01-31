package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.CartDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @GetMapping("/getCart/{id}")
    public CartDto getCart(@PathVariable Long id) {
        return new CartDto();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/createCart")
    public void createCart(@RequestBody CartDto cartDto) {
    }

    @PutMapping(value = "/updateCart")
    public CartDto updateCart(@RequestBody CartDto cartDto) {
        return new CartDto();
    }

    @DeleteMapping(value = "/deleteCart/{id}")
    public void deleteCart(@PathVariable Long id) {
    }
}
