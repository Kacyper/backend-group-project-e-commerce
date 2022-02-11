package com.kodilla.ecommercee;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartAndProductRelationTestSuite {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void cartsHaveProducts(){
        //Given
        Cart cart = Cart.builder()
                .products(new ArrayList<>())
                .build();

        Product product = Product.builder()
                .name("mleko")
                .price(new BigDecimal(234.234))
                .productDescription("laciate")
                .group(null)
                .carts(new ArrayList<>())
                .build();

        Product product2 = Product.builder()
                .name("mleko23")
                .price(new BigDecimal(232314.234))
                .productDescription("laciateXXL")
                .group(null)
                .carts(new ArrayList<>())
                .build();

        //When
        cart.getProducts().add(product);
        product.getCarts().add(cart);
        cart.getProducts().add(product2);
        product2.getCarts().add(cart);

        cartRepository.save(cart);
        productRepository.save(product);
        productRepository.save(product2);
        cartRepository.save(cart);

        //Then
        assertEquals(1, cartRepository.findAll().size());
        assertEquals(2, productRepository.findAll().size());
        assertEquals(2, cart.getProducts().size());
        assertEquals(1, product.getCarts().size());
        assertEquals(1, product2.getCarts().size());
    }

    @Test
    public void UserHasCart(){
        User user = User.builder()
                .username("lololo")
                .email("cokolwike")
                .password("1234")
                .createDate(LocalDateTime.now())
                .isActive(true)
                .isEnabled(true)
                .cart(null)
                .build();

        Cart cart = Cart.builder()
                .products(new ArrayList<>())
                .build();

        //when
        user.setCart(cart);
        cart.setUser(user);

        cartRepository.save(cart);
        userRepository.save(user);

        //then
        assertEquals(1, userRepository.findAll().size());
        assertEquals(1, cartRepository.findAll().size());
        assertEquals(user, cart.getUser());
        assertEquals(cart, user.getCart());
    }
}
