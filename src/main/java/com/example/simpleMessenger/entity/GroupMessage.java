package com.example.simpleMessenger.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "group_message")
@Data
@Builder
public class GroupMessage extends BaseEntity {
    @Id
    private String messageId;
    private String fromUser;
    private String text;
}
