package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerApp extends Application {
    HashMap<String, Scene> myScenes = new HashMap<>();
    Stage primaryStage = null;

    public LoginController loginController = null;
    public MainController mainController = null;
    public TransferController transferController = null;

    public Customer currentCustomer = null;
    //public static ListView<Account> currentAccounts = new ListView<>(); //todo nuvarande försök


    public HashMap<String, Customer> allCustomers = FileService.INSTANCE.loadCustomers();
    public static HashMap<String, Account> allAccounts = FileService.INSTANCE.loadAccounts();
    public static ArrayList<Transfer> allTransfers = FileService.INSTANCE.loadTransfers();

    public static void main(String[] args) {
        launch();
    }

    /**
     * Använder struktur från Jons exempel för att skapa flera fönster
     *
     * @param primaryStage
     * @throws Exception
     * @author Alex
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Bank");

        //Login window
        FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent rootLogin = loaderLogin.load();
        loginController = loaderLogin.getController();
        loginController.customerApp = this;
        Scene loginScene = new Scene(rootLogin, 600, 300);
        myScenes.put("loginScene", loginScene);


        //Main window
        FXMLLoader loaderMain = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent rootMain = loaderMain.load();
        mainController = loaderMain.getController();
        //mainController.createListView(currentCustomer); //todo annat test
        mainController.customerApp = this;
        Scene mainScene = new Scene(rootMain, 300, 900);
        myScenes.put("mainScene", mainScene);

        //Transfer window
        FXMLLoader loaderTransfer = new FXMLLoader(getClass().getResource("/transfer.fxml"));
        Parent rootTransfer = loaderTransfer.load();
        transferController = loaderTransfer.getController();
        transferController.customerApp = this;
        Scene transferScene = new Scene(rootTransfer, 900, 900);
        myScenes.put("transferScene", transferScene);



        /*
        controller.button1.setText("Knapp");
        String cssh = "-fx-min-height: 50; -fx-pref-height: 50%; -fx-max-height: 350;";
        String cssw = "-fx-min-width: 50; -fx-pref-width: 50%; -fx-max-width: 350;";
        controller.button1.setStyle(cssh + cssw);
        */

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    //Kommenterat ut flera olika lösningar medan jag söker lösning
    public static ObservableList<Account> getAccountsInCustomer(Customer customer) {
        ObservableList<Account> currentAccounts = FXCollections.observableArrayList();
        for (Account account : allAccounts.values()) {
            if (account.getOwnerID().equals(customer.getOwnerID())) {
                currentAccounts.add(account);
            }
        }
        return currentAccounts;
    }

    public static ObservableList<Transfer> getTransfersInCustomer(Customer customer) {
        ObservableList<Transfer> currentTransfers = FXCollections.observableArrayList();

        for (Account account : allAccounts.values()) {
            if (account.getOwnerID().equals(customer.getOwnerID())) {
                for (Transfer transfer : allTransfers) {
                    if (transfer.getFromAccountNumber().equals(account.getAccountNumber())) {
                        currentTransfers.add(transfer);
                    }
                }
            }
        }
        return currentTransfers;
    }


}

