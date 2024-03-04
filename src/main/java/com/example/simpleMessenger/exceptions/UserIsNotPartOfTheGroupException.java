package com.example.simpleMessenger.exceptions;

import com.example.simpleMessenger.constants.ExceptionConstants;

public class UserIsNotPartOfTheGroupException extends RuntimeException {
    public UserIsNotPartOfTheGroupException() {super(ExceptionConstants.USER_IS_NOT_PART_OF_THE_GROUP);}
}
