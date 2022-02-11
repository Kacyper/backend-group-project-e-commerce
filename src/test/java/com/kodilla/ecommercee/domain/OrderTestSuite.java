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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTestSuite {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository  cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testCreateNewOrder() {
        //Given
        Order order1 = Order.builder()
                .orderDate(LocalDate.now())
                .isPaid(false)
                .isSent(false)
                .shippingPrice(new BigDecimal(0))
                .build();

        //When
        orderRepository.save(order1);
        List<Order> orders = orderRepository.findAll();

        //Then
        assertEquals(1, orders.size());

        //CleanUp
                orderRepository.deleteAll();
    }

    @Test
    public void testRetrieveAllOrders() {
        //Given
        Order order1 = Order.builder()
                .orderDate(LocalDate.now())
                .isPaid(false)
                .isSent(false)
                .shippingPrice(new BigDecimal(5))
                .build();

        Order order2 = Order.builder()
                .orderDate(LocalDate.now())
                .isPaid(false)
                .isSent(false)
                .shippingPrice(new BigDecimal(10))
                .build();

        orderRepository.save(order1);
        orderRepository.save(order2);
        //When
        List<Order> orders = orderRepository.findAll();
        //Then
        assertEquals(2, orders.size());

        //CleanUp
            orderRepository.deleteAll();
    }

    @Test
    public void testAddOrderToCart() {
        //Given
        User user1 = User.builder()
                .username("Sam")
                .createDate(LocalDateTime.now().minusDays(3))
                .email("max@as.pl")
                .password("asd")
                .isEnabled(true)
                .isActive(true)
                .orders(new ArrayList<>())
                .build();

        userRepository.save(user1);

        Order order1 = Order.builder()
                .orderDate(LocalDate.now())
                .isPaid(false)
                .isSent(false)
                .shippingPrice(new BigDecimal(0))
                .build();

        orderRepository.save(order1);

        Cart cart1 = Cart.builder()
                .products(new ArrayList<>())
                .build();

        cartRepository.save(cart1);

        Product milk = Product.builder()
                .name("Mleko")
                .group(null)
                .carts(new ArrayList<>())
                .productDescription("hgf")
                .price(new BigDecimal(5))
                .build();

        Product water = Product.builder()
                .name("Woda")
                .group(null)
                .carts(new ArrayList<>())
                .productDescription("hgf")
                .price(new BigDecimal(5))
                .build();

        Product butter = Product.builder()
                .name("masło")
                .group(null)
                .carts(new ArrayList<>())
                .productDescription("ttft")
                .price(new BigDecimal(7))
                .build();

        productRepository.save(milk);
        productRepository.save(water);
        productRepository.save(butter);

        user1.getOrders().add(order1);
        cart1.getProducts().add(milk);
        cart1.getProducts().add(water);
        cart1.getProducts().add(butter);

        //When
        List<Cart> cartList = cartRepository.findAll();
        List<Order> orders = orderRepository.findAll();
        List<Product> products = productRepository.findAll();

        //Then
        assertEquals(1, orders.size());
        assertEquals(1, cartList.size());
        assertEquals(3, products.size());

        //CleanUp
            userRepository.deleteAll();
            productRepository.deleteAll();
            orderRepository.deleteAll();
            cartRepository.deleteAll();
    }

    @Test
    public void testDeleteOrder() {
        //Given
        Order order1 = Order.builder()
                .orderDate(LocalDate.now())
                .isPaid(false)
                .isSent(false)
                .shippingPrice(new BigDecimal(0))
                .build();
        orderRepository.save(order1);

        //When
        Long orderId = order1.getId();
        orderRepository.deleteById(orderId);

        //Then
        boolean orderExists = orderRepository.existsById(orderId);
        assertFalse(orderExists);
    }

    @Test
    public void testUpdateOrder() {
        //Given
        Order order1 = Order.builder()
                .orderDate(LocalDate.now())
                .isPaid(false)
                .isSent(false)
                .shippingPrice(new BigDecimal(0))
                .build();

        orderRepository.save(order1);

        //When
        order1.setPaid(true);
        orderRepository.save(order1);

        //Then
        assertTrue(order1.isPaid());

        //CleanUp
            orderRepository.deleteAll();
    }
}