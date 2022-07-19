package com.kodilla.ecommerce.mapper;

import com.kodilla.ecommerce.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartMapper {

    private final ProductMapper productMapper;

    public CartDto mapToCartDto(final Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .productIds(productMapper.mapToProductsIdsFromProducts(cart.getProducts()))
                .build();
    }
}
