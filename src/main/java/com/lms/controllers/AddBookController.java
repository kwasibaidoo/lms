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

public class AddBookController implements Router {

    private NotificationToast notificationToast = new NotificationToast();
    private AuthorDAO authorDAO = new AuthorDAO();
    private Validator validator = new Validator();
    private CategoryDAO categoryDAO = new CategoryDAO();
    private BookDAO bookDAO = new BookDAO();

    @FXML
    private Button addBook;

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

    private NavigationController navigationController = new NavigationController();

    @Override
    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

    private ObservableList<String> authors = FXCollections.observableArrayList();
    private ObservableList<String> categories = FXCollections.observableArrayList();

    @FXML
    void addBook() {
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
            boolean success = bookDAO.createBook(book);
            if(success) {
                // redirect to the correct page
                navigationController.navBooks();
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
            author.setValue("");

            // categories
            LinkedList<Category> categoryList = categoryDAO.getCategoryNames();
            for (Category category : categoryList) {
                categories.add(category.getName());
            }
            category.setItems(categories);
            category.setValue("");

            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

}
