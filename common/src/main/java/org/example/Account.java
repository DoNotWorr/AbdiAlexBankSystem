package org.example;

import java.util.ArrayList;

/**
 * @author Abdi
 *
 */

public class Account {

    private String accountNumber;
    private String ownerID;
    private int balance;


    public Account(String accountNumber, Customer customer, int balance){
        this.accountNumber = accountNumber;
        customer.addNewAccount(accountNumber); //Alex: Lägger nytt kontonummer i kundens lista med kontonummer
        this.ownerID = customer.getOwnerID();
        this.balance = balance;
    }

public String getAccountNumber(){
    return accountNumber;
}
public String getOwnerID(){
        return ownerID;
}
public int getBalance(){
        return balance;
}

public void printAccount(ArrayList<String> allAccounts) {
    //for loop som går igenom alla konton
    //printar ut info som vi vill printa ut för varje konto
}

}