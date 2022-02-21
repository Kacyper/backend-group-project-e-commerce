package com.kodilla.ecommerce.mapper;

import com.kodilla.ecommerce.domain.Product;
import com.kodilla.ecommerce.domain.ProductDto;
import com.kodilla.ecommerce.exception.GroupNotFoundException;
import com.kodilla.ecommerce.exception.ProductNotFoundException;
import com.kodilla.ecommerce.repository.GroupRepository;
import com.kodilla.ecommerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductMapper {

    private final ProductRepository productRepository;
    private final GroupRepository groupRepository;

    public Product mapToProduct(final ProductDto productDto) throws GroupNotFoundException {
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .productDescription(productDto.getProductDescription())
                .available(productDto.isAvailable())
                .group(groupRepository.findById(productDto.getIdGroup()).orElseThrow(GroupNotFoundException::new))
                .build();
    }

    public ProductDto mapToDto(final Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .productDescription(product.getProductDescription())
                .available(product.isAvailable())
                .idGroup(product.getGroup().getId())
                .build();
    }

    public List<ProductDto> mapToListDto(final List<Product> products) {
        return products.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<Product> mapToProductsFromIdProducts(final List<Long> productIds) throws ProductNotFoundException {
        List<Product> products = new ArrayList<>();

        if(productIds.size() > 0) {

            for (Long idProduct : productIds) {
                Product product = productRepository.findById(idProduct).orElseThrow(ProductNotFoundException::new);
                products.add(product);
            }
        }
        return products;
    }

    public List<Long> mapToProductsIdsFromProducts(final List<Product> products) {
        List<Long> productIds = new ArrayList<>();

        if(products.size() > 0) {
            productIds = products.stream()
                    .map(Product::getId)
                    .collect(Collectors.toList());
        }

        return productIds;
    }
}