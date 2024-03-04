package com.example.simpleMessenger.exceptions;

import com.example.simpleMessenger.constants.ExceptionConstants;

public class UserDoesNotExistException extends RuntimeException {
    public UserDoesNotExistException(String user) {
        super(user + " " + ExceptionConstants.USER_DOES_NOT_EXIST);
    }
}
