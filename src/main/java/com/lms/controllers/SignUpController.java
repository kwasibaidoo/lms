package com.lms.controllers;

import com.lms.utils.Validator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignUpController {

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
        Validator.validate(name.getText(),"");

        // input user dtails
    }

}
