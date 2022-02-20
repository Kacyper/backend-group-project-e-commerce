package com.kodilla.ecommerce.repository;

import com.kodilla.ecommerce.domain.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    @Override
    @EntityGraph(value = "graph.Order.products")
    List<Order> findAll();

    @Override
    @EntityGraph(value = "graph.Order.products")
    Optional<Order> findById(Long id);
}