package com.kodilla.ecommerce.service;

import com.kodilla.ecommerce.domain.Cart;
import com.kodilla.ecommerce.domain.Order;
import com.kodilla.ecommerce.domain.Product;
import com.kodilla.ecommerce.domain.User;
import com.kodilla.ecommerce.exception.*;
import com.kodilla.ecommerce.repository.CartRepository;
import com.kodilla.ecommerce.repository.OrderRepository;
import com.kodilla.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DbServiceOrder {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public List<Order> getAllUserOrders(final Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return user.getOrders();
    }

    public Order getOrder(final Long id) throws OrderNotFoundException {
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    public Order createOrder(final Long idCart, final int shipping) throws CartNotFoundException, CartIsEmptyException, CartHasNoAvailableProductsException {
        Cart cart = cartRepository.findById(idCart).orElseThrow(CartNotFoundException::new);

        if (cart.getProducts().size() == 0) {
            throw new CartIsEmptyException();
        } else if (cart.getProducts().stream().noneMatch(Product::isAvailable)) {
            throw new CartHasNoAvailableProductsException();
        }

        User user = userRepository.findByCart(cart);
        BigDecimal totalPrice = cart.getProducts().stream()
                .filter(Product::isAvailable)
                .map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDate.now())
                .shippingPrice(setShippingCompany(shipping))
                .productsTotalPrice(totalPrice)
                .orderTotalPrice(new BigDecimal("0"))
                .sent(false)
                .paid(false)
                .products(new ArrayList<>())
                .build();

        order.setOrderTotalPrice(totalPrice.add(order.getShippingPrice()));

        order.setProducts(cart.getProducts().stream().filter(Product::isAvailable).collect(Collectors.toList()));
        cart.setProducts(new ArrayList<>());

        orderRepository.save(order);
        cartRepository.save(cart);

        return order;
    }

    public BigDecimal setShippingCompany(final int shippingCompany) {
        switch (shippingCompany) {
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

    public Order updateOrderChooseShippingCompany(final Long id, final int shippingCompany) throws OrderNotFoundException, OrderAlreadyProcessedException {
        Order order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);

        if (order.isPaid()) {
            throw new OrderAlreadyProcessedException();
        }

        order.setShippingPrice(setShippingCompany(shippingCompany));
        order.setOrderTotalPrice(order.getProductsTotalPrice().add(order.getShippingPrice()));
        orderRepository.save(order);
        return order;
    }

    public Order updateOrderIsSent(final Long id) throws OrderNotFoundException, OrderNotPaidException, OrderAlreadySentException {
        Order order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        if(!order.isPaid()) {
            throw new OrderNotPaidException();
        } else if (order.isSent()) {
            throw new OrderAlreadySentException();
        }
        order.setSent(true);
        orderRepository.save(order);
        return order;
    }

    public Order updateOrderIsPaid(final Long id) throws OrderNotFoundException, OrderAlreadyPaidException {
        Order order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        if (order.isPaid()) {
            throw new OrderAlreadyPaidException();
        }
        order.setPaid(true);
        orderRepository.save(order);
        return order;
    }
}