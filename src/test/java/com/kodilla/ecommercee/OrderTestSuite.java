package com.kodilla.ecommercee;


import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
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
        Long id = order1.getId();
        List<Order> orders = orderRepository.findAll();

        //Then
        assertEquals(1, orders.size());

        //CleanUp
            try {
                orderRepository.deleteById(id);
            } catch (Exception e) {
                System.out.println("SOMETHING WENT WRONG HERE" + e.getMessage());
            }
    }

    @Test
    public void testRetrieveAllOrders() {
        //Given
        Order order1 = Order.builder()
                .cart(null)
                .orderDate(LocalDate.now())
                .isPaid(false)
                .isSent(false)
                .shippingPrice(new BigDecimal(5))
                .build();

        Order order2 = Order.builder()
                .cart(null)
                .orderDate(LocalDate.now())
                .isPaid(false)
                .isSent(false)
                .shippingPrice(new BigDecimal(10))
                .build();

        orderRepository.save(order1);
        orderRepository.save(order2);
        //When
        Long id1 = order1.getId();
        Long id2 = order2.getId();
        List<Order> orders = orderRepository.findAll();
        //Then
        assertEquals(2, orders.size());

        //CleanUp
        try {
            orderRepository.deleteById(id1);
            orderRepository.deleteById(id2);

        } catch (Exception e) {
            System.out.println("SOMETHING WENT WRONG HERE" + e.getMessage());
        }
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
                .cartName("Koszyk testowy numer 1")
                .products(new ArrayList<>())
                .build();

        cartRepository.save(cart1);

        Product milk = Product.builder()
                .name("Mleko")
                .productDescription("Testujemy mleko w koszyku")
                .price(BigDecimal.valueOf(4.20))
                .group(null)
                .carts(new ArrayList<>())
                .build();

        Product water = Product.builder()
                .name("Woda")
                .productDescription("Testujemy wodę w koszyku")
                .price(BigDecimal.valueOf(5.20))
                .group(null)
                .carts(new ArrayList<>())
                .build();

        Product butter = Product.builder()
                .name("masło")
                .productDescription("Testujemy masło w koszyku")
                .price(BigDecimal.valueOf(6.60))
                .group(null)
                .carts(new ArrayList<>())
                .build();

        productRepository.save(milk);
        productRepository.save(water);
        productRepository.save(butter);

        user1.getOrders().add(order1);
        order1.setCart(cart1);
        cart1.getProducts().add(milk);
        cart1.getProducts().add(water);
        cart1.getProducts().add(butter);

        Long idUser = user1.getId();
        Long idOrder = order1.getId();
        Long idCart = cart1.getId();
        Long idProduct1 =  milk.getId();
        Long idProduct2 =  water.getId();
        Long idProduct3 =  butter.getId();

        //When
        List<Cart> cartList = cartRepository.findAll();
        List<Order> orders = orderRepository.findAll();
        List<Product> products = productRepository.findAll();

        //Then
        assertEquals(1, orders.size());
        assertEquals(1, cartList.size());
        assertEquals(3, products.size());

        //CleanUp
        try {
            userRepository.deleteById(idUser);
            productRepository.deleteById(idProduct1);
            productRepository.deleteById(idProduct2);
            productRepository.deleteById(idProduct3);
            orderRepository.deleteById(idOrder);
            cartRepository.deleteById(idCart);
        } catch (Exception e) {
            System.out.println("SOMETHING WENT WRONG HERE" + e.getMessage());
        }
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

        Long orderId = order1.getId();

        //When
        orderRepository.deleteById(orderId);

        //Then
        boolean orderExists = orderRepository.existsById(orderId);
        assertFalse(orderExists);

        //CleanUp
        try {
            orderRepository.deleteById(orderId);
        } catch (Exception e) {
            System.out.println("SOMETHING WENT WRONG HERE" + e.getMessage());
        }
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
        Long orderId = order1.getId();
        //Then
        assertTrue(order1.isPaid());

        //CleanUp
        try {
            orderRepository.deleteById(orderId);
        } catch (Exception e) {
            System.out.println("SOMETHING WENT WRONG HERE" + e.getMessage());
        }
    }
}