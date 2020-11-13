package org.example.DataClasses;

import org.apache.commons.codec.digest.DigestUtils;
import org.example.Exceptions.InvalidNameException;
import org.example.Exceptions.InvalidOwnerIDException;
import org.example.Exceptions.InvalidPasswordException;
import org.example.ValidationService;

/**
 * @author Alex
 */
public class Customer {
    private String firstName;
    private String lastName;
    private String ownerID;
    private String passwordHash;

    /**
     * Konstruktor som kräver alla parametrar.
     *
     * @param firstName     förnamn
     * @param lastName      efternamn
     * @param ownerID       personnummer i formatet "yyyymmdd-xxxx"
     * @param plainPassword lösenord i plaintext med minst 4 tecken
     * @throws InvalidNameException     om namnet ör ogiltigt. Ett giltigt namn måste bestå av minst en bokstav. Den första bokstaven måste vara stor och följande bokstäver måste vara små.
     * @throws InvalidOwnerIDException  om personnumret är ogiltigt. Ett giltigt personnummer måste ha formatet "yyyyMMdd-XXXX".
     * @throws InvalidPasswordException om lösenordet är ogiltigt. Ett giltigt lösenord måste ha minst 4 tecken
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

    /**
     * @return passwordHash
     */
    public String getPasswordHash() {
        return this.passwordHash;
    }
}
