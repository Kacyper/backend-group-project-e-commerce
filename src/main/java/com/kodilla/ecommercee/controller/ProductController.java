package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.exception.ProductNotFoundException;
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

    @GetMapping
    public List<ProductDto> getProducts() {
        return ProductMapper.mapToListDto(serviceProduct.getProducts());
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id)
            throws ProductNotFoundException {
        return ProductMapper.mapToDto(serviceProduct.getProduct(id));
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return ProductMapper.mapToDto(serviceProduct.createProduct(ProductMapper.mapToProduct(productDto)));
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto)
            throws ProductNotFoundException {
        return ProductMapper.mapToDto(serviceProduct.updateProduct(id, ProductMapper.mapToProduct(productDto)));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        serviceProduct.deleteProduct(id);
    }
}