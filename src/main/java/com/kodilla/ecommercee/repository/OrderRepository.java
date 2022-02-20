package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;
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