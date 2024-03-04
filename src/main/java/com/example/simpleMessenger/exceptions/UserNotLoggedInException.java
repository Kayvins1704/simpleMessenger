package com.example.simpleMessenger.exceptions;

import com.example.simpleMessenger.constants.ExceptionConstants;

public class UserNotLoggedInException extends RuntimeException {
    public UserNotLoggedInException() {
        super(ExceptionConstants.USER_NOT_LOGGED_IN_EXCEPTION);
    }
}

