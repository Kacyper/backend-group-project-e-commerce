package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.CartNotFoundException;
import com.kodilla.ecommercee.controller.ProductNotFoundException;
import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

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
        Product product = productRepository.findById(idProduct).orElseThrow(ProductNotFoundException::new);

        List<Product> products = cart.getProducts();
        List<Cart> carts = product.getCarts();

        products.add(product);
        carts.add(cart);

        BigDecimal total = countTotalInCart(products);

        cart.setProducts(products);
        cart.setTotal(total);
        product.setCarts(carts);

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

    public void createOrder(Long idCart) {

    }
}