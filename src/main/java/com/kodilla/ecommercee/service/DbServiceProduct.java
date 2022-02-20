package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.repository.ProductRepository;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DbServiceProduct {

    private final ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(final Long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    public Product createProduct(final Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(final Long id, final Product product)
            throws ProductNotFoundException {
        Product productFromDb = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        productFromDb.setName(product.getName());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setProductDescription(product.getProductDescription());
        productFromDb.setGroup(product.getGroup());

        return productRepository.save(productFromDb);
    }
}
