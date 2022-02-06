package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.CartNotFoundException;
import com.kodilla.ecommercee.controller.ProductNotFoundException;
import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DbServiceCart {

    private final CartRepository cartRepository;
    private final DbServiceProduct dbServiceProduct;
    private final ProductRepository productRepository;

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

    public Cart updateCart(final Long idCart, final Long idProduct) throws CartNotFoundException, ProductNotFoundException {
        Cart cart = cartRepository.findById(idCart).orElseThrow(CartNotFoundException::new);
        BigDecimal currentTotalFromCart = cart.getTotal();

        productRepository.findById(idProduct).orElseThrow(ProductNotFoundException::new);
        BigDecimal productPrice = productRepository.findById(idProduct).get().getPrice();

        BigDecimal newCurrentTotalFromCart = currentTotalFromCart.add(productPrice);
        cart.setTotal(newCurrentTotalFromCart);
        return cart;
    }

    public void deleteFromCart(final Long idCart, final Long idProduct) throws ProductNotFoundException {
        productRepository.findById(idProduct).orElseThrow(ProductNotFoundException::new);
        productRepository.findById(idProduct).get().getCarts().removeIf(n -> n.getIdCart() == idCart);
        cartRepository.findById(idCart).get().getProducts().removeIf(n -> n.getId() == idProduct);
    }

    public void createOrder(Long idCart) {

    }
}