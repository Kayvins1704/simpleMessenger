package com.example.simpleMessenger.entity;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "user")
@Data
@Builder
public class User extends BaseEntity {
    @Id
    private String id;
    private String username;
    private String passcode;
    @DBRef
    private List<Token> tokenList;
}


