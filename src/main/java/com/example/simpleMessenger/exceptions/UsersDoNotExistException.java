package com.example.simpleMessenger.exceptions;

import com.example.simpleMessenger.constants.ExceptionConstants;

public class UsersDoNotExistException extends RuntimeException {
    public UsersDoNotExistException() {
        super(ExceptionConstants.USERS_DO_NOT_EXIST);
    }
}

