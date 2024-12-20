package com.lms.controllers;

import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.ResourceBundle;

import com.lms.dao.AppUserDAO;
import com.lms.dao.BookDAO;
import com.lms.dao.BorrowDAO;
import com.lms.models.AppUser;
import com.lms.models.Book;
import com.lms.models.Borrowing;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class AddBorrowingController implements Router {

    private NavigationController navigationController = new NavigationController();
    private NotificationToast notificationToast = new NotificationToast();
    private AppUserDAO appUserDAO = new AppUserDAO();
    private Validator validator = new Validator();
    private BookDAO bookDAO = new BookDAO();
    private BorrowDAO borrowDAO = new BorrowDAO();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addRecord;

    @FXML
    private ComboBox<String> book;

    @FXML
    private DatePicker date_borrowed;

    @FXML
    private DatePicker due_date;

    @FXML
    private Label error_book;

    @FXML
    private Label error_date_borrowed;

    @FXML
    private Label error_due_date;

    @FXML
    private Label error_user;

    @FXML
    private ComboBox<String> user;

    private ObservableList<String> users = FXCollections.observableArrayList();
    private ObservableList<String> books = FXCollections.observableArrayList();

    @FXML
    void addRecord(MouseEvent event) {
        if(date_borrowed.getValue() == null || due_date.getValue() == null){
            error_due_date.setText("Date cannot be empty");
        }
        else{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            ValidationResult userVal = validator.validate(user.getValue(), "not_null");
            ValidationResult bookVal = validator.validate(book.getValue(), "not_null");
            ValidationResult dateBorrowedVal = validator.validateDate(date_borrowed.getValue().format(formatter), "not_null");
            ValidationResult dateDueVal = validator.validateDate(due_date.getValue().format(formatter), "not_null");

            if(!userVal.isSuccess() || !bookVal.isSuccess() || !dateBorrowedVal.isSuccess() || !dateDueVal.isSuccess()) {
                error_book.setText(bookVal.getMessage());
                error_user.setText(userVal.getMessage());
                error_due_date.setText(dateDueVal.getMessage());
                error_date_borrowed.setText(dateBorrowedVal.getMessage());
            }
            else {
                String userID = appUserDAO.getUserID(user.getSelectionModel().getSelectedItem());
                String bookID = bookDAO.getBookID(book.getSelectionModel().getSelectedItem());
                Book book = bookDAO.getBookById(bookID);
                // check for available copies
                if(book.getAvailableCopies() == 0){
                    error_book.setText("Not enough books available");
                }
                else{
                    Borrowing borrowing = new Borrowing(
                        userID,
                        bookID,
                        Timestamp.valueOf(date_borrowed.getValue().atStartOfDay()),
                        Timestamp.valueOf(due_date.getValue().atStartOfDay())
                    );
                    boolean success = borrowDAO.createBorrowingRecord(borrowing);
                    if(success) {
                        navigationController.navBorrowing();
                        // deduct available copies in books
                        Book updatedBook = new Book(book.getAvailableCopies() - 1);
                        bookDAO.updateBookAvailableCopies(updatedBook, book.getId());
                    }
                    else{
                        notificationToast.showNotification(AlertType.ERROR, "Process Failed", "There was a problem while creating a new record");
                    }
                }
                
            }
        }
        
    }

    @FXML
    void initialize() {
        try {
            LinkedList<AppUser> userList = appUserDAO.getUsers();
            for (AppUser appUser : userList) {
                users.add(appUser.getName());
            }
            user.setItems(users);
            user.setValue("");


            // get books
            LinkedList<Book> bookList = bookDAO.getBooks();
            for (Book book : bookList) {
                books.add(book.getName());
            }

            book.setItems(books);
            book.setValue("");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

}
