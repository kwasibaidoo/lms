package com.lms.controllers;

import java.io.IOException;

import com.lms.App;
import com.lms.dao.AppUserDAO;
import com.lms.models.AppUser;
import com.lms.utils.AuthUtil;
import com.lms.utils.ValidationResult;
import com.lms.utils.Validator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {


    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label error_email;

    @FXML
    private Label error_password;


    @FXML
    public void login() {

        ValidationResult emailVal = Validator.validate(email.getText(),"not_null","email");
        ValidationResult passwordVal = Validator.validate(password.getText(),"not_null");


        if(!passwordVal.isSuccess()) {
            error_email.setText(emailVal.getMessage());
            error_password.setText(passwordVal.getMessage());
        }
        else {
            AppUser appUser = AppUserDAO.findUserByEmail(email.getText());
            if(appUser.getEmail() == null || appUser.getEmail().isEmpty()){
                error_email.setText("This email does not exist in our database");
            }
            else{
                if (appUser.getPassword().equals(password.getText())) {
                    // AppUserDAO.setIsLoggedIn(appUser);
                    AuthUtil.getInstance().setUserID(appUser.getId());
                    AuthUtil.getInstance().setUserRole(appUser.getAccountType());
                    try {
                        App.setRoot("layouts/layout");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    error_email.setText("The email or password is wrong");
                }
            }
        }
    }

    @FXML
    public void navSignUp(){
        try {
            App.setRoot("register");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
