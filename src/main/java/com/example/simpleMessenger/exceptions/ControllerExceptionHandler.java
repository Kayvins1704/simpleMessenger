package com.example.simpleMessenger.exceptions;

import com.example.simpleMessenger.constants.ApiResponseStatus;
import com.example.simpleMessenger.dtos.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = {UserAlreadyExistsException.class, UsernameOrPasswordIncorrectException.class,
            UserDoesNotExistException.class, GroupDoesNotExistException.class, UsersDoNotExistException.class,
            GroupAlreadyExistsException.class, UserIsNotPartOfTheGroupException.class})
    public ResponseEntity<ApiResponse<?>> userSurroundingException(RuntimeException ex, WebRequest request) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .status(ApiResponseStatus.FAILURE)
                .statusCode(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserNotLoggedInException.class})
    public ResponseEntity<ApiResponse<?>> userNotLoggedInException(RuntimeException ex, WebRequest request) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .status(ApiResponseStatus.FAILURE)
                .statusCode(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }
}
