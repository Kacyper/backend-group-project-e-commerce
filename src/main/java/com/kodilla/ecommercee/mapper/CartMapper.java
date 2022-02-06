package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartMapper {

    private ProductMapper productMapper;

    public Cart mapToCart(final CartDto cartDto) {
        return Cart.builder()
                .id(cartDto.getId())
                .cartName(cartDto.getName())
                .products(new ArrayList<>())
                .order(new Order())
                .build();
    }

    public CartDto mapToCartDto(final Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .name(cart.getCartName())
                .build();
    }

}
