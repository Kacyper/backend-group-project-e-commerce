package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.exception.GroupExistInRepositoryException;
import com.kodilla.ecommercee.exception.GroupNameIsEmptyException;
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

    public Group createGroup(final Group group) throws GroupExistInRepositoryException, GroupNameIsEmptyException {
        validateGroupName(group.getGroupName());
        return saveGroup(group);
    }

    public Group updateGroup(final Long id, final String groupName) throws GroupNotFoundException, GroupExistInRepositoryException, GroupNameIsEmptyException {
        Group updatedGroup = groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);
        validateGroupName(groupName);
        updatedGroup.setGroupName(groupName);
        saveGroup(updatedGroup);
        return updatedGroup;
    }

    public Group saveGroup(final Group group) {
        return groupRepository.save(group);
    }

    private void validateGroupName(String groupName) throws GroupExistInRepositoryException, GroupNameIsEmptyException {
        if (groupRepository.existsGroupByGroupName(groupName)) {
            throw new GroupExistInRepositoryException();
        }

        if (groupName.isEmpty()) {
            throw new GroupNameIsEmptyException();
        }
    }
}