package com.lms.models;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Category {
    
    private String id;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Category(String name) {
        this.name = name;
    }
}
