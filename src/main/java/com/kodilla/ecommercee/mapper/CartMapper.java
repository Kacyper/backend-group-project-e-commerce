package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.CartDto;
import com.kodilla.ecommercee.domain.Order;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CartMapper {

    public Cart mapToCart(final CartDto cartDto) {
        return new Cart(
                cartDto.getIdCart(),
                cartDto.getName(),
                cartDto.getTotal(),
                new ArrayList<>(),
                new Order()
        );
    }

    public CartDto mapToCartDto(final Cart cart) {
        return new CartDto(
                cart.getIdCart(),
                cart.getCartName(),
                cart.getTotal()
        );
    }
}
