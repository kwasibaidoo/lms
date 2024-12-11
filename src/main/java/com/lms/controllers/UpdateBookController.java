package com.lms.controllers;

import java.util.LinkedList;

import com.lms.dao.AuthorDAO;
import com.lms.dao.BookDAO;
import com.lms.dao.CategoryDAO;
import com.lms.models.Author;
import com.lms.models.Book;
import com.lms.models.Category;
import com.lms.utils.NotificationToast;
import com.lms.utils.Router;
import com.lms.utils.ValidationResult;
import com.lms.utils.Validator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UpdateBookController implements Router {

    private NotificationToast notificationToast = new NotificationToast();
    private NavigationController navigationController = new NavigationController();
    private AuthorDAO authorDAO = new AuthorDAO();
    private BookDAO bookDAO = new BookDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
    private Validator validator = new Validator();

    @FXML
    private Button updateBook;

    @FXML
    private ComboBox<String> author;

    @FXML
    private TextField availableCopies;

    @FXML
    private ComboBox<String> category;

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
    private TextField locationfield;

    @FXML
    private TextField name;

    @FXML
    private TextField totalCopies;

    private String bookId;

    @Override
    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

    private ObservableList<String> authors = FXCollections.observableArrayList();
    private ObservableList<String> categories = FXCollections.observableArrayList();

    @FXML
    void updateBook() {
        ValidationResult nameVal = validator.validate(name.getText(), "not_null", "unique|books,name");
        ValidationResult authorVal = validator.validate(author.getValue(), "not_null");
        ValidationResult categoryVal = validator.validate(category.getValue(), "not_null");
        ValidationResult availableCopiesVal = validator.validate(availableCopies.getText(), "not_null");
        ValidationResult totalCopiesVal = validator.validate(totalCopies.getText(), "not_null");
        ValidationResult locationVal = validator.validate(locationfield.getText(), "not_null");

        if(!nameVal.isSuccess() || !authorVal.isSuccess() || !categoryVal.isSuccess() || !availableCopiesVal.isSuccess() || !totalCopiesVal.isSuccess() || !locationVal.isSuccess() ) {
            error_name.setText(nameVal.getMessage());
            error_author.setText(authorVal.getMessage());
            error_category.setText(categoryVal.getMessage());
            error_availableCopies.setText(availableCopiesVal.getMessage());
            error_totalCopies.setText(totalCopiesVal.getMessage());
            error_location.setText(locationVal.getMessage());
        }
        else {
            // get authorID
            String author_id = authorDAO.getAuthorID(author.getValue());
            // get categoryID
            String category_id = categoryDAO.getCategoryID(category.getValue());
            Book book = new Book(
                name.getText(), 
                author_id,
                category_id,
                Integer.parseInt(availableCopies.getText()),
                Integer.parseInt(totalCopies.getText()),
                locationfield.getText()
            );
            boolean success = bookDAO.updateBook(book,bookId);
            if(success) {
                // redirect to the correct page
                notificationToast.showNotification(AlertType.INFORMATION, "Process successful", "Book updated successfully close window");
            }
            else {
                notificationToast.showNotification(AlertType.ERROR, "Process Failed", "There was a problem while creating a new book");
            }
        }
    }

    @FXML
    public void initialize() {
        try {
            

            LinkedList<Author> authorList = authorDAO.getAuthorsName();
            for (Author author : authorList) {
                authors.add(author.getName());
            }
            author.setItems(authors);

            // categories
            LinkedList<Category> categoryList = categoryDAO.getCategoryNames();
            for (Category category : categoryList) {
                categories.add(category.getName());
            }
            category.setItems(categories);

            

            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

    public void setBookId(String bookId) {
        this.bookId = bookId;

        // Fetch the book details using the ID
        Book book = bookDAO.getBookById(bookId); 

        if (book != null) {
            name.setText(book.getName());
            locationfield.setText(book.getLocation());
            author.setValue(book.getAuthor_id());
            category.setValue(book.getCategory_id()); 
            totalCopies.setText(String.valueOf(book.getAvailableCopies()));
            availableCopies.setText(String.valueOf(book.getAvailableCopies()));
        } else {
            System.out.println("Book not found.");
        }
    }

    

}
