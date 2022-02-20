package com.kodilla.ecommerce.repository;

import com.kodilla.ecommerce.domain.Cart;
import com.kodilla.ecommerce.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    @EntityGraph(value = "graph.User.orders")
    List<User> findAll();

    User findByCart(Cart cart);

    Optional<User> findByUsername(String username);

    boolean existsUserByUsername(String username);
}
