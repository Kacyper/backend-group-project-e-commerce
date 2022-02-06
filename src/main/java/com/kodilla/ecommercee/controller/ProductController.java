package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.ProductDto;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @GetMapping
    public List<ProductDto> getProducts() {
        return new ArrayList<>();
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        return ProductDto.builder()
                .id(1L)
                .name("name")
                .groupId(1L)
                .build();
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return ProductDto.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .groupId(productDto.getGroupId())
                .build();
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return ProductDto.builder()
                .id(id)
                .name(productDto.getName())
                .groupId(productDto.getGroupId())
                .build();
    }

    //for testing return Long, later on it has to come back to void
    @DeleteMapping("/{id}")
    public Long deleteProduct(@PathVariable Long id) {
        return id;
    }
}
