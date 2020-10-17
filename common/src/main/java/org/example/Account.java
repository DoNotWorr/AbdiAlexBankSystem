package org.example;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Abdi
 */

public class Account {

    private String accountNumber;
    private String ownerID;
    private int balance;


    public Account(String accountNumber, Customer customer, int balance) {
        this.accountNumber = accountNumber;
        customer.addNewAccount(accountNumber); //Alex: Lägger nytt kontonummer i kundens lista med kontonummer
        this.ownerID = customer.getOwnerID();
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public int getBalance() {
        return balance;
    }

    /**
     * Metod för att skapa bankuppdrag.
     * @Author Alex
     * @param toAccount kontonummer betalning ska ske till
     * @param amount antal ören (inte SEK)
     * @param transferDate datum i format YYYY-MM-DD för överföringen
     */
    public void addTransfer(String toAccount, int amount, LocalDate transferDate) {
        Transfer transfer = new Transfer(this.accountNumber, toAccount, amount, transferDate);
    }

    public void printAccount(ArrayList<String> allAccounts) {
        //for loop som går igenom alla konton
        //printar ut info som vi vill printa ut för varje konto
    }

}