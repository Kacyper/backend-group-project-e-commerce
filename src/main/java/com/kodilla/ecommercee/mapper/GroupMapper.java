package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.GroupDto;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.ProductDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupMapper {

    public static Group mapToGroup(final GroupDto groupDto) {
        System.out.println("start" + " size " + groupDto.getProducts().size());
        Group grup = Group.builder()
                .id(groupDto.getId())
                .groupName(groupDto.getGroupName())
                .products(Optional.of(groupDto.getProducts().stream()
                                .map(ProductMapper::mapToProduct)
                                .collect(Collectors.toList())).
                        orElse(new ArrayList<Product>()))
                .build();
        System.out.println("after" + " size " + groupDto.getProducts().size());
        return grup;
    }

    public static GroupDto mapToGroupDto(final Group group) {
        return GroupDto.builder()
                .id(group.getId())
                .groupName(group.getGroupName())
                .products(Optional.of(group.getProducts().stream()
                                .map(ProductMapper::mapToDto)
                                .collect(Collectors.toList())).
                        orElse(new ArrayList<ProductDto>()))
                .build();
    }

    public static List<GroupDto> mapToGroupDtoList(final List<Group> groups) {
        return groups.stream()
                .map(GroupMapper::mapToGroupDto)
                .collect(Collectors.toList());
    }
}
