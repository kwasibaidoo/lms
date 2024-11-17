package com.lms.models;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Borrowing {


    private String id;
    private String user_id;
    private String book_id;
    private Integer status;
    private Timestamp dateBorrowed;
    private Timestamp duedate;
    private Timestamp deletedAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Borrowing(String user_id, String book_id, Timestamp dateBorrowed, Timestamp duedate){
        this.user_id = user_id;
        this.book_id = book_id;
        this.dateBorrowed = dateBorrowed;
        this.duedate = duedate;
    }
}
