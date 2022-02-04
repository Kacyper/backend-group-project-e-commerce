package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DbServiceCart {

    private final CartRepository cartRepository;

    public Cart saveCart(final Cart cart) {
        return cartRepository.save(cart);
    }

    public boolean ifExist (final Long idCart) {
        return cartRepository.existsById(idCart);
    }

    public List<Product> getAllProducts(final Long idCart) {
        List<Product> products = cartRepository.findById(idCart).get().getProducts();
        return products;
    }
}