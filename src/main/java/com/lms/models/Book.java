package com.lms.models;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private String id;
    private String name;
    private String author_id;
    private String category_id;
    private String location;
    private int availableCopies;
    private int totalCopies;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
