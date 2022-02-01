package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.GroupDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/groups")
public class GroupController {

    //test data added to resultList for initial implementation & tests
    @GetMapping
    public List<GroupDto> getGroups() {
        GroupDto testGroupValue = GroupDto.builder()
                .id(1L)
                .groupName("Pierogi Ruskie")
                .price(10.90)
                .description("Smaczne, pyszne! Mniam, mniam!")
                .build();
        List<GroupDto> resultList = new ArrayList<>();
        resultList.add(testGroupValue);
        return resultList;
    }

    @GetMapping("/{id}")
    public GroupDto getGroup(@PathVariable Long id) throws Exception {
        return GroupDto.builder()
                .id(id)
                .groupName("Test_Name")
                .price(333.33)
                .description("Test_Description")
                .build();
    }

    //temporary void method switched to return GroupDo for initial implementation & tests
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public GroupDto createGroup(@RequestBody GroupDto groupDto) {
        return GroupDto.builder()
                .id(1L)
                .groupName(groupDto.getGroupName())
                .price(groupDto.getPrice())
                .description(groupDto.getDescription())
                .build();
    }

    @PutMapping("/{id}")
    public GroupDto updateGroup(@PathVariable Long id, @RequestBody GroupDto groupDto) {
        return GroupDto.builder()
                .id(id)
                .groupName(groupDto.getGroupName())
                .price(groupDto.getPrice())
                .description(groupDto.getDescription())
                .build();
    }

    //temporary void method switched to return Long for initial implementation & tests
    @DeleteMapping("/{id}")
    public Long deleteGroup(@PathVariable Long id) {
        return id;
    }
}
