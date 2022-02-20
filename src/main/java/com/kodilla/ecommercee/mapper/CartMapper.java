package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartMapper {

    private final ProductMapper productMapper;

    public CartDto mapToCartDto(final Cart cart) {
        System.out.println("Producs: "cart.getProducts());
        return CartDto.builder()
                .id(cart.getId())
                .productIds(productMapper.mapToProductsIdsFromProducts(cart.getProducts()))
                .build();
    }
}
