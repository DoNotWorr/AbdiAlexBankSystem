package org.example;

import java.util.ArrayList;

/**
 * @author Alex
 */
public class Customer {
    private String firstName;
    private String lastName;
    private String ownerID;
    private ArrayList<String> yourAccounts = new ArrayList<>();

    /**
     * Konstruktor som kräver alla parametrar. En tom ArrayList med konton skapas i varje instans
     * @param firstName kundens förnamn
     * @param lastName kundens efternamn
     * @param ownerID kundens personnummer
     */
    //todo validering av inmatning (kan inte skapa en person med som heter "123" eller ett ogiltigt personnummer)
    public Customer(String firstName, String lastName, String ownerID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ownerID = ownerID;
    }

    /**
     * @return förnamn (String)
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @return efternamn (String)
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * @return ownerID/personnummer (String)
     */
    public String getOwnerID() {
        return this.ownerID;
    }

    /**
     * @param newAccountNumber det nya kontots kontonummer som ska läggas i Customer ArrayList yourAccounts
     */
    public void addNewAccount(String newAccountNumber) {
        this.yourAccounts.add(newAccountNumber);
    }
    //förnamn, efternamn, ägare-ID) Tillägg? ArrayList<String kontonr> yourAccounts
    //metod listAccounts
}
