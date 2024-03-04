package com.example.simpleMessenger.exceptions;

import com.example.simpleMessenger.constants.ExceptionConstants;

public class GroupDoesNotExistException extends RuntimeException {
    public GroupDoesNotExistException() {
        super(ExceptionConstants.GROUP_DOES_NOT_EXIST);
    }
}
