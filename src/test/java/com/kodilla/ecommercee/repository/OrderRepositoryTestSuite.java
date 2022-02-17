package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderRepositoryTestSuite {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveOrder() {
        //Given
        Order order1 = Order.builder()
                .orderDate(LocalDate.now())
                .paid(false)
                .sent(false)
                .shippingPrice(new BigDecimal(0))
                .productsTotalPrice(new BigDecimal("2"))
                .orderTotalPrice(new BigDecimal("2"))
                .products(new ArrayList<>())
                .build();

        //When
        orderRepository.save(order1);
        List<Order> orders = orderRepository.findAll();
        Long id1 = order1.getId();

        //Then
        assertEquals(1, orders.size());

        //CleanUp
        orderRepository.deleteById(id1);
    }

    @Test
    public void testRetrieveAllOrders() {
        //Given
        Order order1 = Order.builder()
                .orderDate(LocalDate.now())
                .paid(false)
                .sent(false)
                .shippingPrice(new BigDecimal(0))
                .productsTotalPrice(new BigDecimal("2"))
                .orderTotalPrice(new BigDecimal("2"))
                .products(new ArrayList<>())
                .build();

        Order order2 = Order.builder()
                .orderDate(LocalDate.now())
                .paid(false)
                .sent(false)
                .shippingPrice(new BigDecimal(0))
                .productsTotalPrice(new BigDecimal("2"))
                .orderTotalPrice(new BigDecimal("2"))
                .products(new ArrayList<>())
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
                .paid(false)
                .sent(false)
                .shippingPrice(new BigDecimal(0))
                .productsTotalPrice(new BigDecimal("2"))
                .orderTotalPrice(new BigDecimal("2"))
                .products(new ArrayList<>())
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

    @Test
    public void testCreateOrderWithProductsFromCart() {
        //Given
        Cart cart = Cart.builder()
                .products(new ArrayList<>())
                .build();

        Product milk = Product.builder()
                .name("Milk")
                .productDescription("test description for product")
                .price(new BigDecimal("3.50"))
                .group(null)
                .available(true)
                .build();

        cart.getProducts().add(milk);

        cartRepository.save(cart);
        Long cartId = cart.getId();
        productRepository.save(milk);
        Long milkId = milk.getId();

        //When
        Order order = Order.builder()
                .orderDate(LocalDate.now())
                .paid(false)
                .sent(false)
                .shippingPrice(new BigDecimal(0))
                .productsTotalPrice(new BigDecimal("2"))
                .orderTotalPrice(new BigDecimal("2"))
                .products(new ArrayList<>())
                .build();

        order.setProducts(cart.getProducts());

        orderRepository.save(order);
        Long orderId = order.getId();

        //Then
        List<Product> productsFromOrder = orderRepository.findById(orderId).get().getProducts();

        assertEquals(1, productsFromOrder.size());

        //CleanUp
        order.getProducts().clear();
        orderRepository.save(order);

        cartRepository.deleteById(cartId);
        productRepository.deleteById(milkId);
        orderRepository.deleteById(orderId);
    }
}