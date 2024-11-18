package com.lms.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.ResourceBundle;

import com.lms.App;
import com.lms.dao.BorrowDAO;
import com.lms.models.Borrowing;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class BorrowingController implements Router {

    private NotificationToast notificationToast = new NotificationToast();
    private NavigationController navigationController = new NavigationController();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Borrowing> borrowings_table;

    @FXML
    private TableColumn<Borrowing, String> column_book;

    @FXML
    private TableColumn<Borrowing, Timestamp> column_date_borrowed;

    @FXML
    private TableColumn<Borrowing, Timestamp> column_due_date;

    @FXML
    private TableColumn<Borrowing, String> column_user;

    @FXML
    private Button delete_record;

    @FXML
    private Button view_record;

    private ObservableList<Borrowing> bookList = FXCollections.observableArrayList();

    @FXML
    void deleteRecord(MouseEvent event) {
        boolean success = BorrowDAO.deleteBorrowing(borrowings_table.getSelectionModel().getSelectedItem().getId());
        if(!success) {
            notificationToast.showNotification(AlertType.ERROR,"Process Failed", "There was a problem while deleting the record");
        }
        else {
            notificationToast.showNotification(AlertType.CONFIRMATION,"Borrowing Record Deleted", "Record deleted successfully.");
            navigationController.navBorrowing();
        }
    }

    @FXML
    void viewRecord(MouseEvent event) {
        try {
            Borrowing selectedBorrowing = borrowings_table.getSelectionModel().getSelectedItem();
            String borrowing_id = selectedBorrowing.getId();
            
            FXMLLoader loader = new FXMLLoader(App.class.getResource("viewborrowing.fxml"));
            Parent root = loader.load();

            // Get the controller of the new window
            ViewBorrowingController controller = loader.getController();
            controller.setBorrowingId(borrowing_id); // Pass the book ID to the controller

            // Open the new window
            Stage stage = new Stage();
            stage.setTitle("View Record Details");
            stage.setScene(new Scene(root, 640, 480));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        column_book.setCellValueFactory(new PropertyValueFactory<Borrowing, String>("book_id"));
        column_user.setCellValueFactory(new PropertyValueFactory<Borrowing, String>("user_id"));
        column_date_borrowed.setCellValueFactory(new PropertyValueFactory<Borrowing, Timestamp>("dateBorrowed"));
        column_due_date.setCellValueFactory(new PropertyValueFactory<Borrowing, Timestamp>("due_date"));

        if(AuthUtil.getInstance().getUserRole().equals("patron")){
            LinkedList<Borrowing> borrowingList = BorrowDAO.getUserRecords(AuthUtil.getInstance().getUserID());
            for (Borrowing borrowing : borrowingList) {
                bookList.add(borrowing);
            }
            borrowings_table.setItems(bookList);
        }
        else{
            LinkedList<Borrowing> borrowingList = BorrowDAO.getAllRecords();
            for (Borrowing borrowing : borrowingList) {
                bookList.add(borrowing);
            }
            borrowings_table.setItems(bookList);
        }

    }

    @Override
    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

}
