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
                .idCart(cartDto.getIdCart())
                .cartName(cartDto.getName())
                .total(cartDto.getTotal())
                .products(new ArrayList<>())
                .order(new Order())
                .build();
    }

    public CartDto mapToCartDto(final Cart cart) {
        return CartDto.builder()
                .idCart(cart.getIdCart())
                .name(cart.getCartName())
                .total(cart.getTotal())
                .build();
    }

    public List<ProductDto> mapToProductsDto(final List<Product> products) {
        return products.stream()
                .map(product -> productMapper.mapToProductDto(product))
                .collect(Collectors.toList());
    }
}
