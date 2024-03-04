package com.example.simpleMessenger.exceptions;

import com.example.simpleMessenger.constants.ExceptionConstants;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super(ExceptionConstants.USER_ALREADY_EXISTS_EXCEPTION);
    }
}
