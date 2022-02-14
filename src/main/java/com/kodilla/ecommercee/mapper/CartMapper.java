package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.*;
import org.springframework.stereotype.Service;

@Service
public class CartMapper {

    public CartDto mapToCartDto(final Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .build();
    }
}
