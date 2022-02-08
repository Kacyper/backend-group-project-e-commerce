package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Group;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GroupRepositoryTest {
    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void testSaveGroup(){
        //given
        Group newGroup = Group.builder()
                .groupName("new group")
                .products(new ArrayList<>())
                .build();
        Group saved = groupRepository.save(newGroup);
        //when
        Group group = groupRepository.findById(saved.getId()).orElseThrow();
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
        Group saved1 = groupRepository.save(newGroup1);
        Group saved2 = groupRepository.save(newGroup2);
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
        Group group = groupRepository.findById(saved1.getId()).orElseThrow();
        //then
        assertThat(group.getGroupName()).isEqualTo("new group 1");
        assertThat(group.getProducts().size()).isEqualTo(0);
        //cleanUp
        groupRepository.deleteAll();
    }

    @Test
    public void testDeleteGroup(){
        //given
        Group newGroup1 = Group.builder()
                .groupName("new group 1")
                .products(new ArrayList<>())
                .build();
        Group saved1 = groupRepository.save(newGroup1);
        //when
        groupRepository.deleteById(saved1.getId());
        List<Group> all = groupRepository.findAll();
        //then
        assertThat(all.size()).isEqualTo(0);
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
        Group saved1 = groupRepository.save(newGroup1);
        Group group = groupRepository.findById(saved1.getId()).orElseThrow();
        //when
        group.setGroupName("My group");
        groupRepository.save(group);
        Group updated = groupRepository.findById(saved1.getId()).orElseThrow();
        //then
        assertThat(updated.getGroupName()).isEqualTo("My group");
        //cleanUp
        groupRepository.deleteAll();
    }
}