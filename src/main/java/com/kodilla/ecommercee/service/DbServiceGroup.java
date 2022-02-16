package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.exception.GroupNameIsEmptyStringException;
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

    public Group updateGroup(final Group group) throws GroupNotFoundException, GroupNameIsEmptyStringException {
        Group updatedGroup = groupRepository.findById(group.getId()).orElseThrow(GroupNotFoundException::new);

        if (group.getGroupName().isEmpty()) {
            throw new GroupNameIsEmptyStringException();
        }

        updatedGroup.setGroupName(group.getGroupName());
        saveGroup(updatedGroup);
        return updatedGroup;
    }
}