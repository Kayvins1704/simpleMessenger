package com.example.simpleMessenger.service;

import com.example.simpleMessenger.dtos.GroupDto;
import com.example.simpleMessenger.dtos.request.GroupCreationRequest;
import com.example.simpleMessenger.entity.User;
import com.example.simpleMessenger.exceptions.GroupAlreadyExistsException;
import com.example.simpleMessenger.exceptions.GroupDoesNotExistException;
import com.example.simpleMessenger.exceptions.UsersDoNotExistException;
import com.example.simpleMessenger.repository.GroupRepository;
import com.example.simpleMessenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupDto fetchAllUsersInAGroup(String groupName) {
        var group = groupRepository.findByGroupName(groupName).orElseThrow(GroupDoesNotExistException::new);
        List<String> usernameList = group.getUserList().stream().map(User::getUsername).toList();
        return new GroupDto(group.getGroupName(), usernameList);
    }

    public void createGroup(GroupCreationRequest groupCreationRequest) {
        validate(groupCreationRequest);
        groupRepository.createGroup(groupCreationRequest);
    }

    private void validate(GroupCreationRequest groupCreationRequest) {
        if(groupRepository.findByGroupName(groupCreationRequest.getGroupName()).isPresent()) throw new GroupAlreadyExistsException();
        List<String> usernameList = groupCreationRequest.getUsernameList();
        if(usernameList.size() != userRepository.getCount(usernameList)) throw new UsersDoNotExistException();
    }
}
