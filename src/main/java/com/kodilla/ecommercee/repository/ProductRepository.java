package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    @Override
    List<Product> findAll();

    @Query
    List<Product> retrieveAvailableProducts();
}
