package com.lms.controllers;

import java.io.IOException;

import com.lms.App;
import com.lms.utils.AuthUtil;
import com.lms.utils.NotificationToast;
import com.lms.utils.Router;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class NavigationController {

    private NotificationToast notificationToast = new NotificationToast();

    @FXML
    private VBox content;

    @FXML
    private Label route_add_author;

    @FXML
    private Label route_add_book;

    @FXML
    private Label route_add_category;

    @FXML
    private Label route_author;

    @FXML
    private Label route_books;

    @FXML
    private Label route_category;

    @FXML
    private Label route_home;

    @FXML
    private VBox sidebar;


    public void navHome() {
        loadPage("home.fxml");
    }

    public void navBooks() {
        loadPage("books.fxml");
    }

    public void navCategories() {
        loadPage("categories.fxml");
    }

    public void navAddBooks() {
        if(AuthUtil.getInstance().getUserRole().equals("patron")) {
            notificationToast.showNotification(AlertType.ERROR,"You're not authorised", "You do not have permission to access this page");
        }
        else{
            loadPage("addBook.fxml");
        }
    }

    public void navAddAuthor() {
        if(AuthUtil.getInstance().getUserRole().equals("patron")) {
            notificationToast.showNotification(AlertType.ERROR,"You're not authorised", "You do not have permission to access this page");
        }
        else{
            loadPage("addauthor.fxml");
        }
    }

    public void navAuthors() {
        loadPage("author.fxml");
    }

    public void navAddCategory() {
        if(AuthUtil.getInstance().getUserRole().equals("patron")) {
            notificationToast.showNotification(AlertType.ERROR,"You're not authorised", "You do not have permission to access this page");
        }
        else{
            loadPage("addCategory.fxml");
        }
    }

    public void navReservations() {
        loadPage("reservations.fxml");
    }

    public void navAddReservation() {
        loadPage("addReservation.fxml");
    }

    public void navAddBorrowing() {
        if(AuthUtil.getInstance().getUserRole().equals("patron")) {
            notificationToast.showNotification(AlertType.ERROR,"You're not authorised", "You do not have permission to access this page");
        }
        else{
            loadPage("addborrowing.fxml");
        }
    }

    public void navBorrowing() {
        loadPage("borrowings.fxml");
    }

    @FXML
    void logout() {
        AuthUtil.getInstance().setUserID("");
        AuthUtil.getInstance().setUserRole("");

        try {
            App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public void loadPage(String pageName) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(pageName));
            Parent page = loader.load();
            initializeChildController(loader.getController());
            content.getChildren().clear();
            content.getChildren().add(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initializeChildController(Object childController) {
        if (childController instanceof Router) {
            ((Router) childController).setNavigationController(this);
        }
    }
    
    

}
