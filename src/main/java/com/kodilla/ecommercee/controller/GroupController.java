package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.GroupDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/groups")
public class GroupController {

    @GetMapping
    public List<GroupDto> getGroups() {
        return new ArrayList<>();
    }

    @GetMapping("/{id}")
    public GroupDto getGroup(@PathVariable Long id) throws Exception {
        return new GroupDto();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createGroup(@RequestBody GroupDto groupDto) {

    }

    @PutMapping
    public GroupDto updateGroup(@RequestBody GroupDto groupDto) {
        return new GroupDto();
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id) {

    }
}
