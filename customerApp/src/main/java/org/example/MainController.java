package org.example;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.util.Objects;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;

public class MainController {
    CustomerApp customerApp = null;

    @FXML
    private ImageView imageView;
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
    public void mainBtn_exit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    @FXML
    public void removeAccount(MouseEvent mouseevent) {
        int selectedIdx = 0;
        if (selectedIdx == currentAccountsListView.getItems().size()) {
            currentAccountsListView.getItems().remove(this.currentAccountsListView.getItems().size() - 1);

        }
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
