package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.exception.*;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.service.DbServiceProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final DbServiceProduct serviceProduct;
    private final ProductMapper productMapper;

    @GetMapping
    public List<ProductDto> getProducts() throws EmptyProductRepositoryException {
        return productMapper.mapToListDto(serviceProduct.getProducts());
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id) throws ProductNotFoundException {
        return productMapper.mapToDto(serviceProduct.getProduct(id));
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) throws GroupNotFoundException, ProductNameIsEmptyException, ProductExistInRepositoryException {
        return productMapper.mapToDto(serviceProduct.createProduct(productMapper.mapToProduct(productDto)));
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) throws ProductNotFoundException, GroupNotFoundException, ProductNameIsEmptyException, ProductExistInRepositoryException {
        return productMapper.mapToDto(serviceProduct.updateProduct(id, productMapper.mapToProduct(productDto)));
    }

    @PutMapping("deleteProduct")
    public ProductDto deleteProduct(@RequestParam Long id) throws ProductNotFoundException {
        return productMapper.mapToDto(serviceProduct.deleteProduct(id));
    }
}