package org.example;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Abdi
 */

// Metod addTransfer (från konto: till konto, belopp, överföringsdatum)
// metod removeTransfer
// metod insertMoney
// metod withrawMoney


public class Account {
    public static Scanner scanner = new Scanner(System.in);
    public String accountName;
    private String accountNumber;
    private String ownerID;
    private int balance;
    private int previousTransaction;

    /**
     * @param accountName Kontonamn
     * @param customer    kundens ownerID/personnummer
     */
    public Account(String accountName, Customer customer) {
        this.accountName = accountName;
        this.accountNumber = generateAccountNumber();
        this.ownerID = customer.getOwnerID();
    }

    /**
     * @return AccountNumber/Kontonummer (String)
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Den här metoden ska generera kontonummer som blir nyckeln i Hashmapen
     *
     * @return ska retunera kontonummmret.
     */
    public static String generateAccountNumber() {

        return generateAccountNumber("55");
    }

    /**
     * @return OwnerID/personnummer (String)
     */
    public String getOwnerID() {
        return ownerID;
    }

    /**
     * @return saldo på konto i ören.
     */
    public int getBalance() {
        return balance;
    }

    /**
     * @author Abdi
     * Metod för att ta fram just det konto som jag ska ändra balance på
     */
    /*public static void findCustomersAccountToDepositeMoney(HashMap<String, Account> allAccounts) {
        System.out.println("Ange kontonummret xxxx xxxx xxxx som du vill sätta in pengarna på: ");
        String accountNUmber = scanner.nextLine();
        for (Map.Entry<String, Account> account : allAccounts.entrySet()) {
            if (account.getValue().getAccountNumber().equals(accountNUmber)) {
                *//*
                steg 1 vi tar fram just det konto som vi söker efter och tar fram balance.
                steg 2 vi vill ändra värdet i balance i kontot som vi har tagit fram förut.
                steg 3 sen anropar vi setbalance och skickar med kontot som vi har tagit fram
                steg 4 I setbalance så gör vi själva insättningen och retunerar det nya balance.
                 *//*

                Account thisAccount = account.getValue();
                account.getValue().balance = thisAccount.setBalance(thisAccount, 1); //account.setValue(account.getValue()).setBalance(account.getValue());
            }
        }
    }*/

    public boolean deposit(int amount) {
        if (amount > 0) {
            setBalance(balance + amount);
            return true;
        } else {
            return false;
        }
    }
    private void setBalance(int balance){
        this.balance = balance;

    }
    public boolean withdraw(int amount) {
        if (amount > 0) {
            setBalance(balance - amount);
            return true;
        } else {
            return false;
        }
    }
    /**
     *
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
     */

    public boolean directTransfer(Account toAccount, int amount){
        // Överföring konto till konto
        // Amount
        // retunera true / false
        if(amount > 0 && amount <= this.getBalance()){
            this.withdraw(amount);
            toAccount.deposit(amount);
            return true;
        }
        return false;
    }

    /**
     * Metod för att skapa bankuppdrag.
     * @param toAccount    kontonummer betalning ska ske till
     * @param amount       antal ören (inte SEK)
     * @param transferDate datum i format YYYY-MM-DD för överföringen
     * @author Alex
     */
    public void addTransfer(String toAccount, int amount, LocalDate transferDate) {
        Transfer transfer = new Transfer(this.accountNumber, toAccount, amount, transferDate);
    }

    /**
     * @param allAccounts  Alla konton som finns Accountklassen
     * @param allCustomers Alla kunder som finns i Customerklassen
     * Metoden är till för att kunna printa kundens konto efter man har valt specifik kund.
     * @author Abdi
     */
    public static void printAccount(HashMap<String, Account> allAccounts, HashMap<String, Customer> allCustomers) {
        System.out.println("Antal kunder i systemet: " + allCustomers.size() + "\n");
        int counters = 1;
        String formats = "%-4s %-8s %-10s %-10s \n";
        System.out.format(formats, "Rad", "Förnamn ", "Efternamn ", "Personnummer ");

        for (Map.Entry<String, Customer> ownerId : allCustomers.entrySet()) {
            System.out.format(formats, counters + ". ", ownerId.getValue().getFirstName() + " ",
                    ownerId.getValue().getLastName() + " ",
                    ownerId.getValue().getOwnerID()
            );
            counters++;
        }
        System.out.println("Skriv kundens personnummer yymmdd-XXXX som du vill komma åt: ");
        String ownerID = scanner.nextLine();

        System.out.println("Den valda kundens personnummer är : " + ownerID);

        if (allCustomers.containsKey(ownerID)) {

            int counter = 1;
            String format = "%-4s %-12s%-16s%-18s%-18s\n";
            System.out.format(format,"Rad ","Kontonamn ", "Kontonummer ","Personnumer ","Balance ");
            for (Map.Entry<String, Account> accounts : allAccounts.entrySet()) {
                if (accounts.getValue().getOwnerID().equals(ownerID))
                    System.out.format(format, counter + ". ",
                            accounts.getValue().accountName + " ",
                            accounts.getValue().getAccountNumber() + " ",
                            accounts.getValue().getOwnerID() + " ",
                            accounts.getValue().getBalance()
                    );
                counter++;
            }
            scanner.nextLine();
        } else {
            System.out.println("Tyvärr så finns inte kunden: ");
        }
    }

    /**
     * @param accountNumber ska generera kontonummer som börjar med 55
     * @author Abdi
     */
    public static String generateAccountNumber(String accountNumber) {
        //accountNumber = "55";
        Random value = new Random();

        // Generera 8 tal som ska 55 i början.
        int rad1 = value.nextInt(10);
        int rad2 = value.nextInt(10);
        accountNumber += Integer.toString(rad1) + Integer.toString(rad2) + " ";

        int count = 0;
        int number = 0;
        for (int i = 0; i < 8; i++) {
            if (count == 4) {
                accountNumber += " ";
                count = 0;
            } else
                number = value.nextInt(10);
            accountNumber += Integer.toString(number);
            count++;
        }
        return accountNumber;
    }
}


