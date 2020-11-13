package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

        //Byter scen och visar den scenen
        customerApp.primaryStage.setScene(customerApp.myScenes.get("loginScene"));
        customerApp.primaryStage.show();
    }

    /**
     *
     */
    private void saveEverything() {
        CustomerApp.allCustomers = FileService.INSTANCE.loadCustomers();
        CustomerApp.allAccounts = FileService.INSTANCE.loadAccounts();
        CustomerApp.allTransfers = FileService.INSTANCE.loadTransfers();
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
        //todo Avbryt transaktion som är markerad i listan med transaktioner.
        currentTransfersListView.getSelectionModel().getSelectedItem().setStatus(Transfer.TransferStatus.CANCELLED);
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
        if(pendingTransfers.size() == 0) {
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
}
