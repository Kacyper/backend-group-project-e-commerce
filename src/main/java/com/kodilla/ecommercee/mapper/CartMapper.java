package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartMapper {

    private ProductMapper productMapper;

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

    public List<ProductDto> mapToProductsDto(final List<Product> products) {
        return products.stream()
                .map(product -> productMapper.mapToProductDto(product))
                .collect(Collectors.toList());
    }
}
