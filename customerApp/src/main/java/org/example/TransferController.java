package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class TransferController {
    CustomerApp customerApp = null;

    @FXML
    RadioButton onLaterDate = null;

    @FXML
    RadioButton onCurrentDate = null;

    @FXML
    public void createTransaction() {
        //Om det gick att genomföra
        if(true) {
            //Lägg upp transaktion eller genomför direktöverföring

            //och byt sedan tillbaka till mainScene
            customerApp.primaryStage.setScene(customerApp.myScenes.get("mainScene"));
            customerApp.primaryStage.show();
        }
    }

    @FXML
    public void cancelTransaction() {
        customerApp.primaryStage.setScene(customerApp.myScenes.get("mainScene"));
        customerApp.primaryStage.show();
    }


}
