package com.example.simpleMessenger.service;

import com.example.simpleMessenger.dtos.ChatHistory;
import com.example.simpleMessenger.dtos.GroupDto;
import com.example.simpleMessenger.dtos.UserDto;
import com.example.simpleMessenger.entity.Group;
import com.example.simpleMessenger.entity.GroupMessage;
import com.example.simpleMessenger.entity.Message;
import com.example.simpleMessenger.entity.User;
import com.example.simpleMessenger.exceptions.GroupDoesNotExistException;
import com.example.simpleMessenger.exceptions.UserDoesNotExistException;
import com.example.simpleMessenger.exceptions.UserIsNotPartOfTheGroupException;
import com.example.simpleMessenger.repository.GroupMessageRepository;
import com.example.simpleMessenger.repository.GroupRepository;
import com.example.simpleMessenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupMessageService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final GroupMessageRepository groupMessageRepository;

    public void postMessage(String fromUser, String groupName, String text) {
        validations(fromUser, groupName);
        GroupMessage message = GroupMessage.builder()
                .fromUser(fromUser)
                .text(text)
                .build();
        groupMessageRepository.postMessage(message, groupName);
        log.info("Message from {} to group {} has been successfully sent", fromUser, groupName);
    }

    public List<ChatHistory> getChatHistoryInGroup(String groupName) {
        validations(null, groupName);
        return groupMessageRepository.getChatHistory(groupName);
    }

    private void validations(String fromUser, String groupName) {
        if(fromUser != null && userRepository.findByUsername(fromUser).isEmpty()) throw new UserDoesNotExistException(fromUser);
        var group = groupRepository.findByGroupName(groupName);
        if(group.isEmpty()) throw new GroupDoesNotExistException();
        if(fromUser != null) {
            List<User> userList = group.get().getUserList();
            if(userList.stream().noneMatch(user -> user.getUsername().equals(fromUser))) throw new UserIsNotPartOfTheGroupException();
        }
    }
}
