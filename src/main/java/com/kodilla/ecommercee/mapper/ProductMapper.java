package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.ProductDto;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static Product mapToProduct(final ProductDto dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
//                .price(dto.getPrice())
//                .productDescription(dto.getProductDescription())
//                .group() need Group mapper implementation
                .build();
    }

    public static ProductDto mapToDto(final Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .productDescription(product.getProductDescription())
//                .groupDto() need Group mapper implementation
                .build();
    }

    public static List<ProductDto> mapToListDto(final List<Product> products) {
        return products.stream()
                .map(ProductMapper::mapToDto)
                .collect(Collectors.toList());
    }

}
