package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.CartDto;
import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.mapper.CartMapper;
import com.kodilla.ecommercee.service.DbServiceCart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final DbServiceCart dbServiceCart;
    private final CartMapper cartMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createCart(@RequestBody CartDto cartDto) {
        Cart cart = cartMapper.mapToCart(cartDto);
        dbServiceCart.saveCart(cart);
    }

    @GetMapping("/{idCart}")
    public List<ProductDto> getProductsFromCart(@PathVariable Long idCart) {
        if (dbServiceCart.ifExist(idCart)) {
            List<ProductDto> =
        }
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