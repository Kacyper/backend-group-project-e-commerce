package com.kodilla.ecommercee.repository;

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
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;

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
        List<User> userList = userRepository.findAll();
        //Then
        try {
            assertEquals(1, userList.size());
        } finally {
            userRepository.deleteAll();
        }
    }

    @Test
    public void deleteUserTest() {
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

        //When
        userRepository.save(user1);
        userRepository.save(user2);
        Long user2Id = user2.getId();

        int numberOfUsersBeforeDelete = userRepository.findAll().size();
        userRepository.deleteById(user2Id);

        //Then
        try {
            assertEquals(2, numberOfUsersBeforeDelete);
            assertEquals(1, userRepository.findAll().size());
        } finally {
            userRepository.deleteAll();
        }
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

        //When
        userRepository.save(user1);
        userRepository.save(user2);
        String user1UserName = user1.getUsername();
        userRepository.findById(1L);

        //Then
        try {
            assertEquals("Kate", user1UserName);
        } finally {
            userRepository.deleteAll();
        }
    }

    @Test
    public void OneToManyRelationWithOrderTest() {
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

        Order order1 = Order.builder()
                .orderDate(LocalDate.now())
                .shippingPrice(new BigDecimal("10.99"))
                .isPaid(false)
                .isSent(false)
                .build();

        Order order2 = Order.builder()
                .orderDate(LocalDate.now())
                .shippingPrice(new BigDecimal("10.99"))
                .isPaid(false)
                .isSent(false)
                .build();

        Order order3 = Order.builder()
                .orderDate(LocalDate.now())
                .shippingPrice(new BigDecimal("10.99"))
                .isPaid(false)
                .isSent(false)
                .build();

        //When
        userRepository.save(user);
        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);

        user.getOrders().add(order1);
        user.getOrders().add(order2);
        user.getOrders().add(order3);

        int numberOfUserOrders = user.getOrders().size();

        //Then
        try {
            assertEquals(3, numberOfUserOrders);
        } finally {
            userRepository.deleteAll();
            orderRepository.deleteAll();
        }
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

        //When
        userRepository.save(user);
        user.setEnabled(false);
        userRepository.save(user);

        //Then
        try {
            assertFalse(user.isEnabled());
        } finally {
            userRepository.deleteAll();
            orderRepository.deleteAll();
        }
    }

    @Test
    public void OneToManyRelationWithOrderUserDeleteOrder() {
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

        Order order1 = Order.builder()
                .orderDate(LocalDate.now())
                .shippingPrice(new BigDecimal("10.99"))
                .isPaid(false)
                .isSent(false)
                .build();

        Order order2 = Order.builder()
                .orderDate(LocalDate.now())
                .shippingPrice(new BigDecimal("10.99"))
                .isPaid(false)
                .isSent(false)
                .build();

        Order order3 = Order.builder()
                .orderDate(LocalDate.now())
                .shippingPrice(new BigDecimal("10.99"))
                .isPaid(false)
                .isSent(false)
                .build();

        //When
        userRepository.save(user);
        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);

        user.getOrders().add(order1);
        user.getOrders().add(order2);
        user.getOrders().add(order3);
        user.getOrders().remove(order1);


        int numberOfUserOrders = user.getOrders().size();

        //Then
        try {
            assertEquals(2, numberOfUserOrders);
        } finally {
            userRepository.deleteAll();
            orderRepository.deleteAll();
        }
    }

    @Test
    public void OneToManyRelationWithOrderUserDeleteUser() {
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

        Order order1 = Order.builder()
                .orderDate(LocalDate.now())
                .shippingPrice(new BigDecimal("10.99"))
                .isPaid(false)
                .isSent(false)
                .build();

        Order order2 = Order.builder()
                .orderDate(LocalDate.now())
                .shippingPrice(new BigDecimal("10.99"))
                .isPaid(false)
                .isSent(false)
                .build();

        Order order3 = Order.builder()
                .orderDate(LocalDate.now())
                .shippingPrice(new BigDecimal("10.99"))
                .isPaid(false)
                .isSent(false)
                .build();

        //When
        userRepository.save(user);
        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);

        user.getOrders().add(order1);
        user.getOrders().add(order2);
        user.getOrders().add(order3);

        userRepository.delete(user);

        int numberOfOrders = orderRepository.findAll().size();
        int numberOfUsers = userRepository.findAll().size();

        //Then
        try {
            assertEquals(0, numberOfOrders);
            assertEquals(0, numberOfUsers);
        } finally {
            userRepository.deleteAll();
            orderRepository.deleteAll();
        }
    }
}

