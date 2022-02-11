package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CartMapper {

    private ProductMapper productMapper;

    public Cart mapToCart(final CartDto cartDto) {
        return Cart.builder()
                .id(cartDto.getId())
                .build();
    }

    public CartDto mapToCartDto(final Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .build();
    }

}
