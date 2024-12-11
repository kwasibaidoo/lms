package com.lms.controllers;


import com.lms.dao.CategoryDAO;
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

public class AddCategoryController implements Router {

    private NotificationToast notificationToast = new NotificationToast();
    private NavigationController navigationController = new NavigationController();
    private Validator validator = new Validator();
    private CategoryDAO categoryDAO = new CategoryDAO();

    @FXML
    private VBox content;

    @FXML
    private TextField name;

    @FXML
    private Label error_name;

    @FXML
    public void addCategory() {
        ValidationResult nameResult = validator.validate(name.getText(), "not_null", "unique|categories,name");

        if(!nameResult.isSuccess()) {
            error_name.setText(nameResult.getMessage());
        }
        else {
            // insert category into the database
            Category category = new Category(name.getText());
            boolean success = categoryDAO.createCategory(category);

            if(success) {
                navigationController.navCategories();
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
}
