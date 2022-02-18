package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.GroupDto;
import com.kodilla.ecommercee.exception.groupException.GroupNotFoundException;
import com.kodilla.ecommercee.mapper.GroupMapper;
import com.kodilla.ecommercee.service.DbServiceGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/groups")
@RequiredArgsConstructor
public class GroupController {

    private final DbServiceGroup dbServiceGroup;

    @GetMapping
    public ResponseEntity<List<GroupDto>> getGroups() {
        return ResponseEntity.ok(GroupMapper.mapToGroupDtoList(dbServiceGroup.getGroups()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> getGroup(@PathVariable Long id) throws GroupNotFoundException {
        return ResponseEntity.ok(GroupMapper.mapToGroupDto(dbServiceGroup.getGroup(id)));
    }

    @PostMapping("/{groupName}")
    public ResponseEntity<GroupDto> createGroup(@PathVariable String groupName) throws Exception {
        return ResponseEntity.ok((GroupMapper.mapToGroupDto(dbServiceGroup.createGroup(groupName))));
    }

    @PutMapping("/{id}/{groupName}")
    public ResponseEntity<GroupDto> updateGroup(@PathVariable Long id, @PathVariable String groupName) throws Exception {
        return ResponseEntity.ok(GroupMapper.mapToGroupDto(dbServiceGroup.updateGroup(id, groupName)));
    }
}
