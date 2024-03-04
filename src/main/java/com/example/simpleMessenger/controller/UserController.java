package com.example.simpleMessenger.controller;

import com.example.simpleMessenger.constants.ApiResponseStatus;
import com.example.simpleMessenger.constants.SuccessMessage;
import com.example.simpleMessenger.dtos.UserDto;
import com.example.simpleMessenger.dtos.response.ApiResponse;
import com.example.simpleMessenger.service.AuthenticationService;
import com.example.simpleMessenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<UserDto>>> fetchAllUsers() {
        authenticationService.checkIfAuthenticated();
        return new ResponseEntity<>(ApiResponse.<List<UserDto>>builder()
                .data(userService.fetchAllUsers())
                .message(SuccessMessage.USERS_FETCHED_SUCCESSFULLY)
                .status(ApiResponseStatus.SUCCESS)
                .statusCode(HttpStatus.OK)
                .build(), HttpStatus.OK);
    }
}
