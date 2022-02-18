package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.GroupDto;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupMapper {
    public static Group mapToGroup(final GroupDto groupDto) {
        return Group.builder()
                .id(groupDto.getId())
                .groupName(groupDto.getGroupName())
                .products(groupDto.getProducts())
                .build();
    }

    public static GroupDto mapToGroupDto(final Group group) {
        return GroupDto.builder()
                .id(group.getId())
                .groupName(group.getGroupName())
                .products(group.getProducts())
                .build();
    }

    public static List<GroupDto> mapToGroupDtoList(final List<Group> groups) {
        return groups.stream()
                .map(GroupMapper::mapToGroupDto)
                .collect(Collectors.toList());
    }
}
