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
        //Tömmer användarsession
        UserSession.getInstance().clearInstance();

        //Byter scen och visar den scenen
        customerApp.primaryStage.setScene(customerApp.myScenes.get("loginScene"));
        customerApp.primaryStage.show();
    }

    @FXML
    public void createTransfer() {
        //Byter scen och visar den scenen
        customerApp.primaryStage.setScene(customerApp.myScenes.get("transferScene"));
        customerApp.primaryStage.show();
    }

    @FXML
    public void removeTransfer() {
        //todo Avbryt transaktion som är markerad i listan med transaktioner
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
