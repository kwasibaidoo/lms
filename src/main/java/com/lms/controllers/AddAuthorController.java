package com.lms.controllers;

import com.lms.dao.AuthorDAO;
import com.lms.models.Author;
import com.lms.utils.NotificationToast;
import com.lms.utils.ValidationResult;
import com.lms.utils.Validator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddAuthorController {

    private NotificationToast notificationToast;

    @FXML
    private Button button;

    @FXML
    private Label error_name;

    @FXML
    private TextField name;

    @FXML
    public void addAuthor() {
        ValidationResult nameVal = Validator.validate(name.getText(), "not_null","unique|authors,name");
        if(!nameVal.isSuccess()) {
            error_name.setText(nameVal.getMessage());
        }
        else {
            Author author = new Author(name.getText());
            boolean success = AuthorDAO.createAuthor(author);
            if(success) {
                // redirect to authors page
            }
            else {
                notificationToast.showNotification(AlertType.ERROR, "Process Failed", "There was a problem while creating a new author");
            }
        }
    }

}
