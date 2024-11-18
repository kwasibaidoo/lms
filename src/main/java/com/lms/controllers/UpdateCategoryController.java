package com.lms.controllers;


import com.lms.dao.AuthorDAO;
import com.lms.dao.CategoryDAO;
import com.lms.models.Author;
import com.lms.models.Category;
import com.lms.utils.NotificationToast;
import com.lms.utils.Router;
import com.lms.utils.ValidationResult;
import com.lms.utils.Validator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;

public class UpdateCategoryController implements Router {

    private NotificationToast notificationToast = new NotificationToast();
    private NavigationController navigationController = new NavigationController();

    @FXML
    private VBox content;

    @FXML
    private TextField name;

    @FXML
    private Label error_name;

    private String categoryId;

    @FXML
    public void addCategory() {
        ValidationResult nameResult = Validator.validate(name.getText(), "not_null", "unique|categories,name");

        if(!nameResult.isSuccess()) {
            error_name.setText(nameResult.getMessage());
        }
        else {
            // insert category into the database
            Category category = new Category(name.getText());
            boolean success = CategoryDAO.updateCategory(category, categoryId);

            if(success) {
                notificationToast.showNotification(AlertType.INFORMATION, "Process successful", "Category updated successfully close window");
            }
            else {
                notificationToast.showNotification(AlertType.ERROR, "Process Failed", "There was a problem while creating a new category");
            }
        }
    }

    @Override
    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;

        
        Author author = AuthorDAO.getAuthorById(categoryId); 

        if (author != null) {
            name.setText(author.getName());
        } else {
            System.out.println("Author not found.");
        }
    }
}
