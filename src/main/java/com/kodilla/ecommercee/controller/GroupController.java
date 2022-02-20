package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.GroupDto;
import com.kodilla.ecommercee.exception.GroupExistInRepositoryException;
import com.kodilla.ecommercee.exception.GroupNameIsEmptyException;
import com.kodilla.ecommercee.exception.GroupNotFoundException;
import com.kodilla.ecommercee.exception.ProductNotFoundException;
import com.kodilla.ecommercee.mapper.GroupMapper;
import com.kodilla.ecommercee.service.DbServiceGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/groups")
@RequiredArgsConstructor
public class GroupController {

    private final DbServiceGroup dbServiceGroup;
    private final GroupMapper groupMapper;

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping
    public ResponseEntity<List<GroupDto>> getGroups() {
        return ResponseEntity.ok(groupMapper.mapToGroupDtoList(dbServiceGroup.getGroups()));
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> getGroup(@PathVariable Long id) throws GroupNotFoundException {
        return ResponseEntity.ok(groupMapper.mapToGroupDto(dbServiceGroup.getGroup(id)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createGroup(@RequestBody GroupDto groupDto) {
        Group group = GroupMapper.mapToGroup(groupDto);
        dbServiceGroup.saveGroup(group);
        return ResponseEntity.ok().build();
    @PostMapping
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto groupDto) throws GroupExistInRepositoryException, GroupNameIsEmptyException, ProductNotFoundException {
        return ResponseEntity.ok(groupMapper.mapToGroupDto(dbServiceGroup.createGroup(groupMapper.mapToGroup(groupDto))));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GroupDto> updateGroup(@PathVariable Long id, @RequestParam String groupName) throws GroupNotFoundException, GroupExistInRepositoryException, GroupNameIsEmptyException {
        return ResponseEntity.ok(groupMapper.mapToGroupDto(dbServiceGroup.updateGroup(id, groupName)));
    }
}