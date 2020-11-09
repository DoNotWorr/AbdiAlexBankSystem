package org.example;

import java.time.LocalDate;
import java.util.Random;

/**
 * @author Abdi
 */

public class Account {

    private String accountName;
    private String accountNumber;
    private String ownerID;
    private long balance;
    private int previousTransaction;

    /**
     * @param accountName Kontonamn.
     * @param customer    kundens ownerID/personnummer.
     */
    public Account(String accountName, Customer customer) {
        this.accountName = accountName;
        this.accountNumber = generateAccountNumber();
        this.ownerID = customer.getOwnerID();
    }

    /**
     * Metod för att få fram namnet på kontot.
     *
     * @return retunerar kontots namn.
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Metod som får fram kundens kontonummer.
     *
     * @return AccountNumber/Kontonummer (String)
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Den här metoden ska generera kontonummer som blir nyckeln i Hashmapen.
     *
     * @return ska retunera kontonummmret.
     */
    public static String generateAccountNumber() {
        return generateAccountNumber("55");
    }

    /**
     * Metod fär att få fram kundens personnummer.
     *
     * @return OwnerID/personnummer (String)
     */
    public String getOwnerID() {
        return ownerID;
    }

    /**
     * Metod som får fram kundens saldo i kontot.
     *
     * @return saldo på konto i ören.
     */
    public long getBalance() {
        return balance;
    }

    /**
     * @param amount beloppet som personen ska sätta in i kontot.
     * @return den retunerar true om det gick att sätta in pengar i kontot eller false om det inte gick.
     */

    public boolean depositMoney(long amount) {
        if (amount > 0) {
            setBalance(balance + amount);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metod för att få fram rätt balance för att kunna ändra saldot i kontot.
     */
    private void setBalance(long balance) {
        this.balance = balance;
    }

    /**
     * @param amount beloppet som personen ska ta ut från kontot.
     * @return den retunerar true om det gick att göra ett uttag från kontot eller false om det inte gick.
     */
    public boolean withdrawMoney(long amount) {
        if (amount > 0) {
            if (balance >= amount) {
                setBalance(balance - amount);
                return true;
            }
        }
        return false;
    }

    /**
     * Den här metoden visar historiken i alla insättningar och kontantuttag.
     */
    public void previousTransaction() {
        if (previousTransaction > 0) {
            System.out.println("Insättningar: " + previousTransaction);
        } else if (previousTransaction < 0) {
            System.out.println("Kontantuttag: " + Math.abs(previousTransaction));// Använder metoden Math för att kunna se uttraget som positivt istället för minus 1000kr
        } else {
            System.out.println("inget transaktionen har hänt än ");
        }
    }

    /**
     * Metod för direktövering.
     *
     * @return Den retunerar true om det gick att skicka över pengar eller false om det inte gick genom.
     */
    public boolean directTransfer(Account toAccount, long amount) {
        if (amount > 0 && amount <= this.getBalance()) {
            this.withdrawMoney(amount);
            toAccount.depositMoney(amount);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metod för att skapa bankuppdrag.
     *
     * @param toAccount    kontonummer betalning ska ske till
     * @param amount       antal ören (inte SEK)
     * @param transferDate datum i format YYYY-MM-DD för överföringen
     * @return retunerar den nya Transfer objektet.
     * @author Alex
     */
    public Transfer addTransfer(Account toAccount, long amount, LocalDate transferDate) {
        return new Transfer(this.accountNumber, toAccount.accountNumber, amount, transferDate);
    }

    /**
     * @param accountNumber ska generera kontonummer som börjar med 55
     * @author Abdi
     */
    public static String generateAccountNumber(String accountNumber) {
        //accountNumber = "55";
        Random value = new Random();

        // Generera 8 tal som ska ha 55 i början.
        int rad1 = value.nextInt(10);
        int rad2 = value.nextInt(10);
        accountNumber += rad1 + Integer.toString(rad2) + "-";

        int count = 0;
        int number = 0;
        StringBuilder accountNumberBuilder = new StringBuilder(accountNumber);
        for (int i = 0; i < 8; i++) {
            if (count == 4) {
                accountNumberBuilder.append("-");
                count = 0;
            } else
                number = value.nextInt(10);
            accountNumberBuilder.append(number);
            count++;
        }
        accountNumber = accountNumberBuilder.toString();
        return accountNumber;
    }

    /**
     * Metod för att skriva ut konton. Används i CustomerApp.
     *
     * @return formatet på utskriften
     */
    public String toString() {
        return this.accountName + "(" + this.getAccountNumber() + ")";
    }
}


