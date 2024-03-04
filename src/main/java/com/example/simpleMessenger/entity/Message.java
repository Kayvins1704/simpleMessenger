package com.example.simpleMessenger.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "message")
@Data
@Builder
public class Message extends BaseEntity {
    @Id
    private String id;
    private String fromUser;
    private String toUser;
    private String text;
    @Indexed
    private Boolean isRead;
}
