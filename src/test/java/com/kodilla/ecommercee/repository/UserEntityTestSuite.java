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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserEntityTestSuite {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;

    @Test
    public void createNewUserTest() {
        //Given
        User user = User.builder()
                .username("Kate")
                .email("kate@poczta.pl")
                .password("aaaa1111")
                .createDate(LocalDateTime.now())
                .isActive(true)
                .isEnabled(true)
                .build();
        //When
        userRepository.save(user);
        Long id = user.getId();

        //Then
        List<User> userList = userRepository.findAll();
        assertEquals(1, userList.size());

        //Cleanup
        userRepository.deleteById(id);
    }

    @Test
    public void findUserByIDTest() {
        //Given
        User user1 = User.builder()
                .username("Kate")
                .email("kate@poczta.pl")
                .password("aaaa1111")
                .createDate(LocalDateTime.now())
                .isActive(true)
                .isEnabled(true)
                .build();

        User user2 = User.builder()
                .username("Tom")
                .email("tom@poczta.pl")
                .password("aaaa1111")
                .createDate(LocalDateTime.now())
                .isActive(true)
                .isEnabled(true)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        //When
        String user1UserName = user1.getUsername();
        userRepository.findById(1L);

        //Then
        assertEquals("Kate", user1UserName);

        //Cleanup
        userRepository.deleteById(user1.getId());
        userRepository.deleteById(user2.getId());
    }

    @Test
    public void UserUpdateTest() {
        //Given
        User user = User.builder()
                .username("Kate")
                .email("kate@poczta.pl")
                .password("aaaa1111")
                .createDate(LocalDateTime.now())
                .isActive(true)
                .isEnabled(true)
                .orders(new ArrayList<>())
                .build();

        userRepository.save(user);
        user.setEnabled(false);

        //When
        userRepository.save(user);

        //Then
        assertFalse(user.isEnabled());

        //Cleanup
        userRepository.deleteById(user.getId());
    }

    @Test
    public void OneToManyRelationWithOrderTest() {
        //Given
        Order order = Order.builder()
                .orderDate(LocalDate.of(2022, 01, 23))
                .shippingPrice(new BigDecimal("12.99"))
                .isPaid(false)
                .isSent(false)
                .build();

        List<Order> orders = new ArrayList<>();
        orders.add(order);

        User user = User.builder()
                .username("Kate")
                .email("kate@poczta.pl")
                .password("aaaa1111")
                .createDate(LocalDateTime.now())
                .isActive(true)
                .isEnabled(true)
                .orders(orders)
                .build();

        userRepository.save(user);
        order.setUser(user);
        orderRepository.save(order);

        //When
        List<User> users = userRepository.findAll();
        int numberOfUsers = userRepository.findAll().size();
        LocalDate orderDate = users.get(0).getOrders().get(0).getOrderDate();

        //Then
        assertEquals(1, numberOfUsers);
        assertEquals(LocalDate.of(2022, 1, 23), orderDate);

        //Cleanup
        userRepository.deleteById(user.getId());
        orderRepository.deleteAll();
    }

    @Test
    public void OneToOneRelationWithCartTest() {
        //Given
        Cart cart = new Cart();

        User user = User.builder()
                .username("Kate")
                .email("kate@poczta.pl")
                .password("aaaa1111")
                .createDate(LocalDateTime.now())
                .isActive(true)
                .isEnabled(true)
                .cart(cart)
                .build();

        userRepository.save(user);
        user.setCart(new Cart());
        cartRepository.save(cart);

        //When
        int numberOfUsers = userRepository.findAll().size();

        //Then
        assertEquals(1, numberOfUsers);
        assertEquals(1, cartRepository.findAll().size());
        assertEquals(cart.getId(), userRepository.findAll().get(0).getCart().getId());

        //Cleanup
        userRepository.deleteById(user.getId());
        cartRepository.deleteAll();
    }

    @Test
    public void UserRemovalRemovesCartTest() {
        //Given
        Cart cart = new Cart();

        User user = User.builder()
                .username("Kate")
                .email("kate@poczta.pl")
                .password("aaaa1111")
                .createDate(LocalDateTime.now())
                .isActive(true)
                .isEnabled(true)
                .cart(cart)
                .build();

        userRepository.save(user);
        user.setCart(new Cart());
        cartRepository.save(cart);
        int userRepositoryBeforeUserRemoval = cartRepository.findAll().size();
        int cartRepositoryBeforeUserRemoval = cartRepository.findAll().size();
        userRepository.deleteById(user.getId());

        //When
        int userRepositoryAfterUserRemoval = cartRepository.findAll().size();
        int cartRepositoryAfterUserRemoval = cartRepository.findAll().size();

        //Then

        assertEquals(1, userRepositoryBeforeUserRemoval);
        assertEquals(1, cartRepositoryBeforeUserRemoval);
        assertEquals(0, userRepositoryAfterUserRemoval);
        assertEquals(0, cartRepositoryAfterUserRemoval);

        //Cleanup
    }
    
}