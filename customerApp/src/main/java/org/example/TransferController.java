package org.example;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.Objects;

public class TransferController {
    CustomerApp customerApp = null;

    @FXML
    ChoiceBox<Account> fromAccountList;

    @FXML
    TextField toAccountNumber;

    @FXML
    TextField amountSek;

    @FXML
    ToggleGroup whenTransaction;

    @FXML
    RadioButton onCurrentDate = null;

    @FXML
    RadioButton onLaterDate = null;

    @FXML
    DatePicker laterDatePicker = null;

    @FXML
    public void addTransaction() {
        Account fromAccount = fromAccountList.getValue();

        Account toAccount = CustomerApp.allAccounts.get(toAccountNumber.getText());
        long amountCent;
        //todo Fortsätt när UnitConversion.convertFromSek() tar emot en String istället för en double


        //todo fortsätt när Account.addTransfer() kaster exception, så man slipper nedanstående kod
        /*if(Objects.isNull(toAccount)) {

        }*/

        //Todo gör sedan färdigt så den skapar en transfer
        /*if(onCurrentDate.isSelected()) {
            //skapa direktöverföring
            fromAccount.directTransfer(toAccount, )
        } else if (onLaterDate.isSelected()) { //todo Är det möjligt att ingen RadioButton är selected?
            //skapa bankuppdrag
            fromAccount.addTransfer()
        }*/

        if (true) {
            //todo Lägg upp transaktion eller genomför direktöverföring

            //Byter scen och visar den scenen
            customerApp.primaryStage.setScene(customerApp.myScenes.get("mainScene"));
            customerApp.primaryStage.show();
        }
    }

    @FXML
    public void leaveTransaction() {
        //Ställer in valen i fönstret till standard
        setDefaultFields();

        //Byter scen och visar den scenen
        customerApp.primaryStage.setScene(customerApp.myScenes.get("mainScene"));
        customerApp.primaryStage.show();
    }

    public void fillListViewAccounts(ObservableList<Account> accounts) {
        //Fyller lista med överföringar
        fromAccountList.setItems(accounts);
    }

    public void setDefaultFields() {
        //Ställer ChoiceBox fromAccount till första kontot i listan
        fromAccountList.setValue(UserSession.getInstance().getAccounts().get(0));

        //Tömmer TextField toAccount
        toAccountNumber.setText("");

        //Väljer RadioButton till "omgående"
        setInstant();
    }

    public void isFutureDate() {
        //Kontrollera att valt datum inte är dagens datum eller tidigare
    }

    public void setInstant() {
        //Välj dagens datum i datepicker
        laterDatePicker.setValue(LocalDate.now());

        // disable datepicker så man inte kan välja ett datum (gör den grå)
        laterDatePicker.setDisable(true);

        //Välj RadioButton "omgående"
        onCurrentDate.setSelected(true);

    }

    public void setDelayed() {
        //https://stackoverflow.com/questions/48238855/how-to-disable-past-dates-in-datepicker-of-javafx-scene-builder
        //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/DatePicker.html -> setDayCellFactory
        laterDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate tomorrow = LocalDate.now().plusDays(1);
                setDisable(empty || date.compareTo(tomorrow) < 0);
            }
        });

        //Välj morgondagens datum
        laterDatePicker.setValue(LocalDate.now().plusDays(1));

        //Enable datepicker (gör så man kan använda den)
        laterDatePicker.setDisable(false);

        //Välj RadioButton "på datum"
        onLaterDate.setSelected(true);
    }
}
