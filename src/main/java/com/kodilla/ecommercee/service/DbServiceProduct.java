package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.ProductNotFoundException;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbServiceProduct {

    private final ProductRepository productRepository;

    public Product findProduct(final Long idProduct) throws ProductNotFoundException {
        return productRepository.findById(idProduct).orElseThrow(ProductNotFoundException::new);
    }
}
