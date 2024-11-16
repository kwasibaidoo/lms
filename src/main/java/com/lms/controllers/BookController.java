package com.lms.controllers;


import java.io.IOException;
import java.util.LinkedList;

import com.lms.App;
import com.lms.dao.BookDAO;
import com.lms.models.Book;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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

    @FXML
    private Button view_book;

    @FXML
    private Button delete_book;

    @FXML
    public void viewBook() {
        try {
            Book selectedBook = books_table.getSelectionModel().getSelectedItem();
            String bookId = selectedBook.getId();
            
            FXMLLoader loader = new FXMLLoader(App.class.getResource("viewbooks.fxml"));
            Parent root = loader.load();

            // Get the controller of the new window
            ViewBookController controller = loader.getController();
            controller.setBookId(bookId); // Pass the book ID to the controller

            // Open the new window
            Stage stage = new Stage();
            stage.setTitle("View Book Details");
            stage.setScene(new Scene(root, 640, 480));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteBook() {
        try {
            Stage stage = new Stage();
            Scene scene = new Scene(App.loadFXML("layouts/layout"), 640, 480);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    private ObservableList<Book> bookList = FXCollections.observableArrayList();




    @FXML
    public void initialize() {
        // Set up the columns
        column_name.setCellValueFactory(new PropertyValueFactory<Book,String>("name")); 
        column_author.setCellValueFactory(new PropertyValueFactory<Book,String>("author_id")); 
        column_category.setCellValueFactory(new PropertyValueFactory<Book,String>("category_id")); 
        column_availableCopies.setCellValueFactory(new PropertyValueFactory<Book,Integer>("availableCopies")); 

        // Fetch data from the database
        LinkedList<Book> categories = BookDAO.getBooks();

        // Add data to the ObservableList
        bookList.addAll(categories);

        // Bind the data to the TableView
        books_table.setItems(bookList);
    }

}
