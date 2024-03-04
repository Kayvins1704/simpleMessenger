package com.example.simpleMessenger.dtos.request;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String passcode;
}
