package org.example;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;

/**
 * @author Alex
 */
public class Customer {
    private String firstName;
    private String lastName;
    private String ownerID;
    private String passwordHash;

    /**
     * Konstruktor som kräver alla parametrar. En tom ArrayList med konton skapas i varje instans
     *
     * @param firstName kundens förnamn
     * @param lastName  kundens efternamn
     * @param ownerID   kundens personnummer
     * @throws InvalidNameException    om namnet ör ogiltigt. Ett giltigt namn måste bestå av minst en bokstav. Den första bokstaven måste vara stor och följande bokstäver måste vara små.
     * @throws InvalidOwnerIDException om personnumret är ogiltigt. Ett giltigt personnummer måste ha formatet "yyyyMMdd-XXXX".
     */

    public Customer(String firstName, String lastName, String ownerID, String plainPassword) throws InvalidNameException, InvalidOwnerIDException, InvalidPasswordException {
        //Kontrollerar att förnamn är ett giltigt namn
        if (ValidationService.INSTANCE.isValidName(firstName)) {
            this.firstName = firstName;
        } else {
            throw new InvalidNameException("Ogiltigt förnamn.");
        }
        //Kontrollerar att efternamn är ett giltigt namn
        if (ValidationService.INSTANCE.isValidName(firstName)) {
            this.lastName = lastName;
        } else {
            throw new InvalidNameException("Ogiltigt efternamn.");
        }
        //Kontrollerar att personnumret är giltigt.
        if (ValidationService.INSTANCE.isValidOwnerID(ownerID)) {
            this.ownerID = ownerID;
        } else {
            throw new InvalidOwnerIDException("Ogiltigt personnummer.");
        }
        if (ValidationService.INSTANCE.isValidPassword(plainPassword)) {
            this.passwordHash = DigestUtils.sha256Hex(plainPassword);
        } else {
            throw new InvalidPasswordException("Ogiltigt lösenord.");
        }
    }

    /**
     * @return förnamn (String)
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return efternamn (String)
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return ownerID/personnummer (String)
     */
    public String getOwnerID() {
        return ownerID;
    }


    //förnamn, efternamn, ägare-ID) Tillägg? ArrayList<String kontonr> yourAccounts
    //metod listAccounts
}
