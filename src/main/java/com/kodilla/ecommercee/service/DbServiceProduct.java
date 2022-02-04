package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbServiceProduct {

    private final ProductRepository productRepository;

    public boolean ifExist(final Long idProduct) {
        return productRepository.existsById(idProduct);
    }
}
