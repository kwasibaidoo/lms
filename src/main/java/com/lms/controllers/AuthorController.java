package com.lms.controllers;

import java.sql.Timestamp;
import java.util.LinkedList;

import com.lms.dao.AuthorDAO;
import com.lms.models.Author;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AuthorController {

    @FXML
    private TableView<Author> author_table;

    @FXML
    private TableColumn<Author, Timestamp> column_date;

    @FXML
    private TableColumn<Author, String> column_name;

    private ObservableList<Author> authorList = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        // Set up the columns
        column_name.setCellValueFactory(new PropertyValueFactory<Author,String>("name")); 
        column_date.setCellValueFactory(new PropertyValueFactory<Author,Timestamp>("createdAt")); 

        // Fetch data from the database
        LinkedList<Author> categories = AuthorDAO.getAuthors();

        // Add data to the ObservableList
        authorList.addAll(categories);

        // Bind the data to the TableView
        author_table.setItems(authorList);
    }
}
