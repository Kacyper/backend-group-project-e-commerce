package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.exception.*;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.service.DbServiceProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final DbServiceProduct serviceProduct;
    private final ProductMapper productMapper;

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() throws EmptyProductRepositoryException {
        return ResponseEntity.ok(productMapper.mapToListDto(serviceProduct.getProducts()));
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) throws ProductNotFoundException {
        return ResponseEntity.ok(productMapper.mapToDto(serviceProduct.getProduct(id)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) throws GroupNotFoundException, ProductNameIsEmptyException, ProductExistInRepositoryException {
        return ResponseEntity.ok(productMapper.mapToDto(serviceProduct.createProduct(productMapper.mapToProduct(productDto))));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/deleteProduct")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) throws ProductNotFoundException, GroupNotFoundException, ProductNameIsEmptyException, ProductExistInRepositoryException {
        return ResponseEntity.ok(productMapper.mapToDto(serviceProduct.updateProduct(id, productMapper.mapToProduct(productDto))));
    }
}