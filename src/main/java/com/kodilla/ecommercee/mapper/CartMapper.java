package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.CartDto;
import org.springframework.stereotype.Service;

@Service
public class CartMapper {

    public Cart mapToCart(final CartDto cartDto) {
        return new Cart(
                cartDto.getIdCart()/*,

                Will be uncommented after full Cart implementation (JDP220102-14)
                cartDto.getName(),
                cartDto.getTotal()*/
        );
    }
}
