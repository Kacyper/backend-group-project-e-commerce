package com.kodilla.ecommerce.service;

import com.kodilla.ecommerce.exception.*;
import com.kodilla.ecommerce.repository.ProductRepository;
import com.kodilla.ecommerce.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DbServiceProduct {

    private final ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAllByAvailable(true);
    }

    public Product getProduct(final Long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    public Product createProduct(final Product product) throws ProductNameIsEmptyException, ProductExistInRepositoryException {
        validateProductName(0L, product.getName());
        return productRepository.save(product);
    }

    public Product updateProduct(final Long id, final Product product) throws ProductNotFoundException, ProductNameIsEmptyException, ProductExistInRepositoryException {
        Product productFromDb = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        validateProductName(id, product.getName());
        productFromDb.setName(product.getName());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setProductDescription(product.getProductDescription());
        productFromDb.setAvailable(product.isAvailable());
        productFromDb.setGroup(product.getGroup());
        return productRepository.save(productFromDb);
    }

    public Product deleteProduct(final Long id) throws ProductNotFoundException, ProductIsAlreadyUnavailable {
        Product deletedProduct = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        if(deletedProduct.isAvailable()) {
            deletedProduct.setAvailable(false);
        } else throw new ProductIsAlreadyUnavailable();

        return productRepository.save(deletedProduct);
    }

    private void validateProductName(Long id, String productName) throws ProductNameIsEmptyException, ProductExistInRepositoryException {
        List<String> productNames = productRepository.findAll().stream().map(Product::getName).collect(Collectors.toList());

        if (id != 0L) {
            productNames = productRepository.findAll().stream()
                    .filter(product -> product.getName() != productRepository.findById(id).get().getName())
                    .map(Product::getName)
                    .collect(Collectors.toList());
        }

        for (String name : productNames) {
            if (name.equals(productName)) {
                throw new ProductExistInRepositoryException();
            }
        }

        if (productName.isEmpty()) {
            throw new ProductNameIsEmptyException();
        }
    }
}