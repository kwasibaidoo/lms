package com.lms.controllers;

import com.lms.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class NavigationController {

    @FXML
    private VBox sidebar;

    @FXML
    private VBox content;

    @FXML
    private Label route_home;

    @FXML
    private Label route_books;

    @FXML
    private Label route_category;


    public void navHome() {
        loadPage("home.fxml");
    }

    public void navBooks() {
        loadPage("books.fxml");
    }

    public void navCategories() {
        loadPage("categories.fxml");
    }

    public void navAddCategory() {
        loadPage("addCategory.fxml");
    }



    public void loadPage(String pageName) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(pageName));
            Parent page = loader.load();
            content.getChildren().clear();
            content.getChildren().add(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
