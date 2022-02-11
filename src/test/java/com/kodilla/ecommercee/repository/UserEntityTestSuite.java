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
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

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

        userRepository.save(user1);
        userRepository.save(user2);

        //When
        Long user1Id = user1.getId();
        Long user2Id = user2.getId();
        int numberOfUsersBeforeDelete = userRepository.findAll().size();
        userRepository.deleteById(user2Id);
        int numberOfUsersAfterDelete = userRepository.findAll().size();

        //Then
        assertEquals(2, numberOfUsersBeforeDelete);
        assertEquals(1, numberOfUsersAfterDelete);

        //Cleanup
        userRepository.deleteById(user1Id);
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
    public void OneToManyRelationWithOrderTest() {
        //Given
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

        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);

        List<Order> orders = orderRepository.findAll();
        orders.add(order1);
        orders.add(order1);
        orders.add(order1);

        User user = User.builder()
                .username("Kate")
                .email("kate@poczta.pl")
                .password("aaaa1111")
                .createDate(LocalDateTime.now())
                .isActive(true)
                .isEnabled(true)
                .orders(orderRepository.findAll())
                .build();

        userRepository.save(user);

        //When
        int numberOfUserOrdersBeforeAddingToUser = user.getOrders().size();
        order1.setUser(user);
        order2.setUser(user);
        order3.setUser(user);
        user.getOrders().add(order1);
        user.getOrders().add(order2);
        user.getOrders().add(order3);
        int numberOfUserOrdersAfterAddingToUser = user.getOrders().size();

        //Then
        assertEquals(3, userRepository.findAll(user.getOrders().size()));
        assertEquals(3, orderRepository.findAll().size());
        assertEquals(0, numberOfUserOrdersBeforeAddingToUser);
        assertEquals(3, numberOfUserOrdersAfterAddingToUser);

        //Cleanup
        userRepository.deleteById(user.getId());
        orderRepository.deleteById(order1.getId());
        orderRepository.deleteById(order2.getId());
        orderRepository.deleteById(order3.getId());
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

        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);

        user.getOrders().add(order1);
        user.getOrders().add(order2);
        user.getOrders().add(order3);

        //When
        List<Order> orders = orderRepository.findAll();
        int numberOfUserOrdersBeforeAddingToUser = user.getOrders().size();

        orderRepository.delete(order1);
        int numberOfUserOrdersAfterAddingToUser = user.getOrders().size();
        int numberOfOrdersInRepository = orderRepository.findAll().size();

        int numberOfUserOrdersAfterDelete = user.getOrders().size();
        System.out.println("number of Orders in Repository After Delete " + numberOfOrdersInRepository);

        //Then
        assertEquals(0, numberOfUserOrdersBeforeAddingToUser);
        assertEquals(3, numberOfUserOrdersAfterAddingToUser);
        assertEquals(2, numberOfUserOrdersAfterDelete);

        //Cleanup
        orderRepository.deleteById(order2.getId());
        orderRepository.deleteById(order3.getId());
        System.out.println("Number of users " + userRepository.findAll().size());
        System.out.println("Number of orders " + orderRepository.findAll().size());
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

        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
        userRepository.save(user);

        //When
        int userOrdersBeforeAddingOrdersToUser = user.getOrders().size();
        user.getOrders().add(order1);
        user.getOrders().add(order2);
        user.getOrders().add(order3);
        int userOrdersAfterAddingOrdersToUser = user.getOrders().size();
        List<User> users = userRepository.findAll();
        int numberOfUsersBeforeDeletingUser = users.size();
        userRepository.delete(user);
        int numberOfOrdersAfterDeletingUser = orderRepository.findAll().size();
        int numberOfUsersAfterDeletingUser = userRepository.findAll().size();


        //Then
        assertEquals(0, userOrdersBeforeAddingOrdersToUser);
        assertEquals(3, userOrdersAfterAddingOrdersToUser);
        assertEquals(1, numberOfUsersBeforeDeletingUser);
        assertEquals(0, numberOfOrdersAfterDeletingUser);
        assertEquals(0, numberOfUsersAfterDeletingUser);

        //Cleanup
        orderRepository.deleteById(order1.getId());
        orderRepository.deleteById(order2.getId());
        orderRepository.deleteById(order3.getId());
    }
}