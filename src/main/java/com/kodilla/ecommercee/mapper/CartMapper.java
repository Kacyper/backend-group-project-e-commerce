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
                cartDto.getTotal(),
                new ArrayList<>(),
                new Order()
                /*,
                Will be uncommented after full Cart implementation (JDP220102-14)
                cartDto.getName(),
                cartDto.getTotal()*/
        );
    }

    public CartDto mapToCartDto(final Cart cart) {
        return new CartDto(
                cart.getIdCart(),

                /*Will be uncommented and switched with String "name" after full Cart implementation (JDP220102-14)
                cartDto.getName()*/
                "cartDto",
                cart.getTotal()

                /*,
                Will be uncommented after full Cart implementation (JDP220102-14)
                cartDto.getName(),
                cartDto.getTotal()*/
        );
    }
}
