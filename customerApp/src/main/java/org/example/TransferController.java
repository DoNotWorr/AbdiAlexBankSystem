package org.example;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;

public class TransferController {
    CustomerApp customerApp = null;

    @FXML
    ChoiceBox<Account> currentAccountsChoiceBox;

    @FXML
    RadioButton onLaterDate = null;

    @FXML
    RadioButton onCurrentDate = null;

    @FXML
    DatePicker datePicker = null;

    @FXML
    public void createTransaction() {
        //Om det gick att genomföra
        if (true) {
            //todo Lägg upp transaktion eller genomför direktöverföring

            //Byter scen och visar den scenen
            customerApp.primaryStage.setScene(customerApp.myScenes.get("mainScene"));
            customerApp.primaryStage.show();
        }
    }

    @FXML
    public void cancelTransaction() {
        //todo Töm fält i Transaktion
        //todo Lägg in nya transaktioner i employeeApp för att testa med

        //Byter scen och visar den scenen
        customerApp.primaryStage.setScene(customerApp.myScenes.get("mainScene"));
        customerApp.primaryStage.show();
    }

    public void fillListViewAccounts(ObservableList<Account> accounts) {
        //Fyller lista med överföringar
        currentAccountsChoiceBox.setItems(accounts);
    }
}
