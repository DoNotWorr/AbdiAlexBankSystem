package org.example;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;


public class LoginController {
    CustomerApp customerApp = null;

    @FXML
    TextField textField = null;

    @FXML
    public void login(Event e) {
        //Om angivet personnummer stämmer överens med existerande användare
        if (customerApp.allCustomers.containsKey(textField.getText())) {
            //Skapar användarsession
            UserSession.setInstance(customerApp.allCustomers.get(textField.getText()));

            //Fyller listor i main-fönstret med innehåll från UserSession
            customerApp.mainController.fillListViewAccounts(UserSession.getInstance().getAccounts());
            customerApp.mainController.fillListViewTransfers(UserSession.getInstance().getTransfers());

            //Fyller listor i transfer-fönstret med innehåll från UserSession
            customerApp.transferController.fillListViewAccounts(UserSession.getInstance().getAccounts());

            //Byter scen och visar den scenen
            customerApp.primaryStage.setScene(customerApp.myScenes.get("mainScene"));
            customerApp.primaryStage.show();
        }
    }

    /**
     * @param mouseEvent Eftersom jag gjorde bakgrunden transparent så behöver jag skapa mustryckning för att kunna avsluta fönstret.
     * @author Abdi
     */
    public void btn_exit(MouseEvent mouseEvent) {
        System.exit(0);
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
