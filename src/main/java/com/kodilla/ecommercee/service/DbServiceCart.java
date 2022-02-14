package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.exception.CartNotFoundException;
import com.kodilla.ecommercee.exception.ProductNotFoundInCartException;
import com.kodilla.ecommercee.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DbServiceCart {

    private final CartRepository cartRepository;

    public Cart saveCart(final Cart cart) {
        return cartRepository.save(cart);
    }

    public List<Product> getAllProducts(final Long idCart) throws CartNotFoundException {
        cartRepository.findById(idCart).orElseThrow(CartNotFoundException::new);
        List<Product> products = cartRepository.findById(idCart).get().getProducts();
        return products;
    }

    public Cart updateCart(final Long idCart, final Long idProduct) throws CartNotFoundException, ProductNotFoundInCartException {
        Cart cart = cartRepository.findById(idCart).orElseThrow(CartNotFoundException::new);

        Product product = cart.getProducts().stream()
                .filter(p -> p.getId().equals(idProduct))
                .findFirst()
                .orElseThrow(ProductNotFoundInCartException::new);

        cart.getProducts().remove(product);
        return cart;
    }

    public void deleteFromCart(final Long idCart, final Long idProduct) throws CartNotFoundException, ProductNotFoundInCartException {
        Cart cart = cartRepository.findById(idCart).orElseThrow(CartNotFoundException::new);

        Product product = cart.getProducts().stream()
                .filter(p -> p.getId().equals(idProduct))
                .findFirst()
                .orElseThrow(ProductNotFoundInCartException::new);

        cart.getProducts().remove(product);
    }

//    public Order createOrder(Long idCart) throws CartNotFoundException {
//        Cart cart = cartRepository.findById(idCart).orElseThrow(CartNotFoundException::new);
//        BigDecimal shippingPrice = new BigDecimal(15);
//        BigDecimal totalPrice = countTotalPrice(shippingPrice, cart.getProducts());
//
//        return Order.builder()
//                .orderDate(LocalDate.now())
//                .shippingPrice(shippingPrice)
//                .isSent(false)
//                .isPaid(false)
//                .build();
//    }

    private BigDecimal countTotalPrice(BigDecimal shippingPrice, List<Product> products) {
        BigDecimal productsSum = products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return productsSum.add(shippingPrice);
    }
}