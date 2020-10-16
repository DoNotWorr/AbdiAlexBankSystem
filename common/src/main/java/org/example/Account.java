package org.example;

import org.omg.CORBA.INTERNAL;

/**
 * @author Abdi
 *
 */
// Metod addTransfer (från konto: till konto, belopp, överföringsdatum)
    // metod removeTransfer
    // metod insertMoney
    // metod withrawMoney
    // Kolla så att personen finns


public class Account {

    private String accountNumber;
    private String ownerID;
    private int balance;

    public Account(String accountNumber, String ownerID, int balance ){
        this.accountNumber = accountNumber;
        this.ownerID = ownerID;
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

}