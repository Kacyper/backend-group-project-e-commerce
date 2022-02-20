package com.kodilla.ecommerce.mapper;

import com.kodilla.ecommerce.domain.Group;
import com.kodilla.ecommerce.domain.GroupDto;
import com.kodilla.ecommerce.exception.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GroupMapper {

    private final ProductMapper productMapper;

    public Group mapToGroup(final GroupDto groupDto) throws ProductNotFoundException {
        return Group.builder()
                .id(groupDto.getId())
                .groupName(groupDto.getGroupName())
                .products(productMapper.mapToProductsFromIdProducts(groupDto.getProductIds()))
                .build();
    }

    public GroupDto mapToGroupDto(final Group group) {
        return GroupDto.builder()
                .id(group.getId())
                .groupName(group.getGroupName())
                .productIds(productMapper.mapToProductsIdsFromProducts(group.getProducts()))
                .build();
    }

    public List<GroupDto> mapToGroupDtoList(final List<Group> groups) {
        List<GroupDto> list = new ArrayList<>();
        for (Group group : groups) {
            GroupDto groupDto = mapToGroupDto(group);
            list.add(groupDto);
        }
        return list;
    }
}
