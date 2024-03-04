package com.example.simpleMessenger.controller;

import com.example.simpleMessenger.constants.ApiResponseStatus;
import com.example.simpleMessenger.constants.SuccessMessage;
import com.example.simpleMessenger.dtos.ChatHistory;
import com.example.simpleMessenger.dtos.GroupDto;
import com.example.simpleMessenger.dtos.UserDto;
import com.example.simpleMessenger.dtos.request.SendGroupMessageRequest;
import com.example.simpleMessenger.dtos.response.ApiResponse;
import com.example.simpleMessenger.service.AuthenticationService;
import com.example.simpleMessenger.service.GroupMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class GroupMessageController {
    private final AuthenticationService authenticationService;
    private final GroupMessageService groupMessageService;

    @PostMapping(value = "/{fromUser}/groupMessage", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<?>> sendMessage(@PathVariable String fromUser,
                                                      @RequestBody SendGroupMessageRequest sendGroupMessageRequest) {
        authenticationService.checkIfAuthenticated();
        groupMessageService.postMessage(fromUser, sendGroupMessageRequest.getGroupName(), sendGroupMessageRequest.getText());
        return new ResponseEntity<>(ApiResponse.<UserDto>builder()
                .message(SuccessMessage.MESSAGE_SENT_SUCCESSFULLY)
                .status(ApiResponseStatus.SUCCESS)
                .statusCode(HttpStatus.OK)
                .build(), HttpStatus.OK);
    }

    @GetMapping(value = "/{groupName}/groupMessage/chatHistoryInGroup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<ChatHistory>>> getChatHistoryInGroup(@PathVariable("groupName") String groupName) {
        authenticationService.checkIfAuthenticated();
        return new ResponseEntity<>(ApiResponse.<List<ChatHistory>>builder()
                .data(groupMessageService.getChatHistoryInGroup(groupName))
                .message(SuccessMessage.CHAT_HISTORY_FETCHED_SUCCESSFULLY)
                .status(ApiResponseStatus.SUCCESS)
                .statusCode(HttpStatus.OK)
                .build(), HttpStatus.OK);
    }
}
