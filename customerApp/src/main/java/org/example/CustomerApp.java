package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class CustomerApp extends Application {
    //private static Scene scene;


    HashMap<String, Scene> myScenes = new HashMap<>();
    Stage primaryStage = null;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        // Sätter titeln på själva fönstret
        primaryStage.setTitle("Logga in");

        // Vi laddar in vår fxml-fil med exempelvis layouts och knappar
        FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/login.fxml"));
        FXMLLoader loaderMain = new FXMLLoader(getClass().getResource("/main.fxml"));


        // Ladda vår fxml-fil som Parent till vår Scene
        Parent rootLogin = loaderLogin.load();
        Parent rootMain = loaderMain.load();

        // Vi hämtar en referens till vår Controller-klass
        LoginController loginController = loaderLogin.getController();
        MainController mainController = loaderMain.getController();
        loginController.customerApp = this;
        mainController.customerApp = this;

        /*
        controller.button1.setText("Knapp");
        String cssh = "-fx-min-height: 50; -fx-pref-height: 50%; -fx-max-height: 350;";
        String cssw = "-fx-min-width: 50; -fx-pref-width: 50%; -fx-max-width: 350;";
        controller.button1.setStyle(cssh + cssw);
        */

        // Skapar en scene och ställer in dess storlek
        // Skickar även med  en referens till den Stage/ram som
        Scene loginScene = new Scene(rootLogin, 300, 300);
        Scene mainScene = new Scene(rootMain, 600, 600);
        myScenes.put("loginScene", loginScene);
        myScenes.put("mainScene", mainScene);
        // Lägger vår Scene i fönstret.
        primaryStage.setScene(loginScene);

        // Visar fönstret
        primaryStage.show();
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
