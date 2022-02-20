package com.kodilla.ecommerce.controller;

import com.kodilla.ecommerce.domain.GroupDto;
import com.kodilla.ecommerce.exception.GroupExistInRepositoryException;
import com.kodilla.ecommerce.exception.GroupNameIsEmptyException;
import com.kodilla.ecommerce.exception.GroupNotFoundException;
import com.kodilla.ecommerce.exception.ProductNotFoundException;
import com.kodilla.ecommerce.mapper.GroupMapper;
import com.kodilla.ecommerce.service.DbServiceGroup;
import lombok.RequiredArgsConstructor;
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