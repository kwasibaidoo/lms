package com.lms.controllers;

import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.ResourceBundle;

import com.lms.dao.BookDAO;
import com.lms.dao.ReservationDAO;
import com.lms.models.Book;
import com.lms.models.Reservation;
import com.lms.utils.AuthUtil;
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

public class AddReservationController implements Router {

    private NotificationToast notificationToast = new NotificationToast();
    private String userID;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> book_combo_box;

    @FXML
    private Button bookReservation;

    @FXML
    private Label error_book;

    @FXML
    private Label error_date;

    @FXML
    private DatePicker reservation_date;

    private ObservableList<String> books = FXCollections.observableArrayList();
    private NavigationController navigationController;

    @FXML
    void bookReservation() {
        if(reservation_date.getValue() == null){
            error_date.setText("Date cannot be empty");
        }
        else{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            ValidationResult bookVal = Validator.validate(book_combo_box.getSelectionModel().getSelectedItem(), "not_null");
            ValidationResult dateVal = Validator.validateDate(reservation_date.getValue().format(formatter), "not_null", "after_today");
    
            if(!bookVal.isSuccess() || !dateVal.isSuccess()) {
                error_book.setText(bookVal.getMessage());
                error_date.setText(dateVal.getMessage());
            }
            else {
                System.out.println(book_combo_box.getSelectionModel().getSelectedItem() + "NAME OF THE BOOK");
                String bookID = BookDAO.getBookID(book_combo_box.getSelectionModel().getSelectedItem());
                System.out.println(bookID + " NOOOOO");
                boolean success = ReservationDAO.createReservation(
                    new Reservation(
                        userID,
                        bookID,
                        0,
                        Timestamp.valueOf(reservation_date.getValue().atStartOfDay())
                    )
                );
    
                if(success) {
                    navigationController.navReservations();
                    // TODO: Deduct copies available value in books table
                }
                else {
                    notificationToast.showNotification(AlertType.ERROR, "Process Failed", "There was a problem while creating a new reservation");
                }
            }
        }
        
    }

    @FXML
    void initialize() {
        LinkedList<Book> bookList = BookDAO.getBooks();
        for (Book book : bookList) {
            books.add(book.getName());
        }
        book_combo_box.setItems(books);

        userID = AuthUtil.getInstance().getUserID();
    }

    @Override
    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

}
