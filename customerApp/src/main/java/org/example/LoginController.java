package org.example;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sun.rmi.rmic.Main;

import java.io.IOException;


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
            //Skapar användarsession
            customerApp.currentCustomer = customerApp.allCustomers.get(textField.getText());
            //
            customerApp.mainController.createListView(customerApp.currentCustomer);

            customerApp.primaryStage.setScene(customerApp.myScenes.get("mainScene"));
            customerApp.primaryStage.show();
        }

    }

    /*
    //Kommenterat ut flera olika lösningar medan jag söker lösning
    public ListView<Account> getCurrentAccounts(Customer customer) {
        ListView<Account> currentAccounts = new ListView<>();
        for (Account account : customerApp.allAccounts.values()) {
            if (account.getOwnerID().equals(customer.getOwnerID())) {
                currentAccounts.getItems().add(account);
            }
        }
        return currentAccounts;
    }
     */
}
