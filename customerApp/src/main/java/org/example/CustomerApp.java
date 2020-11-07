package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerApp extends Application {
    HashMap<String, Scene> myScenes = new HashMap<>();
    Stage primaryStage = null;

    public HashMap<String, Customer> allCustomers = FileService.INSTANCE.loadCustomers();
    public HashMap<String, Account> allAccounts = FileService.INSTANCE.loadAccounts();
    public ArrayList<Transfer> allTransfers = FileService.INSTANCE.loadTransfers();

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        // Sätter titeln på själva fönstret
        primaryStage.setTitle("Logga in");

        // Vi laddar in vår fxml-fil med exempelvis layouts och knappar
        FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/login.fxml"));
        FXMLLoader loaderMain = new FXMLLoader(getClass().getResource("/main.fxml"));
        FXMLLoader loaderTransfer = new FXMLLoader(getClass().getResource("/transfer.fxml"));


        // Ladda vår fxml-fil som Parent till vår Scene
        Parent rootLogin = loaderLogin.load();
        Parent rootMain = loaderMain.load();
        Parent rootTransfer = loaderTransfer.load();

        // Vi hämtar en referens till vår Controller-klass
        LoginController loginController = loaderLogin.getController();
        MainController mainController = loaderMain.getController();
        TransferController transferController = loaderTransfer.getController();


        loginController.customerApp = this;
        mainController.customerApp = this;
        transferController.customerApp = this;

        /*
        controller.button1.setText("Knapp");
        String cssh = "-fx-min-height: 50; -fx-pref-height: 50%; -fx-max-height: 350;";
        String cssw = "-fx-min-width: 50; -fx-pref-width: 50%; -fx-max-width: 350;";
        controller.button1.setStyle(cssh + cssw);
        */

        // Skapar en scene och ställer in dess storlek
        // Skickar även med  en referens till den Stage/ram som
        Scene loginScene = new Scene(rootLogin, 300, 300);
        Scene mainScene = new Scene(rootMain, 300, 900);
        Scene transferScene = new Scene(rootTransfer, 300, 300);
        myScenes.put("loginScene", loginScene);
        myScenes.put("mainScene", mainScene);
        myScenes.put("transferScene", transferScene);
        // Lägger vår Scene i fönstret.
        primaryStage.setScene(loginScene);

        // Visar fönstret
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }


   /* @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CustomerApp.class.getResource("/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }*/
}
