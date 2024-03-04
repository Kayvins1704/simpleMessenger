package com.example.simpleMessenger.dtos.request;

import lombok.Data;

import java.util.List;

@Data
public class GroupCreationRequest {
    private String groupName;
    private List<String> usernameList;
}
