package com.lms.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AddBookController {

    @FXML
    private Button addBook;

    @FXML
    private TextField author;

    @FXML
    private TextField availableCopies;

    @FXML
    private TextField category;

    @FXML
    private Label error_author;

    @FXML
    private Label error_availableCopies;

    @FXML
    private Label error_category;

    @FXML
    private Label error_location;

    @FXML
    private Label error_name;

    @FXML
    private Label error_totalCopies;

    @FXML
    private TextField location;

    @FXML
    private TextField name;

    @FXML
    private TextField totalCopies;

    @FXML
    void addBook(MouseEvent event) {

    }

}
