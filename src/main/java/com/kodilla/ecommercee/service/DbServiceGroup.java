package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.exception.GroupNotFoundException;
import com.kodilla.ecommercee.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DbServiceGroup {
    private final GroupRepository groupRepository;

    public List<Group> getGroups() {
        return groupRepository.findAll();
    }

    public Group getGroup(final Long id) throws GroupNotFoundException {
        return groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);
    }

    public Group saveGroup(final Group group) {
        return groupRepository.save(group);
    }

    public Group updateGroup(final Long id, final Group group) throws GroupNotFoundException {
        Group updatedGroup = groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);

        updatedGroup.setGroupName(group.getGroupName());
        updatedGroup.setProducts(group.getProducts());

        return updatedGroup;
    }

    public void deleteGroup(final Long id) throws GroupNotFoundException {

        Group group = groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);
        groupRepository.delete(group);
    }
}