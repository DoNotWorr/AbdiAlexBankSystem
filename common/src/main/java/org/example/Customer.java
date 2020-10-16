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

    public Customer(String firstName, String lastName, String ownerID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ownerID = ownerID;
    }

    public String getOwnerID() {
        return this.ownerID;
    }
    //förnamn, efternamn, ägare-ID) Tillägg? ArrayList<String kontonr> yourAccounts
    //metod listAccounts
}
