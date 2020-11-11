package org.example;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.io.IOException;
import java.util.Objects;

public class MainController {
    CustomerApp customerApp = null;


    @FXML
    ListView<Account> currentAccountsListView;

    //todo disable knappen removeTransfer innan man har valt något
    @FXML
    ListView<Transfer> currentTransfersListView;

    @FXML
    Button removeTransfer;

    @FXML
    public void logout() {
        //Tömmer användarsession
        UserSession.getInstance().clearInstance();

        //Byter scen och visar den scenen
        customerApp.primaryStage.setScene(customerApp.myScenes.get("loginScene"));
        customerApp.primaryStage.show();
    }

    @FXML
    public void createTransfer() {
        customerApp.transferController.setDefaultFields();

        //Byter scen och visar den scenen
        customerApp.primaryStage.setScene(customerApp.myScenes.get("transferScene"));
        customerApp.primaryStage.show();
    }

    @FXML
    public void removeTransfer() {
        //todo Avbryt transaktion som är markerad i listan med transaktioner.
        currentTransfersListView.getSelectionModel().getSelectedItem().setStatus(Transfer.TransferStatus.CANCELLED);
    }

    public void fillListViewAccounts(ObservableList<Account> accounts) {
        //Fyller lista med konton
        currentAccountsListView.setItems(accounts);
    }

    public void fillListViewTransfers(ObservableList<Transfer> transfers) {
        //Fyller lista med överföringar
        currentTransfersListView.setItems(transfers);
    }
}
