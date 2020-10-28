package org.example;

import java.util.HashMap;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Abdi
 */

public class App {
    public static HashMap<String, Customer> allCustomers = FileService.INSTANCE.loadCustomers(); //HashMap K: ownerID (String), V: customer (Customer)
    public static HashMap<String, Account> allAccounts = FileService.INSTANCE.loadAccounts(); //HashMap K: accountNumber (String), V: account (Account)
    public static ArrayList<Transfer> allTransfers = FileService.INSTANCE.loadTransfers();

    public static void main(String[] args){
        menuNavigation();
    }

    private static void menuNavigation()  {


        System.out.println("--------Välkommen till Newton bank--------");
        boolean keepRunning = true;
        while (keepRunning) {


            System.out.println(""
                    + "\n[0] Avsluta program"
                    + "\n[1] Skapa kund"
                    + "\n[2] Skapa konto"
                    + "\n[3] Lista konto"
                    + "\n[4] Sätta in pengar på konto"
                    + "\n[5] Ta ut pengar från konto"
                    + "\n[6] Lägg upp betalningsuppdrag i en textfil"
                    + "\n[7] Ta bort betalningsuppdrag"
                    + "\n[8] Visa kassavalv"
                    + "\n[9] Gör direktöverföring"
                    );

            int menuChoice = Integer.parseInt(SingletonInput.getInstance().scanner.nextLine());
            //Skapade ett Singleton klass för att försöka fatta mig på hur det funkar och jag lyckades. Plon Jon har kollat genom koden och det är rätt.

            switch (menuChoice) {
                case 1:
                    addNewCutomer(allCustomers);
                    break;
                case 2:
                    //skapa konto
                    addCustomerToNewAccount(allCustomers, allAccounts);

                case 3:
                    //lista vald användars konto
                   printAccounts(allAccounts);
                    break;

                case 4:
                    //Sätta in pengar på konto
                    // Hantera kunder
                    break;

                case 5:
                    //Ta ut pengar från konto
                    break;

                case 6:
                    //Spara betalningsuppdrag för framtida betalning
                    break;

                case 7:
                    //Ta bort betalningsuppdrag
                    break;

                case 8:
                    //Visa kassavalv
                   inspectSafe();
                    break;

                case 9:
                    //Överföring mellan två konton
                    break;

                case 0:
                    //Avsluta program
                    FileService.INSTANCE.saveCustomers(allCustomers);
                    FileService.INSTANCE.saveAccounts(allAccounts);
                    FileService.INSTANCE.saveTransfers(allTransfers);
                    keepRunning = false;
                    break;

                default:
                    System.out.println("Felaktig inmatning.\n");
                    break;
            }
        }
    }



    private static void addCustomerToNewAccount(HashMap<String, Customer> allCustomers, HashMap<String, Account> allAccounts) {
        System.out.println("Vill du skapa ett nytt konto? " + "\n[1] Ja \n[2] Nej, Tiillbaka till menyn ");
        int tal = Integer.parseInt(SingletonInput.getInstance().scanner.nextLine());

        switch (tal) {

            case 1:

                System.out.println("Antal kunder i systemet: " + allCustomers.size() + "\n");
                int counter = 1;
                String format = " %-5s %-10s %-10s %-10s \n";
                System.out.format(format,"Rad ","Förnamn ", "Efternamn ", "Personnumer ");
                for (Map.Entry<String, Customer> customer: allCustomers.entrySet()) {    // Eftersom jag använde mig av ett forach loop så behövde jag skapa ett varibel som jag kunde numrera raderna som skrivs ut.
                    System.out.format(format, counter + ". " , customer.getValue().getFirstName() + " " ,
                            customer.getValue().getLastName() + " ",
                            customer.getValue().getOwnerID() + " " + "\n");
                    counter++;
                }

                System.out.println("Ange personnummret som du vill koppla kontot till:");
                String socialNumber = SingletonInput.getInstance().scanner.nextLine();

                if (allCustomers.containsKey(socialNumber)) {

                    Customer customer = allCustomers.get(socialNumber);

                    System.out.println("Lönekonto eller Sparkonto? ");
                    System.out.println("Namnge Kontot: ");
                    String accountName = SingletonInput.getInstance().scanner.nextLine();

                    Account newAccount = new Account(accountName, customer);
                    allAccounts.put(newAccount.getAccountNumber(), newAccount);

                }else{
                    System.out.println("Felaktig personnummer eller så finns inte den personen! ");
                }


                break;

            case 2:
                System.out.println("Tillbaka till menyn ");
                break;

            default:
                System.out.println("Felaktg inmatning! "
                        + "\nVänligen välj mellan dom alternativen som finns...");
                break;
        }
    }

