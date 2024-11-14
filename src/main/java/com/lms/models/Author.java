package com.lms.models;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    public Author(String name) {
        this.name = name;
    }
    private String id;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
