package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.exception.ProductNotFoundException;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.service.DbServiceProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final DbServiceProduct serviceProduct;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(ProductMapper.mapToListDto(serviceProduct.getProducts()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id)
            throws ProductNotFoundException {
        return ResponseEntity.ok(ProductMapper.mapToDto(serviceProduct.getProduct(id)));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(ProductMapper
                .mapToDto(serviceProduct.createProduct(ProductMapper.mapToProduct(productDto))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto)
            throws ProductNotFoundException {
        return ResponseEntity.ok(ProductMapper
                .mapToDto(serviceProduct.updateProduct(id, ProductMapper.mapToProduct(productDto))));
    }
}