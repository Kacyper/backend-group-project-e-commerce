package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.exception.groupException.GroupExistInRepositoryException;
import com.kodilla.ecommercee.exception.groupException.GroupNameIsEmptyStringException;
import com.kodilla.ecommercee.exception.groupException.GroupNotFoundException;
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

    public Group createGroup(final String groupName) throws Exception {
        if (groupRepository.existsGroupByGroupName(groupName)) {
            throw new GroupExistInRepositoryException();

        } else {
            Group group = Group.builder()
                    .groupName(groupName)
                    .build();
            return groupRepository.save(group);
        }
    }

    public Group updateGroup(final Group group) throws Exception {
        Group updatedGroup = groupRepository.findById(group.getId()).orElseThrow(GroupNotFoundException::new);

        if (group.getGroupName().isEmpty()) {
            throw new GroupNameIsEmptyStringException();
        }

        updatedGroup.setGroupName(group.getGroupName());
        saveGroup(updatedGroup);
        return updatedGroup;
    }

    private Group saveGroup(final Group group) throws Exception {
        if(!groupRepository.existsGroupByGroupName(group.getGroupName())) {

            if(group.getGroupName().isEmpty()) {
                throw new GroupNameIsEmptyStringException();
            }

            return groupRepository.save(group);

        } else throw new GroupExistInRepositoryException();
    }
}