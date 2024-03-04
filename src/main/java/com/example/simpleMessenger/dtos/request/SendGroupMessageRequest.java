package com.example.simpleMessenger.dtos.request;

import lombok.Data;

@Data
public class SendGroupMessageRequest {
    private String text;
    private String groupName;
}
