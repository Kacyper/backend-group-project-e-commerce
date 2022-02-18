package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTestSuite {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;

    @Test
    public void testSaveUser() {
        //Given
        User user = User.builder()
                .username("Kate")
                .email("kate@poczta.pl")
                .password("aaaa1111")
                .createDate(LocalDateTime.now())
                .active(true)
                .enabled(true)
                .build();
        //When
        userRepository.save(user);

        //Then
        Long id = user.getId();
        List<User> userList = userRepository.findAll();
        assertEquals(1, userList.size());

        //Cleanup
        userRepository.deleteById(id);
    }

    @Test
    public void testFindUserByID() {
        //Given
        User user1 = User.builder()
                .username("Kate")
                .email("kate@poczta.pl")
                .password("aaaa1111")
                .createDate(LocalDateTime.now())
                .active(true)
                .enabled(true)
                .build();

        userRepository.save(user1);

        //When
        Long id = user1.getId();
        Long readId = userRepository.findById(id).get().getId();

        //Then
        assertEquals(id, readId);

        //Cleanup
        userRepository.deleteById(user1.getId());
    }

    @Test
    public void testUpdateUser() {
        //Given
        User user = User.builder()
                .username("Kate")
                .email("kate@poczta.pl")
                .password("aaaa1111")
                .createDate(LocalDateTime.now())
                .active(true)
                .enabled(true)
                .build();

        userRepository.save(user);

        //When
        user.setEnabled(false);
        userRepository.save(user);

        //Then
        assertFalse(user.isEnabled());

        //Cleanup
        userRepository.deleteById(user.getId());
    }

    @Test
    public void testAddOrderToUser() {
        //Given
        User user = User.builder()
                .username("Kate")
                .email("kate@poczta.pl")
                .password("aaaa1111")
                .createDate(LocalDateTime.now())
                .active(true)
                .enabled(true)
                .build();

        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDate.of(2022, 1, 23))
                .shippingPrice(new BigDecimal("12.99"))
                .productsTotalPrice(new BigDecimal("13.99"))
                .orderTotalPrice(new BigDecimal("26.98"))
                .paid(false)
                .sent(false)
                .build();

        orderRepository.save(order);

        //When
        List<User> users = userRepository.findAll();

        //Then
        int numberOfUserOrders = users.get(0).getOrders().size();
        assertEquals(1, numberOfUserOrders);

        //Cleanup
        userRepository.deleteById(user.getId());
    }

    @Test
    public void testAddCartToUser() {
        //Given
        Cart cart = Cart.builder().build();

        User user = User.builder()
                .username("Kate")
                .email("kate@poczta.pl")
                .password("aaaa1111")
                .createDate(LocalDateTime.now())
                .active(true)
                .enabled(true)
                .cart(cart)
                .build();

        cartRepository.save(cart);

        //When
        userRepository.save(user);

        //Then
        int numberOfUsers = userRepository.findAll().size();
        assertEquals(1, numberOfUsers);
        assertEquals(1, cartRepository.findAll().size());
        assertEquals(cart.getId(), userRepository.findAll().get(0).getCart().getId());

        //Cleanup
        userRepository.deleteById(user.getId());
        cartRepository.deleteAll();
    }
}