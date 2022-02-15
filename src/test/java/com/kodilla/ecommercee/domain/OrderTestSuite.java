package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTestSuite {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveOrder() {
        //Given
        Order order1 = Order.builder()
                .orderDate(LocalDate.now())
                .isPaid(false)
                .isSent(false)
                .shippingPrice(new BigDecimal(0))
                .totalPrice(new BigDecimal(2))
                .build();

        //When
        orderRepository.save(order1);
        List<Order> orders = orderRepository.findAll();

        //Then
        assertEquals(1, orders.size());
    }

    @Test
    public void testRetrieveAllOrders() {
        //Given
        Order order1 = Order.builder()
                .orderDate(LocalDate.now())
                .isPaid(false)
                .isSent(false)
                .shippingPrice(new BigDecimal(5))
                .totalPrice(new BigDecimal(4))
                .build();

        Order order2 = Order.builder()
                .orderDate(LocalDate.now())
                .isPaid(false)
                .isSent(false)
                .shippingPrice(new BigDecimal(10))
                .totalPrice(new BigDecimal(2))
                .build();

        orderRepository.save(order1);
        orderRepository.save(order2);

        Long id1 = order1.getId();
        Long id2 = order2.getId();

        //When
        List<Order> orders = orderRepository.findAll();

        //Then
        assertEquals(2, orders.size());

        //CleanUp
        orderRepository.deleteById(id1);
        orderRepository.deleteById(id2);
    }

    @Test
    public void testUpdateOrder() {
        //Given
        Order order1 = Order.builder()
                .orderDate(LocalDate.now())
                .isPaid(false)
                .isSent(false)
                .shippingPrice(new BigDecimal(0))
                .totalPrice(new BigDecimal(2))
                .build();

        orderRepository.save(order1);

        Long id1 = order1.getId();

        //When
        order1.setPaid(true);
        orderRepository.save(order1);

        //Then
        assertTrue(order1.isPaid());

        //CleanUp
        orderRepository.deleteById(id1);
    }
}