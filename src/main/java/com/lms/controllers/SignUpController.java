package com.lms.controllers;

import java.io.IOException;

import com.lms.App;
import com.lms.dao.AppUserDAO;
import com.lms.models.AppUser;
import com.lms.utils.NotificationToast;
import com.lms.utils.ValidationResult;
import com.lms.utils.Validator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
        ValidationResult emailVal = Validator.validate(email.getText(),"not_null","email","unique|users,email");
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
                    App.setRoot("login");
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                notificationToast.showNotification(AlertType.ERROR, "Account Creation failed", "There was a problem while creating your account");
            }
        }

        
    }

    @FXML
    public void navSignIn(){
        try {
            App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
