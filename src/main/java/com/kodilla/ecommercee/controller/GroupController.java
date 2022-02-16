package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.GroupDto;
import com.kodilla.ecommercee.exception.groupException.GroupExistInRepositoryException;
import com.kodilla.ecommercee.exception.groupException.GroupNameIsEmptyStringException;
import com.kodilla.ecommercee.exception.groupException.GroupNotFoundException;
import com.kodilla.ecommercee.mapper.GroupMapper;
import com.kodilla.ecommercee.service.DbServiceGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<Void> createGroup(@PathVariable String groupName) throws Exception {
        dbServiceGroup.createGroup(groupName);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupDto> updateGroup(@RequestBody @Valid GroupDto groupDto) throws Exception {
        return ResponseEntity.ok(GroupMapper.mapToGroupDto(dbServiceGroup.updateGroup(GroupMapper.mapToGroup(groupDto))));
    }
}
