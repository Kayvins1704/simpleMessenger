package com.example.simpleMessenger.service;

import com.example.simpleMessenger.dtos.ChatHistory;
import com.example.simpleMessenger.dtos.UnreadMessages;
import com.example.simpleMessenger.entity.Message;
import com.example.simpleMessenger.exceptions.UserDoesNotExistException;
import com.example.simpleMessenger.repository.MessageRepository;
import com.example.simpleMessenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public void postMessage(String fromUser, String toUser, String text) {
        validations(fromUser, toUser);
        Message message = Message.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .text(text)
                .isRead(Boolean.FALSE)
                .build();
        messageRepository.postMessage(message);
        log.info("Message from {} to {} has been successfully sent", fromUser, toUser);
    }

    public List<UnreadMessages> getUnreadMessages(String username) {
        validations(username, null);
        return messageRepository.getUnreadMessages(username);
    }

    public List<ChatHistory> getChatHistory(String fromUser, String toUser) {
        validations(fromUser, toUser);
        return messageRepository.getChatHistory(fromUser, toUser);
    }

    private void validations(String fromUser, String toUser) {
        if(userRepository.findByUsername(fromUser).isEmpty()) throw new UserDoesNotExistException(fromUser);
        if(toUser != null && userRepository.findByUsername(toUser).isEmpty()) throw new UserDoesNotExistException(toUser);
    }
}
