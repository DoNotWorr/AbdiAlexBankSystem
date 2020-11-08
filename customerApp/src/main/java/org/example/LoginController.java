package org.example;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginController {
    CustomerApp customerApp = null;

    @FXML
    Label labelOwnerID = null;
    @FXML
    Label labelPassword = null;
    @FXML
    TextField textField = null;
    @FXML
    PasswordField passwordFieldPassword = null;


    @FXML
    Button loginButton = null;

    @FXML
    public void login(Event e) {
        if (customerApp.allCustomers.containsKey(textField.getText())) {
            //customerApp.currentCustomer = customerApp.allCustomers.get(textField.getText());

            //customerApp.yourAccounts

            customerApp.primaryStage.setScene(customerApp.myScenes.get("mainScene"));
            customerApp.primaryStage.show();
        }

    }


}
