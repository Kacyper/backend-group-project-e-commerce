package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.exception.CartNotFoundException;
import com.kodilla.ecommercee.exception.ProductNotFoundInCartException;
import com.kodilla.ecommercee.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DbServiceCart {

    private final CartRepository cartRepository;

    public List<Product> getAllProducts(final Long idCart) throws CartNotFoundException {
        return cartRepository.findById(idCart).orElseThrow(CartNotFoundException::new).getProducts();
    }

    public Cart updateCart(final Long idCart, final Long idProduct) throws CartNotFoundException, ProductNotFoundInCartException {
        Cart cart = cartRepository.findById(idCart).orElseThrow(CartNotFoundException::new);

        Product product = cart.getProducts().stream()
                .filter(p -> p.getId().equals(idProduct))
                .findFirst()
                .orElseThrow(ProductNotFoundInCartException::new);

        cart.getProducts().add(product);
        return cart;
    }

    public Cart deleteFromCart(final Long idCart, final Long idProduct) throws CartNotFoundException, ProductNotFoundInCartException {
        Cart cart = cartRepository.findById(idCart).orElseThrow(CartNotFoundException::new);

        Product product = cart.getProducts().stream()
                .filter(p -> p.getId().equals(idProduct))
                .findFirst()
                .orElseThrow(ProductNotFoundInCartException::new);

        cart.getProducts().remove(product);
        return cart;
    }
}