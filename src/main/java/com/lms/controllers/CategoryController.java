package com.lms.controllers;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;

import com.lms.dao.CategoryDAO;
import com.lms.models.Category;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class CategoryController {

    @FXML
    private VBox content;

    @FXML
    private TableView<Category> category_table;
    
    @FXML
    private Button category_button;

    @FXML
    private TableColumn<Category,String> column_name;

    @FXML
    private TableColumn<Category,Timestamp> column_date;

    private ObservableList<Category> categoryList = FXCollections.observableArrayList();


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


   


    @FXML
    public void initialize() {
        // Set up the columns
        column_name.setCellValueFactory(new PropertyValueFactory<Category,String>("name")); 
        column_date.setCellValueFactory(new PropertyValueFactory<Category,Timestamp>("createdAt")); 

        // Fetch data from the database
        LinkedList<Category> categories = CategoryDAO.getCategories();

        // Add data to the ObservableList
        categoryList.addAll(categories);

        // Bind the data to the TableView
        category_table.setItems(categoryList);
    }
}
