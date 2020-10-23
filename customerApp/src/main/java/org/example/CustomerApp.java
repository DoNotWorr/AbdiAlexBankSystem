package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class CustomerApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Logga in");
        primaryStage.setScene(new Scene(root, 400.0, 600.0));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
