package com.example.simpleMessenger.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatHistory {
    private String username;
    private String text;
}
