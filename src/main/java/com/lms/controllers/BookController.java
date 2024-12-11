package com.lms.controllers;


import java.io.IOException;
import java.util.LinkedList;

import com.lms.App;
import com.lms.dao.BookDAO;
import com.lms.models.Book;
import com.lms.utils.AuthUtil;
import com.lms.utils.NotificationToast;
import com.lms.utils.Router;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class BookController implements Router {

    private NotificationToast notificationToast = new NotificationToast();
    private NavigationController navigationController = new NavigationController();
    private BookDAO bookDAO = new BookDAO();

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
    private Button update_book;

    @FXML
    private TextField search_book;

    // @FXML
    public void search(String query) {
        String lowerCaseSearchText = query.toLowerCase();
        filteredBookList.clear();
        if(lowerCaseSearchText.isEmpty()){
            filteredBookList.addAll(bookList);
        }
        else{
            LinkedList<Book> searchResults = bookDAO.findBook(lowerCaseSearchText);
            filteredBookList.addAll(searchResults);
        }
        

        
        books_table.setItems(filteredBookList);
    }

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
    public void updateBook() {
        if(AuthUtil.getInstance().getUserRole().equals("patron")){
            notificationToast.showNotification(AlertType.ERROR,"You're not authorised", "You do not have permission to access this page");
        }
        else{
            try {
                Book selectedBook = books_table.getSelectionModel().getSelectedItem();
                String bookId = selectedBook.getId();
                
                FXMLLoader loader = new FXMLLoader(App.class.getResource("updatebook.fxml"));
                Parent root = loader.load();
    
                // Get the controller of the new window
                UpdateBookController controller = loader.getController();
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
        
    }

    @FXML
    public void deleteBook() {
        if(AuthUtil.getInstance().getUserRole().equals("patron")){
            notificationToast.showNotification(AlertType.ERROR,"You're not authorised", "You do not have permission to access this page");
        }
        else{
            boolean success = bookDAO.deleteBook(books_table.getSelectionModel().getSelectedItem().getId());
            if(!success) {
                notificationToast.showNotification(AlertType.ERROR,"Process Failed", "There was a problem while deleting the book");
            }
            else {
                notificationToast.showNotification(AlertType.CONFIRMATION,"Book Deleted", "Book deleted successfully.");
                navigationController.navBooks();
            }
        }
        
    }

    

    private ObservableList<Book> bookList = FXCollections.observableArrayList();
    private ObservableList<Book> filteredBookList = FXCollections.observableArrayList();




    @FXML
    public void initialize() {
        // Set up the columns
        column_name.setCellValueFactory(new PropertyValueFactory<Book,String>("name")); 
        column_author.setCellValueFactory(new PropertyValueFactory<Book,String>("author_id")); 
        column_category.setCellValueFactory(new PropertyValueFactory<Book,String>("category_id")); 
        column_availableCopies.setCellValueFactory(new PropertyValueFactory<Book,Integer>("availableCopies")); 

        // Fetch data from the database
        LinkedList<Book> categories = bookDAO.getBooks();

        // Add data to the ObservableList
        bookList.addAll(categories);

        // Bind the data to the TableView
        books_table.setItems(bookList);
        filteredBookList.addAll(bookList);
        // search stuff
        search_book.textProperty().addListener((observable, oldValue, newValue) -> {
            search(newValue);  // Trigger the search every time the text changes
        });
    }

    @Override
    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

}
