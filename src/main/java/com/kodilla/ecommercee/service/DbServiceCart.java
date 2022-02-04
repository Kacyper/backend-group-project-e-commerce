package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.CartNotFoundException;
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

    public Cart updateCart(final Long idCart, final Long idProduct) throws CartNotFoundException {
        Cart cart = cartRepository.findById(idCart).orElseThrow(CartNotFoundException::new);
        BigDecimal currentTotalFromCart = cart.getTotal();
        BigDecimal productPrice = new BigDecimal(0);

        if (dbServiceProduct.ifExist(idProduct)) {
            Optional<Product> product = productRepository.findById(idProduct);
            productPrice = product.get().getPrice();

        } else {
            System.out.println("Product with id: " + idProduct + " doesn't exist or can't be found");
        }

        BigDecimal newCurrentTotalFromCart = currentTotalFromCart.add(productPrice);
        cart.setTotal(newCurrentTotalFromCart);
        return cart;
    }

    public void deleteFromCart(final Long idCart, final Long idProduct) {
        Optional<Cart> cart = cartRepository.findById(idCart);
        String deleteMessage = "";

        if (!dbServiceProduct.ifExist(idProduct)) {
            deleteMessage = "Product with id: " + idProduct + " doesn't exist or can't be found";

        } else {
            boolean isProductRemoved = cart.get().getProducts().removeIf(n -> n.getId() == idProduct);

            if (!isProductRemoved) {
                deleteMessage =
                        "There is no product with given id (" + idProduct+ ") " +
                        "in the cart with id: " + idCart + "or product can't be found";

            } else {
                Optional<Product> product = productRepository.findById(idProduct);
                product.get().getCarts().removeIf(n -> n.getIdCart() == idCart);
                deleteMessage = "Product with id: " + idProduct + " removed from cart with id: " + idCart;
            };
        }

        System.out.println(deleteMessage);
    }
}