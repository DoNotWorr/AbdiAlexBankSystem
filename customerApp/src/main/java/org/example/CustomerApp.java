package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.DataClasses.Account;
import org.example.DataClasses.Customer;
import org.example.DataClasses.Transfer;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerApp extends Application {
    HashMap<String, Scene> myScenes = new HashMap<>();
    Stage primaryStage = null;

    public LoginController loginController = null;
    public MainController mainController = null;
    public TransferController transferController = null;

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

        //Abdi
        //Här använder jag mig av ett EventHandler för att kunna dra fönstret runt och det är för att jag gjorde fönstret transparent.
        double xOffset = 0, yOffset = 0;
        EventHandler<MouseEvent> draggedWindow = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        };


        primaryStage.setTitle("Newton First Bank");

        //Login window
        FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent rootLogin = loaderLogin.load();
        loginController = loaderLogin.getController();
        loginController.customerApp = this;
        Scene loginScene = new Scene(rootLogin, 330, 330);

        //Abdi
        //Här använder jag mig av metoden initStylke för att kunna ge bakgrunden ett transparent utseende
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(loginScene);
        loginScene.setFill(Color.TRANSPARENT);
        rootLogin.addEventHandler(MouseEvent.MOUSE_DRAGGED, draggedWindow);

        myScenes.put("loginScene", loginScene);


        //Main window
        FXMLLoader loaderMain = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent rootMain = loaderMain.load();
        mainController = loaderMain.getController();
        mainController.customerApp = this;
        Scene mainScene = new Scene(rootMain, 800, 520);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(mainScene);
        mainScene.setFill(Color.TRANSPARENT);
        rootMain.addEventHandler(MouseEvent.MOUSE_DRAGGED, draggedWindow);


        myScenes.put("mainScene", mainScene);


        //Transfer window
        FXMLLoader loaderTransfer = new FXMLLoader(getClass().getResource("/transfer.fxml"));
        Parent rootTransfer = loaderTransfer.load();
        transferController = loaderTransfer.getController();
        transferController.customerApp = this;
        Scene transferScene = new Scene(rootTransfer, 558, 419);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(transferScene);
        transferScene.setFill(Color.TRANSPARENT);
        rootTransfer.addEventHandler(MouseEvent.MOUSE_DRAGGED, draggedWindow);
        myScenes.put("transferScene", transferScene);

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

