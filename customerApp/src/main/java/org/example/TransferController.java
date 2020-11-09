package org.example;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class TransferController {
    CustomerApp customerApp = null;

    @FXML
    ListView<Account> currentAccountsListView;

    @FXML
    RadioButton onLaterDate = null;

    @FXML
    RadioButton onCurrentDate = null;

    @FXML
    public void createTransaction() {
        //Om det gick att genomföra
        if (true) {
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

    public void fillListViewAccounts(ObservableList<Account> accounts) {
        currentAccountsListView.setItems(accounts);
        //currentAccountsList.getItems().addAll(CustomerApp.getAccountsInCustomer(customer));
    }


}
