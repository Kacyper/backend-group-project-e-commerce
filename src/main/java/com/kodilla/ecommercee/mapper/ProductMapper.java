package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.exception.GroupNotFoundException;
import com.kodilla.ecommercee.exception.ProductNotFoundException;
import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = mapToDto(product);
            productDtos.add(productDto);
        }
        return productDtos;
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
