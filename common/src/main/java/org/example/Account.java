package org.example;

/**
 * @author Abdi
 *
 */

public class Account {

    private Integer accountNumber;
    private String ownerID;
    private String balance;

    public Account(Integer accountNumber, String ownerID, String balance ){
        this.accountNumber = accountNumber;
        this.ownerID = ownerID;
        this.balance = balance;
    }


public Integer getAccountNumber(){
    return accountNumber;
}
public String getOwnerID(){
        return ownerID;
}
public String getBalance(){
        return balance;
}

}