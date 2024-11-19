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

    public Book(String name, String author_id, String category_id, int availableCopies, int totalCopies, String location) {
        this.name = name;
        this.author_id = author_id;
        this.category_id = category_id;
        this.location = location;
        this.availableCopies = availableCopies;
        this.totalCopies = totalCopies;
    }

    public Book(int availableCopies) {
        this.availableCopies = availableCopies;
    }
}
