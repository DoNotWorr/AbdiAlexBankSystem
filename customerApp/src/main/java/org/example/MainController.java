package org.example;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PopupControl;


import java.io.IOException;
import java.util.Objects;

public class MainController {
    CustomerApp customerApp = null;


    @FXML
    ListView<Account> currentAccountsListView;

    @FXML
    ListView<Transfer> currentTransfersListView;

    @FXML
    public void logout() {
        UserSession.getInstance().clearInstance();
        customerApp.primaryStage.setScene(customerApp.myScenes.get("loginScene"));
        customerApp.primaryStage.show();
    }

    @FXML
    public void createTransfer() {
        customerApp.primaryStage.setScene(customerApp.myScenes.get("transferScene"));
        customerApp.primaryStage.show();
    }

    public void fillListViewAccounts(ObservableList<Account> accounts) {
        currentAccountsListView.setItems(accounts);
        //currentAccountsList.getItems().addAll(CustomerApp.getAccountsInCustomer(customer));
    }

    public void fillListViewTransfers(ObservableList<Transfer> transfers) {
        currentTransfersListView.setItems(transfers);
        //currentAccountsList.getItems().addAll(CustomerApp.getAccountsInCustomer(customer));
    }
}
