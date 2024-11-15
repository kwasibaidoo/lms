package com.lms.controllers;

import com.lms.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class NavigationController {

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
        loadPage("addBook.fxml");
    }

    public void navAddAuthor() {
        loadPage("addauthor.fxml");
    }

    public void navAuthors() {
        loadPage("author.fxml");
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
