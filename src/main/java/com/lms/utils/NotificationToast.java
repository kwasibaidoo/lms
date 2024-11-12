package com.lms.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class NotificationToast {
    public void showNotification(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
