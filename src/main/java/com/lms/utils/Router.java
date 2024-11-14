package com.lms.utils;

import com.lms.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class Router {
    private VBox content;

    public Router(VBox content) {
        this.content = content;
    }

    public void loadPage(String pageName) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(pageName));
            
            Parent page = loader.load();
            content.getChildren().clear();
            content.getChildren().add(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
