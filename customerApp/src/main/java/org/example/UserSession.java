package org.example;

import javafx.collections.ObservableList;
import org.example.DataClasses.Account;
import org.example.DataClasses.Customer;
import org.example.DataClasses.Transfer;

public class UserSession {
    //private för att man inte ska kunna göra otillåtna ändringar i instansen
    private static UserSession instance;
    private Customer customer;
    private ObservableList<Account> accounts;
    private ObservableList<Transfer> transfers;


    /**
     * Skapar en användarsession. Private för att man ska vara tvungen att använda setInstance()
     *
     * @param customer the customer in this usersession
     * @author Alex
     * Tog info ifrån: https://stackoverflow.com/questions/46508098/how-to-keep-user-information-after-login-in-javafx-desktop-application
     */
    private UserSession(Customer customer, ObservableList<Account> accounts, ObservableList<Transfer> transfers) {
        this.customer = customer;
        this.accounts = accounts;
        this.transfers = transfers;
    }

    /**
     * Skapar UserSession. Om det redan finns en inloggad användare så går det inte att skapa en ny UserSession utan den gamla returneras.
     *
     * @param customer användaren som loggat in
     * @return instans av den inloggade användaren
     */
    public static UserSession setInstance(Customer customer) {
        if (instance == null) {
            instance = new UserSession(customer, CustomerApp.getAccountsInCustomer(customer), CustomerApp.getTransfersInCustomer(customer));
        }
        return instance;
    }

    /**
     * Returnerar den nuvarande instansen
     *
     * @return Den inloggade användarens UserSession. Om det inte finns någon inloggad användare returneras null
     */
    public static UserSession getInstance() {
        return instance;
    }

    /**
     * Nollställer den inloggade användarens UserSession. Objekt som ändrats under UserSession behåller sina ändringar. Observera att om man lägger till ett objekt, till exempel en ny transfer, så behöver man även lägga till den i lista med alla transfers.
     */
    public void clearInstance() {
        instance = null;
    }

    /**
     * Getter. Används inte för tillfället men kan vara bra om man till exempel vill skriva ut den inloggade användarens personuppgifter någonstans.
     *
     * @return den inloggande användarens Customer-objekt
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Getter för lista med konton
     *
     * @return lista med konton
     */
    public ObservableList<Account> getAccounts() {
        return accounts;
    }

    /**
     * Getter för lista med transfers
     *
     * @return lista med transfers
     */
    public ObservableList<Transfer> getTransfers() {
        return transfers;
    }
}


