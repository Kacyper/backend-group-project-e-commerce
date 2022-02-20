package com.kodilla.ecommerce.repository;

import com.kodilla.ecommerce.domain.Group;
import com.kodilla.ecommerce.domain.Product;
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
                .available(true)
                .build();

        //When
        productRepository.save(milk);
        Long id = milk.getId();

        //Then
        List<Product> products = productRepository.findAll();

        assertEquals(1, products.size());

        //CleanUp
        productRepository.deleteById(id);
    }

    @Test
    public void testSaveAllProducts() {
        //Given
        Product milk = Product.builder()
                .name("Milk")
                .productDescription("test description for product")
                .price(new BigDecimal("3.50"))
                .group(null)
                .available(true)
                .build();

        Product butter = Product.builder()
                .name("Butter")
                .productDescription("test description for product")
                .price(new BigDecimal("5.50"))
                .group(null)
                .available(true)
                .build();

        //When
        List<Product> products = new ArrayList<>();
        products.add(milk);
        products.add(butter);

        productRepository.saveAll(products);

        //Then
        assertEquals(2, productRepository.findAll().size());

        //CleanUp
        for (Product product : products) {
            productRepository.deleteById(product.getId());
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
                .available(true)
                .build();

        Product butter = Product.builder()
                .name("Butter")
                .productDescription("test description for product")
                .price(new BigDecimal("5.50"))
                .group(null)
                .available(true)
                .build();

        productRepository.save(milk);
        Long milkId = milk.getId();
        productRepository.save(butter);
        Long butterId = butter.getId();

        //When
        Optional<Product> optionalMilk = productRepository.findById(milkId);
        Optional<Product> optionalButter = productRepository.findById(butterId);

        //Then
        assertEquals(optionalMilk.get().getId(), milkId);
        assertEquals(optionalMilk.get().getName(), milk.getName());
        assertEquals(optionalButter.get().getId(), butterId);
        assertEquals(optionalButter.get().getName(), butter.getName());

        //CleanUp
        productRepository.deleteById(milkId);
        productRepository.deleteById(butterId);
    }

    @Test
    public void testExistsById() {
        //Given
        Product milk = Product.builder()
                .name("Milk")
                .productDescription("test description for product")
                .price(new BigDecimal("3.50"))
                .group(null)
                .available(true)
                .build();

        productRepository.save(milk);
        Long id = milk.getId();

        //When
        boolean isMilkPresent = productRepository.existsById(id);

        //Then
        assertTrue(isMilkPresent);

        //CleanUp
        productRepository.deleteById(id);
    }

    @Test
    public void testCount() {
        //Given
        Product milk = Product.builder()
                .name("Milk")
                .productDescription("test description for product")
                .price(new BigDecimal("3.50"))
                .group(null)
                .available(true)
                .build();

        productRepository.save(milk);
        Long id = milk.getId();

        //When
        int count = (int) productRepository.count();

        //Then
        assertEquals(1, count);

        //CleanUp
        productRepository.deleteById(id);
    }

    @Test
    public void testSoftDeleteProduct() {
        //Given
        Product milk = Product.builder()
                .name("Milk")
                .productDescription("test description for product")
                .price(new BigDecimal("3.50"))
                .group(null)
                .available(true)
                .build();

        productRepository.save(milk);
        Long id = milk.getId();

        //When
        milk.setAvailable(false);
        productRepository.save(milk);

        //Then
        List<Product> products = productRepository.retrieveAvailableProducts();

        assertEquals(0, products.size());

        //CleanUp
        productRepository.deleteById(id);
    }

    @Test
    public void testAddProductToGroup() {
        //Given
        Product milk = Product.builder()
                .name("Milk")
                .productDescription("test description for product")
                .price(new BigDecimal("3.50"))
                .group(null)
                .available(true)
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

        groupRepository.save(dairy);

        //Then
        List<Product> dairyProducts = groupRepository.findById(dairyId).get().getProducts();
        Group milkGroup = productRepository.findById(milkId).get().getGroup();

        assertEquals(1, dairyProducts.size());
        assertEquals(groupRepository.findById(dairyId).get().getGroupName(), milkGroup.getGroupName());

        //CleanUp
        productRepository.deleteById(milkId);
        groupRepository.deleteById(dairyId);
    }

    @Test
    public void testRetrieveAvailableProducts() {
        //Given
        Product milk = Product.builder()
                .name("Milk")
                .productDescription("test description for product")
                .price(new BigDecimal("3.50"))
                .group(null)
                .available(true)
                .build();

        Product butter = Product.builder()
                .name("Butter")
                .productDescription("test description for product")
                .price(new BigDecimal("5.50"))
                .group(null)
                .available(false)
                .build();

        productRepository.save(milk);
        Long milkId = milk.getId();
        productRepository.save(butter);
        Long butterId = butter.getId();

        //When
        List<Product> allProducts = productRepository.findAll();
        List<Product> onlyAvailableProducts = productRepository.retrieveAvailableProducts();

        //Then
        assertEquals(2, allProducts.size());
        assertEquals(1, onlyAvailableProducts.size());

        //CleanUp
        productRepository.deleteById(milkId);
        productRepository.deleteById(butterId);
    }
}