package com.kodilla.ecommercee;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("MySQL-Test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class CartRepositoryTestSuite {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

   @Test
    public void cartEntityTest(){
        //given
        Cart cart = Cart.builder()
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
                .isAvailable(true)
                .group(null)
                .build();

        Product shampoo1 = Product.builder()
                .name("shampoo")
                .price(new BigDecimal(19.99))
                .productDescription("SLS free shampoo")
                .isAvailable(true)
                .group(null)
                .build();

        Cart cart1 = Cart.builder()
                .products(new ArrayList<>())
                .build();

        //when
        cart1.getProducts().add(soap1);
        cart1.getProducts().add(shampoo1);

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

        assertTrue(cart1.getProducts().contains(shampoo1));
        assertTrue(cart1.getProducts().contains(soap1));

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
    public void cleanUpTest(){

        userRepository.deleteAll();
        productRepository.deleteAll();
        cartRepository.deleteAll();
    }

    @Test
    public void updateCartAndProduct(){
       //given
        Product soap14 = Product.builder()
                .name("soap")
                .price(new BigDecimal(5))
                .productDescription("vegan soap")
                .group(null)
                .isAvailable(true)
                .build();

        Product shampoo14 = Product.builder()
                .name("shampoo")
                .price(new BigDecimal("19.99"))
                .productDescription("SLS free shampoo")
                .group(null)
                .isAvailable(true)
                .build();

        Cart cart14 = Cart.builder()
                .products(new ArrayList<>())
                .build();

        productRepository.save(shampoo14);
        productRepository.save(soap14);
        cartRepository.save(cart14);

        int cartsBeg = cartRepository.findAll().size();
        int prodBeg = productRepository.findAll().size();

        int cartProductBeginning = cart14.getProducts().size();

        //When
        cart14.getProducts().add(shampoo14);
        cart14.getProducts().add(soap14);
        cartRepository.save(cart14);

//        int cartProductAdded = cart14.getProducts().size();
//        Long shampooID = shampoo14.getId();
        cartRepository.delete(cart14);
        cart14.getProducts().remove(shampoo14);
        cartRepository.save(cart14);

//        cartRepository.(cart14);
//        int cartsEnd = cartRepository.findAll().size();
//        int prodEnd = productRepository.findAll().size();

        //then
//        assertEquals(1, cartsBeg);
//        assertEquals(2, prodBeg);
//
//        assertEquals(0, cartProductBeginning);
//        assertEquals(2, cartProductAdded);
//        assertEquals(1, cart14.getProducts().size());
//
//        assertEquals(1, cartsEnd);
//        assertEquals(2, prodEnd);

//        //cleanup
//        try{
//            productRepository.delete(shampoo14);
//            productRepository.delete(soap14);
//            cartRepository.delete(cart14);
//        } catch (Exception e){
//            System.out.println("nie ma takich rzeczy");
//        }
    }

     @Test
    public void userHasCart(){
        //Given
         Cart myCart = Cart.builder()
                 .products(new ArrayList<>())
                 .build();

         User user = User.builder()
                 .username("asd")
                 .email("whatever")
                 .password("1234")
                 .createDate(LocalDateTime.now())
                 .isActive(true)
                 .isEnabled(true)
                 .build();

         //when
         user.setCart(myCart);
         userRepository.save(user);
         cartRepository.save(myCart);

         //then
         assertEquals(1, userRepository.findAll().size());
         assertEquals(1, cartRepository.findAll().size());

         assertEquals(myCart, user.getCart());
     }
}
