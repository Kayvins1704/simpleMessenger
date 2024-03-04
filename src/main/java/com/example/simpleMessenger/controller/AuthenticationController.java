package com.example.simpleMessenger.controller;

import com.example.simpleMessenger.constants.ApiResponseStatus;
import com.example.simpleMessenger.constants.SuccessMessage;
import com.example.simpleMessenger.dtos.UserDto;
import com.example.simpleMessenger.dtos.request.UserRequest;
import com.example.simpleMessenger.dtos.response.ApiResponse;
import com.example.simpleMessenger.dtos.response.AuthenticationResponse;
import com.example.simpleMessenger.service.AuthenticationService;
import com.example.simpleMessenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserDto>> signUp(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(ApiResponse.<UserDto>builder()
                .data(authenticationService.signupUser(userRequest))
                .message(SuccessMessage.USER_SIGNUP_IS_SUCCESSFUL)
                .status(ApiResponseStatus.SUCCESS)
                .statusCode(HttpStatus.CREATED)
                .build(), HttpStatus.CREATED);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(ApiResponse.<AuthenticationResponse>builder()
                .data(authenticationService.login(userRequest))
                .message(SuccessMessage.USER_LOGIN_IS_SUCCESSFUL)
                .status(ApiResponseStatus.SUCCESS)
                .statusCode(HttpStatus.OK)
                .build(), HttpStatus.OK);
    }

    @PostMapping(value = "/logout", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<?>> logout(@RequestBody UserRequest userRequest) {
        authenticationService.logout(userRequest);
        return new ResponseEntity<>(ApiResponse.builder()
                .message(SuccessMessage.USER_LOGOUT_IS_SUCCESSFUL)
                .status(ApiResponseStatus.SUCCESS)
                .statusCode(HttpStatus.OK)
                .build(), HttpStatus.OK);
    }
}
