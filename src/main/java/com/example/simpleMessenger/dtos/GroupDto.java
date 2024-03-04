package com.example.simpleMessenger.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GroupDto {
    private String groupName;
    private List<String> username;
}
