package com.example.simpleMessenger.entity;

import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
public abstract class BaseEntity {
    @CreatedDate
    private DateTime createdDate;

    @LastModifiedDate
    private DateTime updatedDate;
}
