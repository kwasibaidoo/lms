package com.lms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// import com.lms.controllers.NavigationController;
import com.lms.utils.DatabaseInitializer;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private DatabaseInitializer databaseInitializer = new DatabaseInitializer();

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 640, 480);
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        // authorisation and authentication check
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        App app = new App();
        app.databaseInitializer.initializeDatabase();
        launch();
    }

}