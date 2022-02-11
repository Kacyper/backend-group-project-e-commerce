package com.kodilla.ecommercee;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("MySQL-Test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class CartRepositoryTestSuite {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

   @Test
    public void CartEntityTest(){
        //given
        Cart cart = Cart.builder()
                .cartName("MyCart")
                .products(new ArrayList<>())
                .build();

        //when
        cartRepository.save(cart);
        Long id = cart.getId();

        List <Cart> cartsList = cartRepository.findAll();

        //then
        assertEquals(1, cartsList.size());

        //cleanup
       try {
           cartRepository.deleteById(id);
       } catch (Exception e){
           System.out.println("nie ma takiego koszyka");
       }
    }

    @Test
    public void cartsHaveProductsTest(){
        //given
        Product soap1 = Product.builder()
                .name("soap")
                .price(new BigDecimal(5))
                .productDescription("vegan soap")
                .group(null)
                .carts(new ArrayList<>())
                .build();

        Product shampoo1 = Product.builder()
                .name("shampoo")
                .price(new BigDecimal(19.99))
                .productDescription("SLS free shampoo")
                .group(null)
                .carts(new ArrayList<>())
                .build();

        Cart cart1 = Cart.builder()
                .cartName("MyCart")
                .products(new ArrayList<>())
                .build();

        //when
        cart1.getProducts().add(soap1);
        cart1.getProducts().add(shampoo1);

        soap1.getCarts().add(cart1);
        shampoo1.getCarts().add(cart1);

        cartRepository.save(cart1);
        Long cart1Id = cart1.getId();
        productRepository.save(shampoo1);
        Long shampoo1Id = shampoo1.getId();
        productRepository.save(soap1);
        Long soap1Id = soap1.getId();

        List<Cart> carts = cartRepository.findAll();
        List<Product> products = productRepository.findAll();

        //then
        assertEquals(2, products.size());
        assertEquals(1, carts.size());
        assertEquals(2, cart1.getProducts().size());
        assertEquals(1, shampoo1.getCarts().size());
        assertEquals(1, soap1.getCarts().size());

        assertTrue(cart1.getProducts().contains(shampoo1));
        assertTrue(cart1.getProducts().contains(soap1));
        assertTrue(shampoo1.getCarts().contains(cart1));
        assertTrue(soap1.getCarts().contains(cart1));

        //cleanUp
        try{
        cartRepository.deleteById(cart1Id);
        productRepository.deleteById(shampoo1Id);
        productRepository.deleteById(soap1Id);
        }
        catch (Exception e){
            System.out.println("nie ma takich rzeczy");
        }
    }

    @Test
    public void deleteCartTest() {
        //given
        Cart cartA = Cart.builder()
                .cartName("MyCart1")
                .products(new ArrayList<>())
                .build();
        Cart cartB = Cart.builder()
                .cartName("MyCart2")
                .products(new ArrayList<>())
                .build();
        Cart cartC = Cart.builder()
                .cartName("MyCart3")
                .products(new ArrayList<>())
                .build();

        //when
        cartRepository.save(cartA);
        cartRepository.save(cartB);
        cartRepository.save(cartC);

        Long cartAId = cartA.getId();
        Long cartBId = cartB.getId();
        Long cartCId = cartC.getId();
        int cartsBeforeDeleting = cartRepository.findAll().size();

        try{
        cartRepository.deleteById(cartAId);
        }catch (Exception e){
            System.out.println("nie ma takiego koszyka");
        }

        //then
        assertEquals(3, cartsBeforeDeleting);
        assertEquals(2L, cartRepository.findAll().size());

        //cleanup
        try {
            cartRepository.deleteById(cartBId);
            cartRepository.deleteById(cartCId);
        } catch (Exception e){
            System.out.println("nie ma takich koszyków");
        }
    }



    @Transactional
    @Test
    public void deleteCartNotProductsTest(){
        //given
        Product soapA = Product.builder()
                .name("soap")
                .price(new BigDecimal(5))
                .productDescription("vegan soap")
                .group(null)
                .carts(new ArrayList<>())
                .build();

        Product shampooA = Product.builder()
                .name("shampoo")
                .price(new BigDecimal(19.99))
                .productDescription("SLS free shampoo")
                .group(null)
                .carts(new ArrayList<>())
                .build();

        Cart cart11 = Cart.builder()
                .cartName("MyCart")
                .products(new ArrayList<>())
                .build();

        //when
        cart11.getProducts().add(soapA);
        cart11.getProducts().add(shampooA);

        soapA.getCarts().add(cart11);
        shampooA.getCarts().add(cart11);

        cartRepository.save(cart11);
        productRepository.save(shampooA);
        productRepository.save(soapA);

        Long cart1Id = cart11.getId();
        Long shampooId = shampooA.getId();
        Long soapId = soapA.getId();

        int cartsBeforeDeleting = cartRepository.findAll().size();
        int productsBeforeDeleting = productRepository.findAll().size();

//        try {
//            cartRepository.deleteById(cart1Id);
//        } catch (Exception e){
//            System.out.println("nie ma takiego koszyka");
//        }
//
//        //then
//        assertEquals(1, cartsBeforeDeleting);
//        assertEquals(0, cartRepository.findAll().size());
//
//        assertEquals(2, productsBeforeDeleting);
//        assertEquals(2, productRepository.findAll().size());

        //cleanup
//        try {
//            productRepository.deleteById(shampooId);
//            productRepository.deleteById(soapId);
//        } catch (Exception e){
//            System.out.println("nie ma takich koszyków");
//        }
    }

    @Test
    public void deleteProductNotCartTest(){
        //given
        Product soap12 = Product.builder()
                .name("soap")
                .price(new BigDecimal(5))
                .productDescription("vegan soap")
                .group(null)
                .carts(new ArrayList<>())
                .build();

        Product shampoo12 = Product.builder()
                .name("shampoo")
                .price(new BigDecimal(19.99))
                .productDescription("SLS free shampoo")
                .group(null)
                .carts(new ArrayList<>())
                .build();

        Cart cart12 = Cart.builder()
                .cartName("MyCart")
                .products(new ArrayList<>())
                .build();

        //when
        cart12.getProducts().add(soap12);
        cart12.getProducts().add(shampoo12);

        soap12.getCarts().add(cart12);
        shampoo12.getCarts().add(cart12);

        cartRepository.save(cart12);
        productRepository.save(shampoo12);
        productRepository.save(soap12);

        int cartsBeforeDeleting = cartRepository.findAll().size();
        int productsBeforeDeleting = productRepository.findAll().size();
        int howManyProductsInCartBeforeDeleting = cart12.getProducts().size();

        Long cart12Id = cart12.getId();
        Long shampoo12Id = shampoo12.getId();
        Long soap12Id = soap12.getId();

        try {
            cart12.getProducts().remove(shampoo12);
            productRepository.deleteById(shampoo12Id);
        } catch (Exception e){
            System.out.println("nie ma takiego produktu");
        }

        //then
        assertEquals(1, cartsBeforeDeleting);
        assertEquals(1, cartRepository.findAll().size());

        assertEquals(2, productsBeforeDeleting);
        assertEquals(1, productRepository.findAll().size());

        assertEquals(2, howManyProductsInCartBeforeDeleting);
        assertEquals(1, cart12.getProducts().size());
        System.out.println(cart12.getProducts());

        //cleanup
        try {
            productRepository.deleteById(soap12Id);
            cartRepository.deleteById(cart12Id);
        } catch (Exception e){
            System.out.println("nie ma takich rzeczy");
        }
    }

    @Test
    public void cartHasOrder(){
        //given
        Cart cart13 = Cart.builder()
                .cartName("MyCart")
                .products(new ArrayList<>())
                .build();

        Order order13 = Order.builder()
                .orderDate(LocalDate.now())
                .shippingPrice(new BigDecimal(113))
                .isSent(true)
                .isPaid(true)
                .build();
        //when
        cart13.setOrder(order13);
        order13.setCart(cart13);

        cartRepository.save(cart13);
        orderRepository.save(order13);

        int carts = cartRepository.findAll().size();
        int orders = orderRepository.findAll().size();
        Long cart13Id = cart13.getId();
        Long order13Id = order13.getId();

        //then
        assertEquals(1, carts);
        assertEquals(1, orders);

        //cleanUp
        try {
            cartRepository.deleteById(cart13Id);
            orderRepository.deleteById(order13Id);
        } catch (Exception e){
            System.out.println("nie znaleziono");
        }
    }

    @Test
    public void cleanUpTest(){
        productRepository.deleteAll();
        cartRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    public void UpdateCartAndProduct(){
       //given
        Product soap14 = Product.builder()
                .name("soap")
                .price(new BigDecimal(5))
                .productDescription("vegan soap")
                .group(null)
                .carts(new ArrayList<>())
                .build();

        Product shampoo14 = Product.builder()
                .name("shampoo")
                .price(new BigDecimal(19.99))
                .productDescription("SLS free shampoo")
                .group(null)
                .carts(new ArrayList<>())
                .build();

        Cart cart14 = Cart.builder()
                .cartName("MyCart")
                .products(new ArrayList<>())
                .build();

        productRepository.save(shampoo14);
        productRepository.save(soap14);
        cartRepository.save(cart14);

        cart14.getProducts().add(shampoo14);
        cartRepository.save(cart14);

        Long shampId = shampoo14.getId();

        productRepository.deleteById(shampId);

    }

    @Test
    public void testTest(){
       Optional<Cart> cart12 = cartRepository.findById(55L);
       System.out.println("cokolwiek");
    }

}
