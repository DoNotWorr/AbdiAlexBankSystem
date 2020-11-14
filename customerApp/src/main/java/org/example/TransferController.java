package org.example;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.DataClasses.Account;
import org.example.DataClasses.Transfer;
import org.example.Exceptions.*;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Abdi, Alex
 * Se tagg på varje metod
 */
public class TransferController {
    CustomerApp customerApp = null;

    @FXML
    ListView<Account> fromAccountList;

    @FXML
    TextField toAccountNumber;

    @FXML
    Label errorMsgToAccount;

    @FXML
    TextField amountSek;

    @FXML
    Label errorMsgAmount;

    @FXML
    ToggleGroup whenTransaction;

    @FXML
    RadioButton onCurrentDate;

    @FXML
    RadioButton onLaterDate = null;

    @FXML
    DatePicker laterDatePicker = null;

    /**
     * Läser inmatad info och skapar transaktion om det är möjligt. Byter sedan till mainScene. Om det inte är möjligt skrivs istället lämpligt felmeddelande ut och användaren får chans att korrigera.
     *
     * @author Alex, Abdi
     * Alex skrev ursprungliga metoden. Abdi och Alex fixade buggar tillsammans så rätt felmeddelande skrivs ut.
     */
    @FXML
    public void addTransaction() {
        //Tar bort eventuell text i felmeddelande
        errorMsgAmount.setText("");
        errorMsgToAccount.setText("");

        Account fromAccount = fromAccountList.getSelectionModel().getSelectedItem();
        Account toAccount = CustomerApp.allAccounts.get(toAccountNumber.getText());
        String amountInput = amountSek.getText();
        long amountCent;

        try {
            if (onCurrentDate.isSelected()) {
                if (Objects.isNull(toAccount)) {
                    errorMsgToAccount.setText("Hittade ingen mottagare");
                    return; //La till return. Annars så visades fel felmeddelande eftersom metoden körde vidare /Abdi
                }
                amountCent = UnitConversion.convertFromSek(amountInput);
                //skapa direktöverföring (genomförs direkt)
                if (fromAccount.directTransfer(toAccount, amountCent) == false) {
                    if (amountCent > fromAccount.getBalance()) {
                        errorMsgAmount.setText("För lågt saldo");
                    } else {
                        errorMsgToAccount.setText("Samma konto som du skickar ifrån");
                    }
                    return;
                }
            } else if (onLaterDate.isSelected()) {
                //skapa bankuppdrag och lägg i lista
                Transfer brandNewTransfer = fromAccount.addTransfer(toAccount, UnitConversion.convertFromSek(amountInput), laterDatePicker.getValue());
                CustomerApp.allTransfers.add(brandNewTransfer);
                UserSession.getInstance().getTransfers().add(brandNewTransfer);
            }
        } catch (NonNumericalException e) {
            errorMsgAmount.setText("Fyll i korrekt belopp");
            return;
        } catch (NumberNotInBoundsException e) {
            errorMsgAmount.setText("Fyll i korrekt belopp");
            return;
        } catch (NullToAccountException e) {
            errorMsgToAccount.setText("Hittade ingen mottagare");
            return;
        } catch (NotLaterDateException e) {
            e.printStackTrace(); //Kan inte uppstå
            return;
        } catch (TooSmallNumberException e) {
            errorMsgAmount.setText("Fyll i giltigt belopp");
            return;
        } catch (SameAccountException e) {
            errorMsgToAccount.setText("Samma konto som du skickar ifrån");
            return;
        }

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
     * @author Alex
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
     * @author Alex
     */
    public void updateAccounts(ObservableList<Account> accounts) {
        //Fyller lista med överföringar
        fromAccountList.setItems(accounts);

        //Uppdaterar lista
        fromAccountList.refresh();
    }

    /**
     * Återställer standardvärden i transfer-fönster.
     *
     * @author Alex
     */
    public void setDefaultFields() {
        //Ställer ChoiceBox fromAccount till första kontot i listan
        fromAccountList.getSelectionModel().selectFirst();

        //Tömmer TextField toAccount
        toAccountNumber.setText("");

        //Tömmer TextField amountSek
        amountSek.setText("");

        //Tömmer felmeddelanden
        errorMsgAmount.setText("");
        errorMsgToAccount.setText("");

        //Väljer RadioButton "omgående"
        setInstant();
    }

    /**
     * När man väljer "omgående" så går det inte att välja ett datum. För att det ska bli tydligt för användaren så visar datumväljaren dagens datum.
     *
     * @author Alex
     */
    public void setInstant() {
        //Välj dagens datum i datepicker
        laterDatePicker.setValue(LocalDate.now());

        // disable datepicker så man inte kan välja ett datum (gör den grå)
        laterDatePicker.setDisable(true);

        //Välj RadioButton "omgående".
        onCurrentDate.setSelected(true);
    }

    /**
     * Om man väljer "på datum" så tillgängliggörs datepicker, men ogiltiga datum går inte att trycka på
     *
     * @author Alex
     */
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
