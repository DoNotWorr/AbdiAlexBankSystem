package org.example;

import javafx.collections.ListChangeListener;
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
        if (CustomerApp.allCustomers.containsKey(textField.getText())) {
            //Skapar användarsession
            UserSession.setInstance(CustomerApp.allCustomers.get(textField.getText()));

            //Uppdaterar listor i main-fönstret med innehåll från UserSession
            customerApp.mainController.updateAccounts(UserSession.getInstance().getAccounts());
            customerApp.mainController.updateTransfers(UserSession.getInstance().getTransfers());

            //Om listan med användarens konton har storlek 0
            if (UserSession.getInstance().getAccounts().size() == 0) {
                //Det går inte att trycka på knappen "Skapa ny transaktion"
                customerApp.mainController.addTransfer.setDisable(true);
            } else {
                //Det går att trycka på knapparna "Skapa ny transaktion"
                customerApp.mainController.addTransfer.setDisable(false);
                //Fyller listor i transfer-fönstret med innehåll från UserSession
                customerApp.transferController.updateAccounts(UserSession.getInstance().getAccounts());
            }

            //Byter scen och visar den scenen
            customerApp.primaryStage.setScene(customerApp.myScenes.get("mainScene"));
            customerApp.primaryStage.show();
        }
    }

    /**
     * Skapade en metod som rensar från personnummret och lösenordet efter man har loggat ut.
     */
    public void deleteUser() {
        textField.clear();
    }

    /**
     * @param mouseEvent Eftersom jag gjorde bakgrunden transparent så behöver jag skapa mustryckning för att kunna avsluta fönstret.
     * @author Abdi
     */
    public void btn_exit(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
