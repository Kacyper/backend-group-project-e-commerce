package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbServiceCart {

    private final CartRepository cartRepository;

    public Cart saveCart(final Cart cart) {
        return cartRepository.save(cart);
    }
}
