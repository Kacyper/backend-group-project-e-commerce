package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.exception.CartNotFoundException;
import com.kodilla.ecommercee.mapper.*;
import com.kodilla.ecommercee.service.DbServiceCart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final DbServiceCart dbServiceCart;
    private final CartMapper cartMapper;
    private final ProductMapper productMapper;

    @GetMapping("/{idCart}")
    public ResponseEntity <List<ProductDto>> getProductsFromCart(@PathVariable Long idCart) throws CartNotFoundException {
        return ResponseEntity.ok(productMapper.mapToListDto(dbServiceCart.getAllProducts(idCart)));
    }

    @PutMapping("addProduct/{idCart}/{idProduct}")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable Long idCart, @PathVariable Long idProduct) throws Exception {
        return ResponseEntity.ok(cartMapper.mapToCartDto(dbServiceCart.addToCart(idCart, idProduct)));
    }


    @PutMapping("/{idCart}/{idProduct}")
    public ResponseEntity<CartDto> deleteProductFromCart(@PathVariable Long idCart, @PathVariable Long idProduct) throws Exception {
        return ResponseEntity.ok(cartMapper.mapToCartDto(dbServiceCart.deleteFromCart(idCart, idProduct)));
    }
}