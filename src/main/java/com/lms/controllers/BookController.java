package com.lms.controllers;


import java.util.LinkedList;


import com.lms.dao.BookDAO;
import com.lms.models.Book;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BookController {

    @FXML
    private TableView<Book> books_table;

    @FXML
    private TableColumn<Book, String> column_author;

    @FXML
    private TableColumn<Book, Integer> column_availableCopies;

    @FXML
    private TableColumn<Book, String> column_category;

    @FXML
    private TableColumn<Book, String> column_name;

    private ObservableList<Book> bookList = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        // Set up the columns
        column_name.setCellValueFactory(new PropertyValueFactory<Book,String>("name")); 
        column_author.setCellValueFactory(new PropertyValueFactory<Book,String>("author")); 
        column_category.setCellValueFactory(new PropertyValueFactory<Book,String>("category")); 
        column_availableCopies.setCellValueFactory(new PropertyValueFactory<Book,Integer>("availableCopies")); 

        // Fetch data from the database
        LinkedList<Book> categories = BookDAO.getBooks();

        // Add data to the ObservableList
        bookList.addAll(categories);

        // Bind the data to the TableView
        books_table.setItems(bookList);
    }

}
