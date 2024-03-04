package com.example.simpleMessenger.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupMessageDto {
    private String fromUser;
    private String text;
}
