package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.exception.ProductNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProductRepositoryTestSuite {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    public void testSaveProduct() {
        //Given
        Product milk = Product.builder()
                .name("Milk")
                .productDescription("test description for product")
                .price(new BigDecimal("3.50"))
                .build();

        //When
        productRepository.save(milk);
        Long id = milk.getId();

        List<Product> products = productRepository.findAll();

        //Then
        assertEquals(1, products.size());

        //CleanUp
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Something went wrong here: ".toUpperCase() + e.getMessage());
        }
    }

    @Test
    public void testSaveAllProducts() {
        //Given
        Product milk = Product.builder()
                .name("Milk")
                .productDescription("test description for product")
                .price(new BigDecimal("3.50"))
                .build();

        Product butter = Product.builder()
                .name("Butter")
                .productDescription("test description for product")
                .price(new BigDecimal("5.50"))
                .build();

        //When
        List<Product> products = new ArrayList<>();
        products.add(milk);
        products.add(butter);

        productRepository.saveAll(products);

        //Then
        assertEquals(2, productRepository.findAll().size());

        //CleanUp
        try {
            for (Product product : products) {
                productRepository.deleteById(product.getId());
            }
        } catch (Exception e) {
            System.out.println("SOMETHING WENT WRONG HERE: " + e.getMessage());
        }
    }

    @Test
    public void testFindById() {
        //Given
        Product milk = Product.builder()
                .name("Milk")
                .productDescription("test description for product")
                .price(new BigDecimal("3.50"))
                .build();

        Product butter = Product.builder()
                .name("Butter")
                .productDescription("test description for product")
                .price(new BigDecimal("5.50"))
                .build();

        //When
        productRepository.save(milk);
        Long milkId = milk.getId();
        productRepository.save(butter);
        Long butterId = butter.getId();

        Optional<Product> optionalMilk = productRepository.findById(milkId);
        Optional<Product> optionalButter = productRepository.findById(butterId);

        //Then
        assertEquals(optionalMilk.get().getId(), milkId);
        assertEquals(optionalMilk.get().getName(), milk.getName());
        assertEquals(optionalButter.get().getId(), butterId);
        assertEquals(optionalButter.get().getName(), butter.getName());

        //CleanUp
        try {
            productRepository.deleteById(milkId);
            productRepository.deleteById(butterId);
        } catch (Exception e) {
            System.out.println("SOMETHING WENT WRONG HERE: " + e.getMessage());
        }
    }

    @Test
    public void testExistsById() {
        //Given
        Product milk = Product.builder()
                .name("Milk")
                .productDescription("test description for product")
                .price(new BigDecimal("3.50"))
                .build();

        //When
        productRepository.save(milk);
        Long id = milk.getId();

        boolean isMilkPresent = productRepository.existsById(id);

        //Then
        assertTrue(isMilkPresent);

        //CleanUp
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("SOMETHING WENT WRONG HERE: " + e.getMessage());
        }
    }

    @Test
    public void testCount() {
        //Given
        Product milk = Product.builder()
                .name("Milk")
                .productDescription("test description for product")
                .price(new BigDecimal("3.50"))
                .build();

        //When
        productRepository.save(milk);
        Long id = milk.getId();

        int count = (int) productRepository.count();

        //Then
        assertEquals(1, count);

        //CleanUp
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("SOMETHING WENT WRONG HERE: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteById() {
        //Given
        Product milk = Product.builder()
                .name("Milk")
                .productDescription("test description for product")
                .price(new BigDecimal("3.50"))
                .build();

        //When
        productRepository.save(milk);
        Long id = milk.getId();

        productRepository.deleteById(id);
        List<Product> products = productRepository.findAll();

        //Then
        assertEquals(0, products.size());
    }

    @Test
    public void testMeasureOperationTimeOfSpecificFetchType() {
        productRepository.deleteAll();
        groupRepository.deleteAll();
        //Given
        Group dairy = Group.builder()
                .groupName("Dairy")
                .products(new ArrayList<>())
                .build();

        //When
        for (int i=0; i<5; i++) {
            dairy.getProducts().add(Product.builder()
                    .name("MILK")
                    .productDescription("test")
                    .price(new BigDecimal("3.50"))
                    .group(dairy)
                    .build());
        }

        groupRepository.save(dairy);
        productRepository.saveAll(dairy.getProducts());

        //Then
        Long being = System.currentTimeMillis();
        for (int i=0; i<1000; i++) {
            Optional<Group> testGroup = groupRepository.findById(dairy.getId());
        }
        Long end = System.currentTimeMillis();

        System.out.println("BEGIN: " + being + "\nEND: " + end);
        System.out.println("Time taken for getGroup from database = " + (end-being) + " miliseconds");
        System.out.println("\nTime taken for getGroup from database = " + (end-being)/1000 + " seconds\n");
    }
}