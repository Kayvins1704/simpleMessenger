package com.example.simpleMessenger.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "group")
@Data
@Builder
public class Group extends BaseEntity {
    @Id
    private String id;
    @Indexed
    private String groupName;
    @DBRef
    private List<GroupMessage> groupMessageList;
    @DBRef
    private List<User> userList;
}
