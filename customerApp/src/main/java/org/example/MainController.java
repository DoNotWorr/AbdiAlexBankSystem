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
    public void logout() {
        //System.out.println(currentAccounts.getItems().get(0).getOwnerID()); //test för att se om currentAccounts visas todo fixa så currentAccounts visas när man loggar in
        customerApp.primaryStage.setScene(customerApp.myScenes.get("loginScene"));
        customerApp.primaryStage.show();
    }

    @FXML
    public void createTransfer() {
        customerApp.primaryStage.setScene(customerApp.myScenes.get("transferScene"));
        customerApp.primaryStage.show();
    }

    public void createListView(Customer customer) {
        ObservableList<Account> accountsInCustomer = customerApp.getAccountsInCustomer(customerApp.currentCustomer);
        if(Objects.isNull(currentAccountsListView)) {
            System.out.println("currentAccountsListView är null");
        } else {
            currentAccountsListView.setItems(accountsInCustomer);
        }
        //currentAccountsList.getItems().addAll(CustomerApp.getAccountsInCustomer(customer));
    }
}
