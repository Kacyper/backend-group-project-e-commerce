package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.exception.CartNotFoundException;
import com.kodilla.ecommercee.exception.OrderNotFoundException;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DbServiceOrder {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(final Long id) throws OrderNotFoundException {
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    public Order createOrder(final Long idCart) throws CartNotFoundException {
        Cart cart = cartRepository.findById(idCart).orElseThrow(CartNotFoundException::new);
        User user = userRepository.findByCart(cart);
        BigDecimal totalPrice = cart.getProducts().stream()
                .filter(Product::isAvailable)
                .map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDate.now())
                .shippingPrice(setShippingCompany(0))
                .totalPrice(totalPrice)
                .sent(false)
                .paid(false)
                .build();

        cart.getProducts().clear();

        orderRepository.save(order);
        cartRepository.save(cart);

        return order;
    }

    public BigDecimal setShippingCompany(final int shippingComapny) {
        switch (shippingComapny) {
            case 1:
                return new BigDecimal("10"); //paczkomat
            case 2:
                return new BigDecimal("15"); //DHL
            case 3:
                return new BigDecimal("69"); //awizo od poczty polskiej
            case 4:
                return new BigDecimal("420"); //BigJoint company
            default:
                return BigDecimal.ZERO; //gdy odbiór osobity
        }
    }

    public Order updateOrderChooseShippingCompany(final Long id, final int shippingCompany) throws OrderNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        order.setShippingPrice(setShippingCompany(shippingCompany));
        return order;
    }

    public Order updateOrderIsSent(final Long id) throws OrderNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        order.setSent(true);
        return order;
    }

    public Order updateOrderIsPaid(final Long id) throws OrderNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        order.setPaid(true);
        return order;
    }
}