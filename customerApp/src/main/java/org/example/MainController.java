package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.example.DataClasses.*;

import java.util.Comparator;

public class MainController {
    CustomerApp customerApp = null;

    @FXML
    ListView<Account> currentAccountsListView;

    //todo disable knappen removeTransfer innan man har valt något
    @FXML
    ListView<Transfer> currentTransfersListView;

    @FXML
    Button addTransfer;

    @FXML
    Button removeTransfer;

    @FXML
    public void logout() {
        //Sparar alla ändringar
        saveEverything();

        //Tömmer användarsession
        UserSession.getInstance().clearInstance();

        // Rensar personnummer och lösenord efter man har loggat ut
        customerApp.loginController.deleteUser();

        //Byter scen och visar den scenen
        customerApp.primaryStage.setScene(customerApp.myScenes.get("loginScene"));
        customerApp.primaryStage.show();
    }

    /**
     *
     */
    private void saveEverything() {
        FileService.INSTANCE.saveCustomers(CustomerApp.allCustomers);
        FileService.INSTANCE.saveAccounts(CustomerApp.allAccounts);
        FileService.INSTANCE.saveTransfers(CustomerApp.allTransfers);
    }

    @FXML
    public void createTransfer() {
        //Fyller listor i transfer-fönstret med innehåll från UserSession
        customerApp.transferController.updateAccounts(UserSession.getInstance().getAccounts());

        //Ställer in valen i fönstret till standard
        customerApp.transferController.setDefaultFields();

        //Byter scen och visar den scenen
        customerApp.primaryStage.setScene(customerApp.myScenes.get("transferScene"));
        customerApp.primaryStage.show();
    }

    @FXML
    public void removeTransfer() {
        //Om en transaktion är vald, dvs "...getSelectedItem != null"
        if (currentTransfersListView.getSelectionModel().getSelectedItem() != null) {
            //Sätter status som CANCELLED om det går.
            if (currentTransfersListView.getSelectionModel().getSelectedItem().setStatus(Transfer.TransferStatus.CANCELLED)) { //I den nuvarande versionen är if-satsen överflödig eftersom listan visar enbart PENDING och det alltid går att byta från PENDING till CANCELLED
                updateTransfers(UserSession.getInstance().getTransfers());
            }
        } else {
            //todo lägg till label errorMsgSelectedTransfer med text "Välj en transaktion"
        }

    }

    /**
     * Fyller lista med innehåll
     *
     * @param accounts konton som ska visas
     */
    public void updateAccounts(ObservableList<Account> accounts) {
        //Fyller lista med konton
        currentAccountsListView.setItems(accounts);
        //enligt dokumentation ska setItems() automatiskt göra en refresh(), men en manuell refresh() verkar krävas ändå
        currentAccountsListView.refresh();
    }

    /**
     * Fyller lista med innehåll
     *
     * @param transfers lista som ska visas
     */
    public void updateTransfers(ObservableList<Transfer> transfers) {
        //Skapar ObservableList med transfers som är pågående.
        ObservableList<Transfer> pendingTransfers = FXCollections.observableArrayList();
        for (Transfer transfer : transfers) {
            if (transfer.getStatus() == Transfer.TransferStatus.PENDING) {
                pendingTransfers.add(transfer);
            }
        }

        //Om listan med pågående transfers är tom
        if (pendingTransfers.size() == 0) {
            //Går inte att trycka på "Ta bort transaktion"
            removeTransfer.setDisable(true);
        } else {
            //Går att trycka på "Ta bort transaktion"
            removeTransfer.setDisable(false);
        }

        //Sorterar listan på datum
        pendingTransfers.sort(Comparator.comparing(Transfer::getTransferDate));

        //Fyller lista med överföringar
        currentTransfersListView.setItems(pendingTransfers);
        //enligt dokumentation ska setItems() automatiskt göra en refresh(), men en manuell refresh() verkar krävas ändå
        currentTransfersListView.refresh();
    }

    /**
     * author Abdi
     */
    @FXML
    public void logOutAndQuit() {
        //Sparar alla ändringar
        saveEverything();

        System.exit(0);
    }
}
