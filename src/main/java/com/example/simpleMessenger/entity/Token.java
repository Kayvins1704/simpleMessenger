package com.example.simpleMessenger.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "token")
@Data
@Builder
public class Token extends BaseEntity {
    @Id
    public String id;

    public String accessToken;

    public Boolean revoked;

    public Boolean expired;
}
