package com.example.simpleMessenger.controller;

import com.example.simpleMessenger.constants.ApiResponseStatus;
import com.example.simpleMessenger.constants.SuccessMessage;
import com.example.simpleMessenger.dtos.ChatHistory;
import com.example.simpleMessenger.dtos.UnreadMessages;
import com.example.simpleMessenger.dtos.UserDto;
import com.example.simpleMessenger.dtos.request.SendMessageRequest;
import com.example.simpleMessenger.dtos.response.ApiResponse;
import com.example.simpleMessenger.service.AuthenticationService;
import com.example.simpleMessenger.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/{fromUser}/message", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<?>> sendMessage(@PathVariable String fromUser,
                                                      @RequestBody SendMessageRequest sendMessageRequest) {
        authenticationService.checkIfAuthenticated();
        messageService.postMessage(fromUser, sendMessageRequest.getTo(), sendMessageRequest.getMessage());
        return new ResponseEntity<>(ApiResponse.<UserDto>builder()
                .message(SuccessMessage.MESSAGE_SENT_SUCCESSFULLY)
                .status(ApiResponseStatus.SUCCESS)
                .statusCode(HttpStatus.OK)
                .build(), HttpStatus.OK);
    }

    @GetMapping(value = "/{toUser}/message", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<UnreadMessages>>> getUnreadMessages(@PathVariable String toUser) {
        authenticationService.checkIfAuthenticated();
        return new ResponseEntity<>(ApiResponse.<List<UnreadMessages>>builder()
                .data(messageService.getUnreadMessages(toUser))
                .message(SuccessMessage.FETCHED_UNREAD_MESSAGES_SUCCESSFULLY)
                .status(ApiResponseStatus.SUCCESS)
                .statusCode(HttpStatus.OK)
                .build(), HttpStatus.OK);
    }

    @GetMapping(value = "/{fromUser}/message/chatHistory", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<ChatHistory>>> getChatHistory(@PathVariable("fromUser") String fromUser,
                                                                         @RequestParam("friend") String toUser) {
        authenticationService.checkIfAuthenticated();
        return new ResponseEntity<>(ApiResponse.<List<ChatHistory>>builder()
                .data(messageService.getChatHistory(fromUser, toUser))
                .message(SuccessMessage.CHAT_HISTORY_FETCHED_SUCCESSFULLY)
                .status(ApiResponseStatus.SUCCESS)
                .statusCode(HttpStatus.OK)
                .build(), HttpStatus.OK);
    }
}
