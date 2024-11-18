package com.lms.controllers;

import java.net.URL;
import java.util.ResourceBundle;


import com.lms.dao.BorrowDAO;
import com.lms.models.Borrowing;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewBorrowingController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label book_value;

    @FXML
    private Label date_borrowed_value;

    @FXML
    private Label due_date_value;

    @FXML
    private Label patron_name_value;

    @FXML
    private Label status_value;

    @FXML
    void initialize() {

    }
    private String borrowingID;

    public void setBorrowingId(String borrowingID) {
        this.borrowingID = borrowingID;

        // Fetch the book details using the ID
        Borrowing borrowing = BorrowDAO.getRecordByID(borrowingID); 

        if (borrowing != null) {
            patron_name_value.setText(borrowing.getUser_id());
            book_value.setText(borrowing.getBook_id());
            due_date_value.setText(borrowing.getDue_date().toString());
            date_borrowed_value.setText(borrowing.getDateBorrowed().toString()); 
            status_value.setText(String.valueOf(borrowing.getStatus()));
        } else {
            System.out.println("Record not found.");
        }
    }

}