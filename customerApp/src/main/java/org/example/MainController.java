package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;


import java.io.IOException;

public class MainController {
    CustomerApp customerApp = null;

    Button logout = null;

    @FXML
    public void logout() {
        customerApp.primaryStage.setScene(customerApp.myScenes.get("loginScene"));
        customerApp.primaryStage.show();
    }

}
