
package org.example;

import javafx.collections.ObservableList;
import org.example.DataClasses.Account;
import org.example.DataClasses.Customer;
import org.example.DataClasses.Transfer;

//todo final?
public class UserSession {
    private static UserSession instance;

    private Customer customer;
    private ObservableList<Account> accounts;
    private ObservableList<Transfer> transfers;


    /**
     * https://stackoverflow.com/questions/46508098/how-to-keep-user-information-after-login-in-javafx-desktop-application
     *
     * @param customer the customer in this usersession
     */


    private UserSession(Customer customer, ObservableList<Account> accounts, ObservableList<Transfer> transfers) {
        //todo validering att inlogg är korrekt?
        this.customer = customer;
        this.accounts = accounts;
        this.transfers = transfers;
    }

    public static UserSession setInstance(Customer customer) {
        if (instance == null) {
            instance = new UserSession(customer, CustomerApp.getAccountsInCustomer(customer), CustomerApp.getTransfersInCustomer(customer));
        }
        return instance;
    }

    public static UserSession getInstance() {
        return instance;
    }

    public void clearInstance() {
        instance = null;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ObservableList<Account> getAccounts() {
        return accounts;
    }

    public ObservableList<Transfer> getTransfers() {
        return transfers;
    }
}


