package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTestSuite {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    GroupRepository groupRepository;

    @Test
    public void testSaveProduct() {
        //Given
        Product milk = Product.builder()
                .name("Milk")
                .productDescription("test description for product")
                .price(new BigDecimal("3.50"))
                .group(null)
                .isAvailable(true)
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
                .group(null)
                .isAvailable(true)
                .build();

        Product butter = Product.builder()
                .name("Butter")
                .productDescription("test description for product")
                .price(new BigDecimal("5.50"))
                .group(null)
                .isAvailable(true)
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
                .group(null)
                .isAvailable(true)
                .build();

        Product butter = Product.builder()
                .name("Butter")
                .productDescription("test description for product")
                .price(new BigDecimal("5.50"))
                .group(null)
                .isAvailable(true)
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
                .group(null)
                .isAvailable(true)
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
                .group(null)
                .isAvailable(true)
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
                .group(null)
                .isAvailable(true)
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
    public void testAddProductToGroup() {
        //Given
        Product milk = Product.builder()
                .name("Milk")
                .productDescription("test description for product")
                .price(new BigDecimal("3.50"))
                .group(null)
                .isAvailable(true)
                .build();

        Group dairy = Group.builder()
                .groupName("Dairy")
                .products(new ArrayList<>())
                .build();

        productRepository.save(milk);
        Long milkId = milk.getId();
        groupRepository.save(dairy);
        Long dairyId = dairy.getId();

        //When
        milk.setGroup(dairy);
        dairy.getProducts().add(milk);

        productRepository.save(milk);
        groupRepository.save(dairy);

        List<Product> products = productRepository.findAll();
        List<Group> groups = groupRepository.findAll();

        List<Product> dairyProducts = dairy.getProducts();

        //Then
        assertEquals(1, products.size());
        assertEquals(1, groups.size());
        assertTrue(dairyProducts.contains(milk));

        //CleanUp
        try {
            productRepository.deleteById(milkId);
            groupRepository.deleteById(dairyId);
        } catch (Exception e) {
            System.out.println("SOMETHING WENT WRONG HERE: " + e.getMessage());
        }

    }

    @Test
    public void testRetrieveAvailableProducts() {
        //Given
        Product milk = Product.builder()
                .name("Milk")
                .productDescription("test description for product")
                .price(new BigDecimal("3.50"))
                .group(null)
                .isAvailable(true)
                .build();

        Product butter = Product.builder()
                .name("Butter")
                .productDescription("test description for product")
                .price(new BigDecimal("5.50"))
                .group(null)
                .isAvailable(false)
                .build();

        //When
        productRepository.save(milk);
        Long milkId = milk.getId();
        productRepository.save(butter);
        Long butterId = butter.getId();

        List<Product> allProducts = productRepository.findAll();
        List<Product> onlyAvailableProducts = productRepository.retrieveAvailableProducts();

        //Then
        assertEquals(2, allProducts.size());
        assertEquals(1, onlyAvailableProducts.size());

        //CleanUp
        try {
            productRepository.deleteById(milkId);
            productRepository.deleteById(butterId);
        } catch (Exception e) {
            System.out.println("SOMETHING WENT WRONG HERE: " + e.getMessage());
        }
    }
}