package com.example.simpleMessenger.dtos.request;

import lombok.Data;

@Data
public class SendMessageRequest {
    private String to;
    private String message;
}
