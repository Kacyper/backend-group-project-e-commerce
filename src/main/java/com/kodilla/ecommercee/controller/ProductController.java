package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.exception.ModificationTokenNotFoundException;
import com.kodilla.ecommercee.exception.ModificationTokenNotValidException;
import com.kodilla.ecommercee.exception.ProductNotFoundException;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.service.DbServiceProduct;
import com.kodilla.ecommercee.service.ModificationTokenService;
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
    private final ModificationTokenService modificationTokenService;

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(ProductMapper.mapToListDto(serviceProduct.getProducts()));
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id)
            throws ProductNotFoundException {
        return ResponseEntity.ok(ProductMapper.mapToDto(serviceProduct.getProduct(id)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(ProductMapper
                .mapToDto(serviceProduct.createProduct(ProductMapper.mapToProduct(productDto))));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto, @RequestParam String modificationToken)
            throws ProductNotFoundException, ModificationTokenNotFoundException, ModificationTokenNotValidException {
        modificationTokenService.checkIfModificationTokenValid(modificationToken);
        return ResponseEntity.ok(ProductMapper
                .mapToDto(serviceProduct.updateProduct(id, ProductMapper.mapToProduct(productDto))));
    }
}