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
        return new ProductDto();
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return new ProductDto();
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        return new ProductDto();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {

    }
}
