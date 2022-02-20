package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.exception.CartNotFoundException;
import com.kodilla.ecommercee.mapper.*;
import com.kodilla.ecommercee.service.DbServiceCart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final DbServiceCart dbServiceCart;
    private final CartMapper cartMapper;

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/{idCart}")
    public ResponseEntity <List<ProductDto>> getProductsFromCart(@PathVariable Long idCart) throws CartNotFoundException {
        return ResponseEntity.ok(ProductMapper.mapToListDto(dbServiceCart.getAllProducts(idCart)));
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PutMapping("addProduct/{idCart}/{idProduct}")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable Long idCart, @PathVariable Long idProduct) throws Exception {
        return ResponseEntity.ok(cartMapper.mapToCartDto(dbServiceCart.addToCart(idCart, idProduct)));
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PutMapping("/{idCart}/{idProduct}")
    public ResponseEntity<CartDto> deleteProductFromCart(@PathVariable Long idCart, @PathVariable Long idProduct) throws Exception {
        return ResponseEntity.ok(cartMapper.mapToCartDto(dbServiceCart.deleteFromCart(idCart, idProduct)));
    }
}