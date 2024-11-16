package com.lms.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.lms.dao.BookDAO;
import com.lms.models.Book;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewBookController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label author_value;

    @FXML
    private Label category_value;

    @FXML
    private Label copies_left_value;

    @FXML
    private Label location_value;

    @FXML
    private Label name_value;

    @FXML
    private Label total_copies_value;

    private String bookId;

    @FXML
    void initialize() {
        

    }

    public void setBookId(String bookId) {
        this.bookId = bookId;

        // Fetch the book details using the ID
        Book book = BookDAO.getBookById(bookId); 

        if (book != null) {
            name_value.setText(book.getName());
            location_value.setText(book.getLocation());
            author_value.setText(book.getAuthor_id());
            category_value.setText(book.getCategory_id()); 
            copies_left_value.setText(String.valueOf(book.getAvailableCopies()));
            total_copies_value.setText(String.valueOf(book.getAvailableCopies()));
        } else {
            System.out.println("Book not found.");
        }
    }

}
