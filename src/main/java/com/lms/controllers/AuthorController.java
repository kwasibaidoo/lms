package com.lms.controllers;

import java.sql.Timestamp;
import java.util.LinkedList;

import com.lms.dao.AuthorDAO;
import com.lms.models.Author;
import com.lms.utils.AuthUtil;
import com.lms.utils.NotificationToast;
import com.lms.utils.Router;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AuthorController implements Router {

    private NotificationToast notificationToast = new NotificationToast();
    private NavigationController navigationController = new NavigationController();

    @FXML
    private TableView<Author> author_table;

    @FXML
    private TableColumn<Author, Timestamp> column_date;

    @FXML
    private TableColumn<Author, String> column_name;

    private ObservableList<Author> authorList = FXCollections.observableArrayList();

    @FXML
    public void deleteAuthor() {
        if(AuthUtil.getInstance().getUserRole().equals("patron")){
            notificationToast.showNotification(AlertType.ERROR,"You're not authorised", "You do not have permission to access this page");
        }
        else{
            boolean success = AuthorDAO.deleteAuthor(author_table.getSelectionModel().getSelectedItem().getId());
            if(!success) {
                notificationToast.showNotification(AlertType.ERROR,"Process Failed", "There was a problem while deleting the author");
            }
            else {
                notificationToast.showNotification(AlertType.CONFIRMATION,"Author Deleted", "Author deleted successfully.");
                navigationController.navAuthors();
            }
        }
        
    }


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


    @Override
    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }
}
