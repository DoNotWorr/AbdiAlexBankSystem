package org.example;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Alex och Abdi
 * Se kommentera på varje enskild metod
 */
public class LoginController {
    CustomerApp customerApp = null;

    @FXML
    TextField textField = null;

    @FXML
    PasswordField password = null;

    @FXML
    Label loginErrorMsg = null;

    /**
     * @author Alex
     * Läser från login-fönstret och loggar in användare om personnr/password stämmer. Vid inloggning fylls nästa fönster med data från den inloggade användaren
     */
    @FXML
    public void login() {
        //Nollställer eventuellt felmeddelande
        loginErrorMsg.setText("");

        //Om angivet personnummer finns i hashmap och lösenord stämmer överens. Lösenord är hashade med DigestUtils.sha256hex() så kör det inmatade lösenordet i samma metod.
        if (CustomerApp.allCustomers.containsKey(textField.getText()) && CustomerApp.allCustomers.get(textField.getText()).getPasswordHash().equals(DigestUtils.sha256Hex(password.getText()))) {

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
        } else {
            password.setText("");
            loginErrorMsg.setText("Felaktiga uppgifter");
        }
    }

    /**
     * @author Abdi
     * Skapade en metod som rensar från personnummret och lösenordet efter man har loggat ut.
     */
    public void setDefaultFields() {
        textField.setText("yyyymmdd-xxxx");
        password.clear();
    }

    /**
     * metod för att kunna avsluta programmet. Eftersom primaryStage är transparent så finns inte den vanliga knappen för att avsluta programmet
     *
     * @author Abdi
     */
    public void btn_exit() {
        System.exit(0);
    }
}
