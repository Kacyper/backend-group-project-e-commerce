package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.GroupDto;
import com.kodilla.ecommercee.exception.GroupNotFoundException;
import com.kodilla.ecommercee.mapper.GroupMapper;
import com.kodilla.ecommercee.service.DbServiceGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/groups")
public class GroupController {

    private final DbServiceGroup dbServiceGroup;

    @GetMapping
    public List<GroupDto> getGroups() {
        return GroupMapper.mapToGroupDtoList(dbServiceGroup.getGroups());
    }

    @GetMapping("/{id}")
    public GroupDto getGroup(@PathVariable Long id) throws GroupNotFoundException {
        return GroupMapper.mapToGroupDto(dbServiceGroup.getGroup(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createGroup(@RequestBody GroupDto groupDto) {
        Group group = GroupMapper.mapToGroup(groupDto);
        dbServiceGroup.saveGroup(group);
    }

    @PutMapping("/{id}")
    public GroupDto updateGroup(@PathVariable Long id, @RequestBody GroupDto groupDto) throws GroupNotFoundException {
        return GroupMapper.mapToGroupDto(dbServiceGroup.updateGroup(id, GroupMapper.mapToGroup(groupDto)));
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id) {
        dbServiceGroup.deleteGroup(id);
    }
}
