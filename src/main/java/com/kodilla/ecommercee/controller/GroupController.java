package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.GroupDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/groups")
public class GroupController {

    @GetMapping(value = "getGroups")
    public List<GroupDto> getGroups() {
        return new ArrayList<>();
    }

    @GetMapping(value = "getGroup")
    public GroupDto getGroup(@RequestParam Long groupId) throws Exception {
        return new GroupDto();
    }

    @PostMapping(value = "createGroup")
    public void createGroup(@RequestBody GroupDto groupDto) {

    }

    @PutMapping(value = "updateGroup")
    public GroupDto updateGroup(@RequestBody GroupDto groupDto) {
        return new GroupDto();
    }

    @DeleteMapping(value = "deleteGroup")
    public void deleteGroup(@RequestParam Long groupId) {

    }
}
