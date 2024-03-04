package com.example.simpleMessenger.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class UnreadMessages {
    @JsonIgnore
    private List<String> messageIds;
    private String fromUser;
    private List<String> texts;
}
