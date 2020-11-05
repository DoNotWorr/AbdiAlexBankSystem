package org.example;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LoginController {
    CustomerApp customerApp = null;

    @FXML
    Label labelOwnerID = null;
    @FXML
    Label labelPassword = null;
    @FXML
    TextField textFieldOwnerID = null;
    @FXML
    PasswordField passwordFieldPassword = null;


    @FXML
    Button loginButton = null;
    @FXML
    public void login(Event e) {
        customerApp.primaryStage.setScene(customerApp.myScenes.get("mainScene"));
        customerApp.primaryStage.show();
    }



}
