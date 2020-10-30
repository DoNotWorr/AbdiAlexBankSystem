package org.example;

import java.util.HashMap;
import java.util.*;


/**
 * @author Abdi
 */

public class App {
    public static HashMap<String, Customer> allCustomers = FileService.INSTANCE.loadCustomers(); //HashMap K: ownerID (String), V: customer (Customer)
    public static HashMap<String, Account> allAccounts = FileService.INSTANCE.loadAccounts(); //HashMap K: accountNumber (String), V: account (Account)
    public static ArrayList<Transfer> allTransfers = FileService.INSTANCE.loadTransfers();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){
        menuNavigation();
    }

    //@SuppressWarnings("DuplicateBranchesInSwitch")
    private static void menuNavigation() {


        System.out.println("--------Välkommen till Newton bank--------");
        boolean keepRunning = true;
        while (keepRunning) {
            try {
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

                int menuChoice = Integer.parseInt(scanner.nextLine());
                //Skapade ett Singleton klass för att försöka fatta mig på hur det funkar och jag lyckades. Plon Jon har kollat genom koden och det är rätt.

                switch (menuChoice) {
                    case 1:
                        addNewCutomer(allCustomers);
                        break;
                    case 2:
                        //skapa konto
                        addCustomerToNewAccount();
                        break;

                    case 3:
                        //lista vald användars konto
                        printAccounts();
                        break;

                    case 4:
                        // välj kund
                        // sätta in pengar med hjälp av metoden som finns i Account
                        // kunna se dom andra insättningar
                        depositeMoney();
                        break;

                    case 5:
                        //Välj kund
                        //Ta ut pengar från konto
                        // kunna se dom andra insättningar
                        withdrawMoney();
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
                        System.out.println("Felaktig inmatning. " + "\nVänligen välj det som finns i menyn!");
                        break;
                }
            }catch (Exception e){
                System.out.println("Felaktig inmatning! " + "\nVänligen försök igen ");
            }
        }
    }

    private static void withdrawMoney() {
        System.out.println("Antal kunder i systemet: " + allCustomers.size() + "\n");
        int counterswithdraw = 1;
        String formatswithdraw = "%-5s %-10s %-10s %-10s  \n";
        System.out.format(formatswithdraw, "Rad", "Förnamn ", "Efternamn ", "Personnummer ");

        for(Map.Entry<String, Customer> ownerID2 : allCustomers.entrySet())
        {
            System.out.format(formatswithdraw, counterswithdraw + ". ", ownerID2.getValue().getFirstName() + " ",
                    ownerID2.getValue().getLastName() + " ",
                    ownerID2.getValue().getOwnerID());
            counterswithdraw++;
        }
        System.out.println("Välj kundens personnummer som du vill komma åt: ");
        String ownerID2 = scanner.nextLine();

        System.out.println("Du har valt: " + ownerID2 + "\n");
        if (allCustomers.keySet().contains(ownerID2))
        {

            int counters2 = 0;
            String formatswithdraw2 = "%-5s %-10s %-16s %-15s %-19s  \n";
            System.out.format(formatswithdraw2, "Rad", "Förnamn ", "Efternamn ", "Personnummer ", "Balance ");
            for (Map.Entry<String, Account> accounts : allAccounts.entrySet())
            {
                if (accounts.getValue().getOwnerID().equals(ownerID2))
                {
                    System.out.format(formatswithdraw2, counters2 + 1 + ". ",
                            accounts.getValue().accountName + " ",
                            accounts.getValue().getAccountNumber() + " ",
                            accounts.getValue().getOwnerID() + " ",
                            accounts.getValue().getBalance() + " ");

                    counters2++;
                }
            }
        }

        Account.withdrawMoney(allAccounts);
    }
    private static void depositeMoney() {
        System.out.println("Antal kunder i systemet: " + allCustomers.size() + "\n");
        int counters = 1;
        String formats = "%-5s %-10s %-10s %-10s  \n";
        System.out.format(formats, "Rad", "Förnamn ", "Efternamn ", "Personnummer ");

        for(Map.Entry<String, Customer> ownerID : allCustomers.entrySet()){
            System.out.format(formats, counters + ". ", ownerID.getValue().getFirstName() + " ",
                    ownerID.getValue().getLastName() + " ",
                    ownerID.getValue().getOwnerID());
            counters++;
        }
        System.out.println("Välj kundens personnummer som du vill komma åt: ");
        String ownerID = scanner.nextLine();

        System.out.println("Du har valt: " + ownerID + "\n");
        if (allCustomers.keySet().contains(ownerID)){

            int counter = 0;
            String format = "%-5s %-10s %-16s %-15s %-19s  \n";
            System.out.format(format, "Rad", "Förnamn ", "Efternamn ", "Personnummer ", "Balance ");
            for (Map.Entry<String, Account> accounts : allAccounts.entrySet()) {
                if (accounts.getValue().getOwnerID().equals(ownerID)) {
                    System.out.format(format, counter + 1 + ". ",
                            accounts.getValue().accountName + " ",
                            accounts.getValue().getAccountNumber() + " ",
                            accounts.getValue().getOwnerID() + " ",
                            accounts.getValue().getBalance() + " ");

                    counter++;
                }
            }
        }

        Account.depositMoney(allAccounts);
    }
    private static void printAccounts() {
        Account.printAccount(allAccounts, allCustomers);
    }
    private static void addCustomerToNewAccount() {
        System.out.println("Vill du skapa ett nytt konto? " + "\n[1] Ja \n[2] Nej. Tiillbaka till menyn. ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {

            case 1:

                System.out.println("Antal kunder i systemet: " + allCustomers.size() + "\n");
                int counter = 1;
                String format = " %-5s %-10s %-10s %-10s \n";
                System.out.format(format,"Rad ","Förnamn ", "Efternamn ", "Personnumer ");
                for (Map.Entry<String, Customer> ownerID: allCustomers.entrySet()) {    // Eftersom jag använde mig av ett forach loop så behövde jag skapa ett varibel som jag kunde numrera raderna som skrivs ut.
                    System.out.format(format, counter + ". " ,
                            ownerID.getValue().getFirstName() + " " ,
                            ownerID.getValue().getLastName() + " ",
                            ownerID.getValue().getOwnerID() + " "
                                    + "\n");
                    counter++;
                }

                System.out.println("Ange personnummret som du vill koppla kontot till:");
                String ownerID = scanner.nextLine();

                if (allCustomers.containsKey(ownerID)) {

                    Customer customer = allCustomers.get(ownerID);

                    System.out.println("Lönekonto eller Sparkonto? ");
                    System.out.println("Namnge Kontot: ");
                    String accountName = scanner.nextLine();

                    Account newAccount = new Account(accountName, customer);
                    allAccounts.put(newAccount.getAccountNumber(), newAccount);

                    System.out.println("Nu skapas ett nytt konto: " + "\n"
                            + "Kontonamn:"
                            + newAccount.accountName + "\n"
                            + "Kontonummer: "
                            +  newAccount.getAccountNumber() + "\n"
                            + "balance: "
                            +  newAccount.getBalance()
                    );

                }else{
                    System.out.println("Felaktig personnummer eller så finns inte den personen! ");
                }
                break;

            case 2:
                System.out.println("Tillbaka till menyn. ");
                break;

            default:
                System.out.println("Felaktg inmatning! "
                        + "\nVänligen välj mellan dom alternativen som finns...");
                break;
        }
    }
    private static void addNewCutomer(HashMap<String, Customer> allCustomers) {
        try {
            System.out.println("Förnamn: ");
            String firstname = scanner.nextLine();

            System.out.println("Efternamn: ");
            String lastname = scanner.nextLine();

            System.out.println("Personnummer: yyyy-MM-dd-xxxx ");
            String ownerID = scanner.nextLine();

            Customer newCustomer = new Customer(firstname, lastname, ownerID);
            allCustomers.put(ownerID, newCustomer);

            System.out.println("Nu skapas en ny kund i systemet: "
                    + "\n" + "Förnamn: "
                    +  newCustomer.getFirstName() + "\n"
                    + "Efternamn: "
                    + newCustomer.getLastName() + "\n"
                    + "Personnummer: "
                    +  newCustomer.getOwnerID()
                );

        }catch (InvalidNameException invalidNameException) {
            System.out.println("Namnet får inte innehålla siffor! "
                    + "\nVänligen försök igen! ");

        }catch (InvalidOwnerIDException invalidOwnerIDException){
            System.out.println("Ogiltig personnummer "
                    +"\nVänligen försök igen! ");
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
        System.out.println("Det finns " + UnitConversion.convertToSek(totalBalance) + " kronor i kassavalvet.\n");
    }
}
