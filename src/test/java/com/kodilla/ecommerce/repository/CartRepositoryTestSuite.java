package com.kodilla.ecommerce.repository;

import com.kodilla.ecommerce.domain.Cart;
import com.kodilla.ecommerce.domain.Product;
import com.kodilla.ecommerce.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartRepositoryTestSuite {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

   @Test
    public void testSaveCart(){
        //Given
        Cart cart = Cart.builder()
                .products(new ArrayList<>())
                .build();

        //When
        cartRepository.save(cart);
        Long id = cart.getId();
        List <Cart> cartsList = cartRepository.findAll();

        //Then
        assertEquals(1, cartsList.size());

        //CleanUp
       try {
           cartRepository.deleteById(id);
       } catch (Exception e){
           System.out.println("nie ma takiego koszyka");
       }
    }

    @Test
    public void testCartsHaveProducts(){
        //Given
        Product soap1 = Product.builder()
                .name("soap")
                .price(new BigDecimal(5))
                .productDescription("vegan soap")
                .available(true)
                .group(null)
                .build();

        Product shampoo1 = Product.builder()
                .name("shampoo")
                .price(new BigDecimal(19.99))
                .productDescription("SLS free shampoo")
                .available(true)
                .group(null)
                .build();

        Cart cart1 = Cart.builder()
                .products(new ArrayList<>())
                .build();

        //When
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

        //Then
        assertEquals(2, products.size());
        assertEquals(1, carts.size());
        assertEquals(2, cart1.getProducts().size());

        assertTrue(cart1.getProducts().contains(shampoo1));
        assertTrue(cart1.getProducts().contains(soap1));

        //CleanUp
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
    public void testUserHasCart(){
        //Given
         Cart myCart = Cart.builder()
                 .products(new ArrayList<>())
                 .build();

         User user = User.builder()
                 .username("asd")
                 .fullName("whatever")
                 .password("1234")
                 .createDate(LocalDateTime.now())
                 .enabled(true)
                 .build();

         //When
         user.setCart(myCart);

         userRepository.save(user);
         Long userId = user.getId();

         cartRepository.save(myCart);

         //Then
         assertEquals(1, userRepository.findAll().size());
         assertEquals(1, cartRepository.findAll().size());

         assertEquals(myCart, user.getCart());

         //CleanUp
         try {
             userRepository.deleteById(userId);
         } catch (Exception e){
             System.out.println("nie ma takiego uzytkownika");
         }
     }
}
