package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.GroupDto;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.exception.ProductNotFoundException;
import com.kodilla.ecommercee.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupMapper {

    private final ProductMapper productMapper;

    public Group mapToGroup(final GroupDto groupDto) throws ProductNotFoundException{
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
