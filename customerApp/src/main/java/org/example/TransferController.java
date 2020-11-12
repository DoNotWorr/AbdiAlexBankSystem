package org.example;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

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

    /**
     * Läser inmatad info och skapar transaktion om det är möjligt.
     */
    @FXML
    public void addTransaction() {
        Account fromAccount = fromAccountList.getValue();
        Account toAccount = CustomerApp.allAccounts.get(toAccountNumber.getText());
        String amountInput = amountSek.getText();

        //todo ta bort testprints
        System.out.println("Före:");
        System.out.println("Konto-instans: " + fromAccount.getBalance());
        System.out.println("UserSession konto-instans: " + UserSession.getInstance().getAccounts().get(0).getBalance());
        System.out.println("Konto i customerApp.allAccounts: " + CustomerApp.allAccounts.get(fromAccount.getAccountNumber()).getBalance());


        try {
            if (onCurrentDate.isSelected()) {
                //skapa direktöverföring (genomförs direkt)
                //todo toAccount kan vara null, uppdatera directTransfer
                fromAccount.directTransfer(toAccount, UnitConversion.convertFromSek(amountInput));
            } else if (onLaterDate.isSelected()) {
                //skapa bankuppdrag och lägg i lista
                Transfer brandNewTransfer = fromAccount.addTransfer(toAccount, UnitConversion.convertFromSek(amountInput), laterDatePicker.getValue());
                CustomerApp.allTransfers.add(brandNewTransfer);
                UserSession.getInstance().getTransfers().add(brandNewTransfer);
            }
        } catch (NonNumericalException e) {
            e.printStackTrace();
            return;
        } catch (NumberNotInBoundsException e) {
            e.printStackTrace();
            return;
        } catch (NullToAccountException e) {
            e.printStackTrace();
            return;
        } catch (NotLaterDateException e) {
            e.printStackTrace();
            return;
        }
        //todo info till användaren om vad som blev fel, istället för att återställa fälten och byta tillbaka till mainScene

        //todo ta bort testprints
        System.out.println("Efter:");
        System.out.println("Konto-instans: " + fromAccount.getBalance());
        System.out.println("UserSession konto-instans: " + UserSession.getInstance().getAccounts().get(0).getBalance());
        System.out.println("Konto i customerApp.allAccounts: " + CustomerApp.allAccounts.get(fromAccount.getAccountNumber()).getBalance());
        System.out.println("Konto i ChoiceBox: " + fromAccountList.getValue().getBalance());

        //Ställer in valen i fönstret till standard
        setDefaultFields();

        //Läser accounts på nytt för att uppdatera listor i mainScene
        customerApp.mainController.updateAccounts(UserSession.getInstance().getAccounts());

        //Läser transfers på nytt för att uppdatera listor i mainScene
        customerApp.mainController.updateTransfers(UserSession.getInstance().getTransfers());

        //Byter scen och visar den scenen
        customerApp.primaryStage.setScene(customerApp.myScenes.get("mainScene"));
        customerApp.primaryStage.show();
    }

    /**
     * Tömmer alla fält och byter till mainScene
     */
    @FXML
    public void leaveTransaction() {
        //Ställer in valen i fönstret till standard
        setDefaultFields();

        //Byter scen och visar den scenen
        customerApp.primaryStage.setScene(customerApp.myScenes.get("mainScene"));
        customerApp.primaryStage.show();
    }

    /**
     * Fyller scrollista med konton som man kan välja i transfer-fönster
     *
     * @param accounts konton som ska visas
     */
    public void updateAccounts(ObservableList<Account> accounts) {
        //Fyller lista med överföringar

        fromAccountList.setItems(accounts);

        //todo behöver uppdatera listan?

    }

    /**
     * Återställer standardvärden i transfer-fönster
     */
    public void setDefaultFields() {
        //Ställer ChoiceBox fromAccount till första kontot i listan
        fromAccountList.setValue(UserSession.getInstance().getAccounts().get(0));

        //Tömmer TextField toAccount
        toAccountNumber.setText("");

        //Tömmer TextField amountSek
        amountSek.setText("");

        //Väljer RadioButton till "omgående"
        setInstant();
    }

    /**
     *
     */
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