    private static void printAccounts(HashMap<String, Account> allAccounts) {
        // Skriv kod för att välja kund
        // Customer customer;
        // Account.printAccount(allAccounts, customer);

        System.out.println("Antal kunder i systemet: " + allCustomers.size() + "\n");
        int counter = 1;
        String format = " %-5s %-10s %-10s %-10s \n";
        System.out.format(format,"Rad ","Förnamn ", "Efternamn ", "Personnumer ");
        for (Map.Entry<String, Customer> customers: allCustomers.entrySet()) {    // Eftersom jag använde mig av ett forach loop så behövde jag skapa ett varibel som jag kunde numrera raderna som skrivs ut.
            System.out.format(format, counter + ". " , customers.getValue().getFirstName() + " " ,
                    customers.getValue().getLastName() + " ",
                    customers.getValue().getOwnerID() + " " + "\n");
            counter++;
        }

        System.out.println("Vilken kund vill du komma åt? " + "\nVänligen ange personnummer: " );
        String socialNUmber =  SingletonInput.getInstance().scanner.nextLine();

        if(allCustomers.containsKey(socialNUmber)){

            Account.printAccount(allAccounts);
        } else{
            System.out.println("Tyvärr hittar inte kunden! ");
        }
/*
        int counter = 1;
        System.out.println("Kunder: " + allAccounts.size() + "\n");
        String format = "%-5s %-10s %-14s %-19s %-15s \n";
        System.out.format(format, "Rad ", "Kontonmamn ", "Kontonummer ", "Personnumer ", "Balance ");
        for (Map.Entry<String, Account> customers : allAccounts.entrySet()) {
           System.out.format(format, counter + ".", customers.getValue().accountName + " ",
                    customers.getValue().getAccountNumber() + " ",
                    customers.getValue().getOwnerID() + " ", customers.getValue().getBalance() + " " + "\n");
            counter++;
        }
    }

 */
    }

    private static void addNewCutomer(HashMap<String, Customer> allCustomers) {
        try {
            System.out.println("Förnamn: ");
            String firstname = SingletonInput.getInstance().scanner.nextLine();

            System.out.println("Efternamn: ");
            String lastname = SingletonInput.getInstance().scanner.nextLine();

            System.out.println("Personnummer: yyyymmdd-xxxx ");
            String ownerID = SingletonInput.getInstance().scanner.nextLine();

            Customer newCustomer = new Customer(firstname, lastname, ownerID);
            allCustomers.put(ownerID, newCustomer);

        }catch (InvalidNameException invalidNameException) {
            System.out.println("Namnet får inte innehålla siffor! " + "\nVänligen försök igen! ");

        }catch (InvalidOwnerIDException invalidOwnerIDException){
            System.out.println("Ogiltig personnummer " +"\nVänligen försök igen! ");
        }
    }

    /**
     * @author Alex
     * Metod som visar innehållet i kassavalvet.
     */
    private static void inspectSafe() {
        int totalBalance = 0;
        for(Account account : allAccounts.values()) {
            totalBalance += account.getBalance();
        }
        System.out.println("Det finns " + totalBalance + " ören i kassavalvet.\n");

    }
}
