package com.example.simpleMessenger.controller;

import com.example.simpleMessenger.constants.ApiResponseStatus;
import com.example.simpleMessenger.constants.SuccessMessage;
import com.example.simpleMessenger.dtos.GroupDto;
import com.example.simpleMessenger.dtos.request.GroupCreationRequest;
import com.example.simpleMessenger.dtos.response.ApiResponse;
import com.example.simpleMessenger.service.AuthenticationService;
import com.example.simpleMessenger.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private final AuthenticationService authenticationService;
    private final GroupService groupService;

    @GetMapping(value = "/fetchAllUsers/{groupName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<GroupDto>> fetchAllUsersInGroup(@PathVariable("groupName") String groupName) {
        authenticationService.checkIfAuthenticated();
        return new ResponseEntity<>(ApiResponse.<GroupDto>builder()
                .data(groupService.fetchAllUsersInAGroup(groupName))
                .message(SuccessMessage.USERS_FETCHED_SUCCESSFULLY)
                .status(ApiResponseStatus.SUCCESS)
                .statusCode(HttpStatus.OK)
                .build(), HttpStatus.OK);
    }

    @PostMapping(value = "/createGroup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<?>> createGroup(@RequestBody GroupCreationRequest groupCreationRequest) {
        authenticationService.checkIfAuthenticated();
        groupService.createGroup(groupCreationRequest);
        return new ResponseEntity<>(ApiResponse.<List<GroupDto>>builder()
                .message(SuccessMessage.GROUP_CREATED_SUCCESSFULLY)
                .status(ApiResponseStatus.SUCCESS)
                .statusCode(HttpStatus.OK)
                .build(), HttpStatus.OK);
    }
}
