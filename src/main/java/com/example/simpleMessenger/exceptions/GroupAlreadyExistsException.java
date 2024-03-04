package com.example.simpleMessenger.exceptions;

import com.example.simpleMessenger.constants.ExceptionConstants;

public class GroupAlreadyExistsException extends RuntimeException {
    public GroupAlreadyExistsException() {
        super(ExceptionConstants.GROUP_ALREADY_EXISTS);
    }
}