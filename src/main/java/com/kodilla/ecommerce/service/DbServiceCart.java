package com.kodilla.ecommerce.service;

import com.kodilla.ecommerce.domain.*;
import com.kodilla.ecommerce.exception.*;
import com.kodilla.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DbServiceCart {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public List<Product> getAllProducts(final Long idCart) throws CartNotFoundException {
        return new ArrayList<>(cartRepository.findById(idCart)
                .orElseThrow(CartNotFoundException::new)
                .getProducts());
    }

    public Cart addToCart(final Long idCart, final Long idProduct) throws CartNotFoundException, ProductNotFoundException, ProductNotAvailableException {
        Cart cart = cartRepository.findById(idCart).orElseThrow(CartNotFoundException::new);
        Product product = productRepository.findById(idProduct).orElseThrow(ProductNotFoundException::new);

        if (product.isAvailable()) {
            cart.getProducts().add(product);
            cartRepository.save(cart);
            return cart;

        } else throw new ProductNotAvailableException();
    }

    public Cart deleteFromCart(final Long idCart, final Long idProduct) throws CartNotFoundException, ProductNotFoundInCartException {
        Cart cart = cartRepository.findById(idCart).orElseThrow(CartNotFoundException::new);

        Product product = cart.getProducts().stream()
                .filter(p -> p.getId().equals(idProduct))
                .findFirst()
                .orElseThrow(ProductNotFoundInCartException::new);

        cart.getProducts().remove(product);
        cartRepository.save(cart);
        return cart;
    }
}