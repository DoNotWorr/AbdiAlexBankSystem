package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerApp extends Application {
    HashMap<String, Scene> myScenes = new HashMap<>();
    Stage primaryStage = null;

    Customer currentCustomer = null;
    ListView<Account> currentAccounts = null;

    public HashMap<String, Customer> allCustomers = FileService.INSTANCE.loadCustomers();
    public HashMap<String, Account> allAccounts = FileService.INSTANCE.loadAccounts();
    public ArrayList<Transfer> allTransfers = FileService.INSTANCE.loadTransfers();

    /**
     * Använder struktur från Jons exempel för att skapa flera fönster
     * @author Alex
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Bank");

        //Login window
        FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent rootLogin = loaderLogin.load();
        LoginController loginController = loaderLogin.getController();
        loginController.customerApp = this;
        Scene loginScene = new Scene(rootLogin, 300, 300);
        myScenes.put("loginScene", loginScene);

        //Main window
        FXMLLoader loaderMain = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent rootMain = loaderMain.load();
        MainController mainController = loaderMain.getController();
        mainController.customerApp = this;
        Scene mainScene = new Scene(rootMain, 300, 900);
        myScenes.put("mainScene", mainScene);

        //Transfer window
        FXMLLoader loaderTransfer = new FXMLLoader(getClass().getResource("/transfer.fxml"));
        Parent rootTransfer = loaderTransfer.load();
        TransferController transferController = loaderTransfer.getController();
        transferController.customerApp = this;
        Scene transferScene = new Scene(rootTransfer, 300, 300);
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

    public static void main(String[] args) {
        launch();
    }
}
