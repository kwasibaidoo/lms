package com.lms.controllers;

import java.io.IOException;

import com.lms.App;
import com.lms.dao.AppUserDAO;
import com.lms.models.AppUser;
import com.lms.utils.NotificationToast;
import com.lms.utils.ValidationResult;
import com.lms.utils.Validator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class SignUpController {

    private NotificationToast notificationToast = new NotificationToast();

    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    private TextField confirm_password;

    @FXML
    private Label error_name;

    @FXML
    private Label error_password;

    @FXML
    private Label error_email;

    @FXML
    private Label error_confirm_password;

    @FXML
    public void register() {
        // validate user input
        ValidationResult nameVal = Validator.validate(name.getText(),"not_null","min:1");
        ValidationResult emailVal = Validator.validate(email.getText(),"not_null","email","unique|email");
        ValidationResult passwordVal = Validator.passwordValidation(password.getText(),confirm_password.getText());

        // boolean nameVal = true;
        // boolean emailVal = true;
        // boolean passwordVal = true;

        if(!nameVal.isSuccess() || !emailVal.isSuccess() || !passwordVal.isSuccess()) {
            error_name.setText(nameVal.getMessage());
            error_email.setText(emailVal.getMessage());
            error_password.setText(passwordVal.getMessage());
        }
        else {
            // input user details
            AppUser appUser = new AppUser(name.getText(),email.getText(),password.getText(),"patron");
            boolean success = AppUserDAO.createUser(appUser);
            if(success) {
                
                try {
                    // load home page
                    // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
                    // Parent root = fxmlLoader.load();
                    // Scene homeScene = new Scene(root);
                    // Stage primaryStage = (Stage) error_name.getScene().getWindow();
                    // primaryStage.setScene(homeScene);
                    // primaryStage.setTitle("Welcome to the Home Page");
                    // primaryStage.show();
                    App.setRoot("home");
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                notificationToast.showNotification(AlertType.ERROR, "Account Creation failed", "There was a problem while creating your account");
            }
        }

        
    }

}
