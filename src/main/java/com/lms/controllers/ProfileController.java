package com.lms.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.lms.dao.AppUserDAO;
import com.lms.models.AppUser;
import com.lms.utils.AuthUtil;
import com.lms.utils.NotificationToast;
import com.lms.utils.Router;
import com.lms.utils.ValidationResult;
import com.lms.utils.Validator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ProfileController implements Router {

    private NotificationToast notificationToast = new NotificationToast();
    private NavigationController navigationController = new NavigationController();
    private String oldPassword;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField confirm_new_password;

    @FXML
    private TextField email;

    @FXML
    private Label error_confirm_new_password;

    @FXML
    private Label error_email;

    @FXML
    private Label error_name;

    @FXML
    private Label error_new_password;

    @FXML
    private Label error_old_password;

    @FXML
    private TextField name;

    @FXML
    private PasswordField new_password;

    @FXML
    private PasswordField old_password;

    @FXML
    private Button updatePassword;

    @FXML
    private Button updateProfile;


    @FXML
    void updatePassword(MouseEvent event) {
       ValidationResult oldPasswordVal = Validator.validate(old_password.getText(), "not_null");
       ValidationResult passwordVal = Validator.passwordValidation(new_password.getText(),confirm_new_password.getText());

       if(!oldPasswordVal.isSuccess() || !passwordVal.isSuccess()) {
            error_old_password.setText(oldPasswordVal.getMessage());
            error_new_password.setText(passwordVal.getMessage());
       }
       else{
            if(!old_password.getText().equals(oldPassword)) {
                error_old_password.setText("Old password is incorrect");
            }
            else{
                AppUser appUser = new AppUser(new_password.getText());
                boolean success = AppUserDAO.updateUserPassword(appUser, AuthUtil.getInstance().getUserID());

                if(success){
                    notificationToast.showNotification(AlertType.INFORMATION,"Password Updated", "Password updated successfully");
                    navigationController.navProfile();
                }
                else{
                    notificationToast.showNotification(AlertType.ERROR,"Process Failed", "There was a problem while updating the password");
                }
            }
       }
    }

    @FXML
    void updateProfile(MouseEvent event) {
        ValidationResult nameVal = Validator.validate(name.getText(), "not_null");
        ValidationResult emailVal = Validator.validate(email.getText(), "not_null","email");

        if(!nameVal.isSuccess() || !emailVal.isSuccess()) {
            error_name.setText(nameVal.getMessage());
            error_email.setText(emailVal.getMessage());
        }
        else{
            AppUser appUser = new AppUser(name.getText(),email.getText());
            boolean success = AppUserDAO.updateUser(appUser, AuthUtil.getInstance().getUserID());

            if(success){
                notificationToast.showNotification(AlertType.INFORMATION,"Profile Updated", "Profile updated successfully");
                navigationController.navProfile();

            }
            else{
                notificationToast.showNotification(AlertType.ERROR,"Process Failed", "There was a problem while updating the profile");
            }
        }
    }

    @FXML
    void initialize() {
        AppUser appUser = AppUserDAO.findUserById(AuthUtil.getInstance().getUserID());
        email.setText(appUser.getEmail().trim());
        name.setText(appUser.getName());
        this.oldPassword = appUser.getPassword();

    }

    @Override
    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

}
