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
    private boolean status;
    private Timestamp dateBorrowed;
    private Timestamp duedate;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
