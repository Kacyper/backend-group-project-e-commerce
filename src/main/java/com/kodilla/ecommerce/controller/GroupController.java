package com.kodilla.ecommerce.controller;

import com.kodilla.ecommerce.service.DbServiceGroup;
import com.kodilla.ecommerce.domain.GroupDto;
import com.kodilla.ecommerce.exception.*;
import com.kodilla.ecommerce.mapper.GroupMapper;
import com.kodilla.ecommerce.service.ModificationTokenService;
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
    private final ModificationTokenService modificationTokenService;

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
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto groupDto, @RequestParam String modificationToken) throws GroupExistInRepositoryException, GroupNameIsEmptyException, ProductNotFoundException {
        return ResponseEntity.ok(groupMapper.mapToGroupDto(dbServiceGroup.createGroup(groupMapper.mapToGroup(groupDto))));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GroupDto> updateGroup(@PathVariable Long id, @RequestParam String groupName, @RequestParam String modificationToken) throws GroupNotFoundException, GroupExistInRepositoryException, GroupNameIsEmptyException, ModificationTokenNotFoundException, ModificationTokenNotValidException {
        modificationTokenService.checkIfModificationTokenValid(modificationToken);
        return ResponseEntity.ok(groupMapper.mapToGroupDto(dbServiceGroup.updateGroup(id, groupName)));
    }
}