package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.exception.*;
import com.kodilla.ecommercee.repository.ProductRepository;
import com.kodilla.ecommercee.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DbServiceProduct {

    private final ProductRepository productRepository;

    public List<Product> getProducts() throws EmptyProductRepositoryException {
        List<Product> products = productRepository.findAll().stream().filter(p -> p.isAvailable()).collect(Collectors.toList());
        if(products.size() < 1) {
            throw new EmptyProductRepositoryException();
        }
        return products;
    }

    public Product getProduct(final Long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    public Product createProduct(final Product product) throws ProductNameIsEmptyException, ProductExistInRepositoryException {
        validateProductName(product.getName());
        return productRepository.save(product);
    }

    public Product updateProduct(final Long id, final Product product) throws ProductNotFoundException, ProductNameIsEmptyException, ProductExistInRepositoryException {
        Product productFromDb = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        validateProductName(product.getName());
        productFromDb.setName(product.getName());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setProductDescription(product.getProductDescription());
        productFromDb.setAvailable(product.isAvailable());
        productFromDb.setGroup(product.getGroup());
        return productRepository.save(productFromDb);
    }

    public Product deleteProduct(final Long id) throws ProductNotFoundException {
        Product deletedProduct = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        deletedProduct.setAvailable(false);
        return productRepository.save(deletedProduct);
    }

    private void validateProductName(String productName) throws ProductNameIsEmptyException, ProductExistInRepositoryException {
        if (productRepository.existsProductByName(productName)) {
            throw new ProductExistInRepositoryException();
        }

        if (productName.isEmpty()) {
            throw new ProductNameIsEmptyException();
        }
    }
}