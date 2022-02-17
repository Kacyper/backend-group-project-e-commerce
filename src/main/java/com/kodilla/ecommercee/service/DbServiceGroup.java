package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.exception.groupException.GroupExistInRepositoryException;
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
            saveGroup(group);

            return group;
        }
    }

    public Group updateGroup(final Long id, final String groupName) throws Exception {
        Group updatedGroup = groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);

        if(!groupRepository.existsGroupByGroupName(groupName)) {
            updatedGroup.setGroupName(groupName);
            saveGroup(updatedGroup);
            return updatedGroup;

        } else throw new GroupExistInRepositoryException();
    }

    private Group saveGroup(final Group group) {
        return groupRepository.save(group);
    }
}