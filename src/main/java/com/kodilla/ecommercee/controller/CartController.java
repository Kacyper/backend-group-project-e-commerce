package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.mapper.*;
import com.kodilla.ecommercee.service.DbServiceCart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final DbServiceCart dbServiceCart;
    private final CartMapper cartMapper;
    private final ProductMapper productMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createCart(@RequestBody CartDto cartDto) {
        Cart cart = cartMapper.mapToCart(cartDto);
        dbServiceCart.saveCart(cart);
    }

    @GetMapping("/{idCart}")
    public List<ProductDto> getProductsFromCart(@PathVariable Long idCart) {

        if (dbServiceCart.ifExist(idCart)) {
            List<Product> products = dbServiceCart.getAllProducts(idCart);
            return products.stream()
                    .map(product -> productMapper.mapToProductDto(product))
                    .collect(Collectors.toList());

        } else {
            System.out.println("Cart with id: " + idCart + " doesn't exist or can't be found");
            return new ArrayList<>();
        }
    }

    @PutMapping("/{idCart}/{idProduct}")
    public ResponseEntity<CartDto> updateCart(@PathVariable Long idCart, @PathVariable Long idProduct) throws CartNotFoundException {
        return ResponseEntity.ok(cartMapper.mapToCartDto(dbServiceCart.updateCart(idCart, idProduct)));
    }

    @DeleteMapping("/{idCart}/{idProduct}")
    public ResponseEntity<Void> deleteFromCart(@PathVariable Long idCart, @PathVariable Long idProduct) {

        if (dbServiceCart.ifExist(idCart)) {
            dbServiceCart.deleteFromCart(idCart, idProduct);

        } else {
            System.out.println("Cart with id: " + idCart + " doesn't exist or can't be found");
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/createOrder/{idCart}")
    public void createOrder(@PathVariable Long idCart) {
        System.out.println("Order for cart with id: " + idCart + " created");
    }
}