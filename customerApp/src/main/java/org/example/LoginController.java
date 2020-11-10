package org.example;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;


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
            UserSession.setInstance(customerApp.allCustomers.get(textField.getText()));

            //Hämtar
            customerApp.mainController.fillListViewAccounts(UserSession.getInstance().getAccounts());
            customerApp.mainController.fillListViewTransfers(UserSession.getInstance().getTransfers());

            customerApp.transferController.fillListViewAccounts(UserSession.getInstance().getAccounts());

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
