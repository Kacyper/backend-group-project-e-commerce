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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupRepositoryTestSuite {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveGroup(){
        //given
        Group newGroup = Group.builder()
                .groupName("new group")
                .products(new ArrayList<>())
                .build();
        Group saved = groupRepository.save(newGroup);
        //when
        Group group = groupRepository.findById(saved.getId())
                .get();
        //then
        assertThat(group.getGroupName()).isEqualTo("new group");
        assertThat(group.getProducts().size()).isEqualTo(0);
        //cleanUp
        groupRepository.deleteAll();
    }

    @Test
    public void testGetAllGroups(){
        //given
        Group newGroup1 = Group.builder()
                .groupName("new group 1")
                .products(new ArrayList<>())
                .build();
        Group newGroup2 = Group.builder()
                .groupName("new group 2")
                .products(new ArrayList<>())
                .build();
        Group save = groupRepository.save(newGroup1);
        Group save1 = groupRepository.save(newGroup2);
        //when
        List<Group> all = groupRepository.findAll();
        //then
        assertThat(all.size()).isEqualTo(2);
        //cleanUp
        groupRepository.deleteAll();
    }

    @Test
    public void testGetGroupById(){
        //given
        Group newGroup1 = Group.builder()
                .groupName("new group 1")
                .products(new ArrayList<>())
                .build();
        Group saved1 = groupRepository.save(newGroup1);
        //when
        Group group = groupRepository.findById(saved1.getId()).get();
        //then
        assertThat(group.getGroupName()).isEqualTo("new group 1");
        assertThat(group.getProducts().size()).isEqualTo(0);
        //cleanUp
        groupRepository.deleteAll();
    }

    @Test
    public void testUpdateGroup(){
        //given
        Group newGroup1 = Group.builder()
                .groupName("new group 1")
                .products(new ArrayList<>())
                .build();
        groupRepository.save(newGroup1);
        Group group = groupRepository.findAll().get(0);
        //when
        group.setGroupName("My group");
        groupRepository.save(group);
        Group updated = groupRepository.findAll().get(0);
        //then
        assertThat(updated.getGroupName()).isEqualTo("My group");
        //cleanUp
        groupRepository.deleteAll();
    }

    @Test
    public void testAddProductToGroup(){
        //given
        Product product = Product.builder()
                .name("Produkt")
                .price(BigDecimal.TEN)
                .productDescription("Nowy")
                .build();
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        Group group = Group.builder()
                .groupName("new group 1")
                .products(products)
                .build();
        Group save = groupRepository.save(group);
        product.setGroup(group);
        productRepository.save(product);
        //when
        List<Group> all = groupRepository.findAll();
        //then
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getProducts().get(0).getName()).isEqualTo("Produkt");
        //cleanUp
        groupRepository.deleteById(save.getId());
    }
}