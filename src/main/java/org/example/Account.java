package org.example;

public class Account {

    private String accountNumber;
    private String ownerID;
    private String amount;

    public Account(String accountNumber, String ownerID, String amount ){
        this.accountNumber = accountNumber;
        this.ownerID = ownerID;
        this.amount = amount;
    }


public String getAccountNumber(){
    return accountNumber;
}
public String getOwnerID(){
        return ownerID;
}
public String getAmount(){
        return amount;
}
}