package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.*;
import com.kodilla.ecommercee.domain.*;
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
    private final ProductRepository productRepository;

    public Cart saveCart(final Cart cart) {
        return cartRepository.save(cart);
    }

    public boolean ifExist (final Long idCart) {
        return cartRepository.existsById(idCart);
    }

    public List<Product> getAllProducts(final Long idCart) throws CartNotFoundException {
        cartRepository.findById(idCart).orElseThrow(CartNotFoundException::new);
        List<Product> products = cartRepository.findById(idCart).get().getProducts();
        return products;
    }

    public Cart updateCart(final Long idCart, final Long idProduct) throws CartNotFoundException, ProductNotFoundInCartException {
        Cart cart = cartRepository.findById(idCart).orElseThrow(CartNotFoundException::new);

        Product product1 = cart.getProducts().stream()
                .filter(p -> p.getId().equals(idProduct))
                .findFirst()
                .orElseThrow(ProductNotFoundInCartException::new);

        cart.getProducts().remove(product1);

        return cart;
    }

    public void deleteFromCart(final Long idCart, final Long idProduct) throws CartNotFoundException, ProductNotFoundException {
        Cart cart = cartRepository.findById(idCart).orElseThrow(CartNotFoundException::new);
        Product product = productRepository.findById(idProduct).orElseThrow(ProductNotFoundException::new);;

        List<Product> products = cart.getProducts();
        List<Cart> carts = product.getCarts();

        products.removeIf(p -> p.getId() == idProduct);
        carts.removeIf(c -> c.getIdCart() == idCart);

        BigDecimal total = countTotalInCart(products);

        cart.setProducts(products);
        cart.setTotal(total);
        product.setCarts(carts);
    }

    private BigDecimal countTotalInCart(List<Product> products) {
        BigDecimal total = null;

        for (Product product : products) {
            BigDecimal productPrice = product.getPrice();
            total = total.add(productPrice);
        }

        return total;
    }

    public Order createOrder(Long idCart) throws CartNotFoundException {
        Cart cart = cartRepository.findById(idCart).orElseThrow(CartNotFoundException::new);
        BigDecimal shippingPrice = new BigDecimal(15);
        BigDecimal totalPrice = countTotalPrice(shippingPrice, idCart);

        return Order.builder()
                .orderDate(LocalDate.now())
                .shippingPrice(shippingPrice)
                .totalPrice(totalPrice)
                .isSent(false)
                .isPaid(false)
                .cart(cart)
                .build();
    }

    private BigDecimal countTotalPrice(BigDecimal shippingPrice, Long idCart) {
        return cartRepository.findById(idCart).get().getTotal().add(shippingPrice);
    }
}