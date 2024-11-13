package com.lms.controllers;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class CategoryController {

    @FXML
    private VBox content;
    @FXML
    private Button category_button;


    public void addCategory() {
        try {
            // Load the Add Category page dynamically
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lms/addCategory.fxml"));
            Parent addCategoryPage = loader.load();
            
            // You can either load the page into the current content VBox
            content.getChildren().clear();  // Clear any existing content
            content.getChildren().add(addCategoryPage);  // Add the new page content
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
