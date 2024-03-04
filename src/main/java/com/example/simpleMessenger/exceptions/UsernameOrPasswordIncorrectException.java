package com.example.simpleMessenger.exceptions;

import com.example.simpleMessenger.constants.ExceptionConstants;

public class UsernameOrPasswordIncorrectException extends RuntimeException {
    public UsernameOrPasswordIncorrectException() {
        super(ExceptionConstants.USERNAME_OR_PASSWORD_IS_INCORRECT);
    }
}
