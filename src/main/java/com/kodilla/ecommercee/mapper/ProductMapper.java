package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.exception.ProductNotFoundException;
import com.kodilla.ecommercee.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductMapper {

    private final ProductRepository productRepository;

    public static Product mapToProduct(final ProductDto dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .productDescription(dto.getProductDescription())
                .available(dto.isAvailable())
                .group(dto.getGroup())
                .build();
    }

    public static ProductDto mapToDto(final Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .productDescription(product.getProductDescription())
                .available(product.isAvailable())
                .group(product.getGroup())
                .build();
    }

    public static List<ProductDto> mapToListDto(final List<Product> products) {
        return products.stream()
                .map(ProductMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<Product> mapToProductsFromIdProducts(final List<Long> productIds) throws ProductNotFoundException {
        List<Product> products = new ArrayList<>();

        if(productIds.size() > 0) {

            for (Long idProduct : productIds) {
                Product product = productRepository.findById(idProduct).orElseThrow(ProductNotFoundException::new);
                products.add(product);
            }
            return products;

        } else return new ArrayList<>();
    }

    public List<Long> mapToProductsIdsFromProducts(final List<Product> products) {
        List<Long> productIds = new ArrayList<>();

        if(productIds.size() > 0) {

            for (Product product : products) {
                Long idProduct = product.getId();
                productIds.add(idProduct);
            }
            return productIds;

        } else return new ArrayList<>();
    }
}
