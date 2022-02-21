package com.kodilla.ecommerce.controller;

import com.kodilla.ecommerce.domain.ProductDto;
import com.kodilla.ecommerce.exception.*;
import com.kodilla.ecommerce.mapper.ProductMapper;
import com.kodilla.ecommerce.service.DbServiceProduct;
import com.kodilla.ecommerce.service.ModificationTokenService;
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
    private final ModificationTokenService modificationTokenService;

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
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto, @RequestParam String modificationToken) throws ProductNotFoundException, GroupNotFoundException, ProductNameIsEmptyException, ProductExistInRepositoryException, ModificationTokenNotFoundException, ModificationTokenNotValidException {
        modificationTokenService.checkIfModificationTokenValid(modificationToken);
        return ResponseEntity.ok(productMapper.mapToDto(serviceProduct.updateProduct(id, productMapper.mapToProduct(productDto))));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/deleteProduct/{idProduct}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long idProduct, @RequestParam String modificationToken ) throws ProductNotFoundException, ModificationTokenNotFoundException, ModificationTokenNotValidException {
        modificationTokenService.checkIfModificationTokenValid(modificationToken);
        return ResponseEntity.ok(productMapper.mapToDto(serviceProduct.deleteProduct(idProduct)));
    }
}