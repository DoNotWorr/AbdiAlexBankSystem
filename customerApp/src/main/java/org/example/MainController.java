package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PopupControl;


import java.io.IOException;

public class MainController {
    CustomerApp customerApp = null;

    Button logout = null;

    @FXML
    public void logout() {
        customerApp.primaryStage.setScene(customerApp.myScenes.get("loginScene"));
        customerApp.primaryStage.show();
    }

    @FXML
    public void createTransfer() {
        customerApp.primaryStage.setScene(customerApp.myScenes.get("transferScene"));
        customerApp.primaryStage.show();
    }
}
