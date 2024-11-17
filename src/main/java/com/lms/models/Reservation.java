package com.lms.models;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    private String id;
    private String user_id;
    private String book_id;
    private int status;
    private Timestamp reservation_date;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Reservation(String user_id, String book_id, int status, Timestamp reservation_date) {
        this.user_id = user_id;
        this.book_id = book_id;
        this.status = status;
        this.reservation_date = reservation_date;
    }
}
