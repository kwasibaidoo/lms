package com.lms.controllers;


import com.lms.dao.AuthorDAO;
import com.lms.models.Author;
import com.lms.utils.NotificationToast;
import com.lms.utils.Router;
import com.lms.utils.ValidationResult;
import com.lms.utils.Validator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UpdateAuthorController implements Router {

    private NotificationToast notificationToast = new NotificationToast();

    @FXML
    private Button button;

    @FXML
    private Label error_name;

    @FXML
    private TextField name;

    private NavigationController navigationController = new NavigationController();
    private String authorId;

    @Override
    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

    @FXML
    public void updateAuthor() {
        ValidationResult nameVal = Validator.validate(name.getText(), "not_null","unique|authors,name");
        if(!nameVal.isSuccess()) {
            error_name.setText(nameVal.getMessage());
        }
        else {
            Author author = new Author(name.getText());
            boolean success = AuthorDAO.updateAuthor(author, authorId);
            if(success) {
                notificationToast.showNotification(AlertType.INFORMATION, "Process successful", "Author updated successfully close window");
            }
            else {
                notificationToast.showNotification(AlertType.ERROR, "Process Failed", "There was a problem while updating author");
            }
        }
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;

        // Fetch the book details using the ID
        Author author = AuthorDAO.getAuthorById(authorId); 

        if (author != null) {
            name.setText(author.getName());
        } else {
            System.out.println("Author not found.");
        }
    }

}
