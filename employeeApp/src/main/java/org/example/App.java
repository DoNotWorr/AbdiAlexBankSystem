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
                        String ownerID = getCutomer("%-4s%-8s%-10s%-10s\n", "Den valda kundens personnummer är: ");
                        if (allCustomers.containsKey(ownerID)){

                            int counter = 0;
                            String format = "%-4s %-12s %-16s %-18s %-18s\n";
                            System.out.format(format, "Rad", "Kontonamn ", "Kontonummer ", "Personnummer ", "Balance ");
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
                            findCustomer(allAccounts);

                        }
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
                        // Välj kund
                        // välj konto

                        createNewTransfer();

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

    private static void createNewTransfer() {
        String ownerID = getCutomer("%-4s%-8s%-10s%-10s\n", "Den valda kundens personnummer är: ");

        if (allCustomers.containsKey(ownerID)){

            int counter = 0;
            String format = "%-4s %-12s %-16s %-18s %-18s\n";
            System.out.format(format, "Rad", "Kontonamn ", "Kontonummer ", "Personnummer ", "Balance ");
            for (Account thisAccount : allAccounts.values()) {
                if (thisAccount.getOwnerID().equals(ownerID)) {
                    System.out.format(format, counter + 1 + ". ",
                            thisAccount.accountName + " ",
                            thisAccount.getAccountNumber() + " ",
                            thisAccount.getOwnerID() + " ",
                            thisAccount.getBalance() + " ");

                    counter++;
                }
            }
        }
        System.out.println("Ange kontonummret xxxx xxxx xxxx som du vill skicka ifrån: ");
        String fromAccountNumber = scanner.nextLine();

        if (allAccounts.containsKey(fromAccountNumber)) {
            Account fromAccount = allAccounts.get(fromAccountNumber);
            System.out.println("Ange kontonummret xxxx xxxx xxxx som du vill skicka till: ");
            String toAccountNumber = scanner.nextLine();
            if (allAccounts.containsKey(toAccountNumber)) {
                Account toAccount = allAccounts.get(toAccountNumber);
                System.out.println("Hur mycket vill du skicka: ");
                int amount = Integer.parseInt(scanner.nextLine());
                if (fromAccount.directTransfer(toAccount, amount)) {
                    System.out.println("skickad");
                } else {
                    System.out.println("kunde inte skickad.....");
                }
            } else {
                System.out.println("hittade inte konto");
            }
        } else{
            System.out.println("fanns inget konto");
        }
    }
/*
    public int setBalance(Account account, int sign) {
        int balance = account.getBalance();
        if (sign == 1) {
            System.out.println("Hur mycket vill du sätta in: ");
            int amount = Integer.parseInt(scanner.nextLine());
            if (amount != 0) {
                balance = balance + amount;
                previousTransaction = amount;
                System.out.println("Kundens personnummer är: " + account.getOwnerID() );
                System.out.println("Kontonamn: " + account.accountName + "\n"
                        + "Kontonummer: "
                        + accountNumber + "\n"
                        + "Balance: " + balance + "\n"
                );
                previousTransaction(); //TODO så att man kan se historiken av flera insättnigar eller uttag.
                scanner.nextLine();
            }
        } else if (sign == 2) {
            System.out.println("Hur mycket vill du ta ut: ");
            int amount = Integer.parseInt(scanner.nextLine());
            if (balance >= amount) {
                balance = balance - amount;
                previousTransaction = -amount;
                System.out.println("Kundens personnummer är: " + account.ownerID );
                System.out.println("Kontonamn: " + accountName + "\n"
                        + "Kontonummer: "
                        + accountNumber + "\n"
                        + "Balance: " + balance + "\n"
                        + "Historik: " + previousTransaction);
                //previousTransaction();
                scanner.nextLine();
            } else {
                System.out.println("Transaktionen mysslyckades! ");
                System.out.println("För lite saldo i kontot... " + "\n"
                        +"Kontonamn: "
                        + accountName + "\n"
                        + "Kontonummer: "
                        + accountNumber + "\n"
                        + "Balance: "
                        + account.getBalance()
                );
                scanner.nextLine();
            }
        }

        return balance;
    }*/
    /*
    public static void findCustomersAccountToDepositeMoney(HashMap<String, Account> allAccounts) {
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
                // Abdis konton
                Account thisAccount = account.getValue();
                account.getValue().getBalance(); //thisAccount.setBalance(thisAccount, 1); //account.setValue(account.getValue()).setBalance(account.getValue());
            }
        }
    }*/



    public static void findCustomer(HashMap<String, Account> allAccounts) {
        System.out.println("Ange kontonummret xxxx xxxx xxxx som du vill betala ifrån: ");
        String accountNUmber = scanner.nextLine();
        for (Map.Entry<String, Account> account : allAccounts.entrySet()) {
            if (account.getValue().getAccountNumber().equals(accountNUmber)) {

                Account thisAccount = account.getValue();

            }
        }
    }

    private static void withdrawMoney() {
        String ownerID = getCutomer("%-5s %-10s %-10s %-10s  \n", "Den valda kundens personnumer är: ");

        if (allCustomers.containsKey(ownerID))
        {
            int counters = 0;
            String formats = "%-4s%-12s%-16s%-18s%-8s \n";
            System.out.format(formats, "Rad", "Kontonamn ", "Kontonummer ", "Personnummer ", "Balance ");
            for (Map.Entry<String, Account> accounts : allAccounts.entrySet())
            {
                if (accounts.getValue().getOwnerID().equals(ownerID))
                {
                    System.out.format(formats, counters + 1 + ". ",
                            accounts.getValue().accountName + " ",
                            accounts.getValue().getAccountNumber() + " ",
                            accounts.getValue().getOwnerID() + " ",
                            accounts.getValue().getBalance() + " "
                    );
                    counters++;
                }
            }
        }

        //Account.findCustomersAccountToWithdrawMoney(allAccounts);
    }
    private static void depositeMoney() {
        String ownerID = getCutomer("%-4s%-8s%-10s%-10s\n", "Den valda kundens personnummer är: ");

        if (allCustomers.containsKey(ownerID)){

            int counter = 0;
            String format = "%-4s %-12s %-16s %-18s %-18s\n";
            System.out.format(format, "Rad", "Kontonamn ", "Kontonummer ", "Personnummer ", "Balance ");
            for (Account thisAccount : allAccounts.values()) {
                if (thisAccount.getOwnerID().equals(ownerID)) {
                    System.out.format(format, counter + 1 + ". ",
                            thisAccount.accountName + " ",
                            thisAccount.getAccountNumber() + " ",
                            thisAccount.getOwnerID() + " ",
                            thisAccount.getBalance() + " ");

                    counter++;
                }
            }
        }
        System.out.println("Ange kontonummret xxxx xxxx xxxx som du vill sätta in pengarna på: ");
        String accountNumber = scanner.nextLine();

        for (Map.Entry<String, Account> account : allAccounts.entrySet()) {
            if (account.getValue().getAccountNumber().equals(accountNumber)) {
                /*
                steg 1 vi tar fram just det konto som vi söker efter och tar fram balance.
                steg 2 vi vill ändra värdet i balance i kontot som vi har tagit fram förut.
                steg 3 sen anropar vi setbalance och skickar med kontot som vi har tagit fram
                steg 4 I setbalance så gör vi själva insättningen och retunerar det nya balance.
                 */
                // Abdis konton
                System.out.println("Hur mycket vill du sätta in: ");
                int amount = Integer.parseInt(scanner.nextLine());
                if(account.setValue(account.getValue()).deposit(amount)){
                    System.out.println("Kundens personnummer är: " + account.getValue().getOwnerID() );
                    System.out.println("Kontonamn: " + account.getValue().accountName + "\n"
                            + "Kontonummer: "
                            + account.getValue().getAccountNumber() + "\n"
                            + "Balance: " + account.getValue().getBalance() + "\n"
                    );
                }

            }
        }

        //Account.findCustomersAccountToDepositeMoney(allAccounts);
    }
    private static void printAccounts() {
        Account.printAccount(allAccounts, allCustomers);
    }
    private static void addCustomerToNewAccount() {
        System.out.println("Vill du skapa ett nytt konto? " + "\n[1] Ja \n[2] Nej. Tillbaka till menyn. ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {

            case 1:

                System.out.println("Antal kunder i systemet: " + allCustomers.size() + "\n");
                int counter = 1;
                String format ="%-4s%-8s%-10s%-10s\n";
                System.out.format(format,"Rad","Förnamn","Efternamn","Personnumer");
                for (Customer thisCustomer: allCustomers.values()) {    // Eftersom jag använde mig av ett forach loop så behövde jag skapa ett varibel som jag kunde numrera raderna som skrivs ut.
                    System.out.format(format, counter + ". " ,
                            thisCustomer.getFirstName() + " " ,
                            thisCustomer.getLastName() + " ",
                            thisCustomer.getOwnerID());
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

                    System.out.println("Nu skapas ett nytt konto i systemet: " + "\n"
                            + "Kontonamn:"
                            + newAccount.accountName + "\n"
                            + "Kontonummer:"
                            +  newAccount.getAccountNumber() + "\n"
                            + "balance: "
                            +  newAccount.getBalance()
                    );

                    scanner.nextLine();

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

            System.out.println("Personnummer: yyyymmdd-XXXX ");
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
            scanner.nextLine();

        }catch (InvalidNameException invalidNameException) {
            System.out.println("Namnet får inte innehålla siffor! "
                    + "\nVänligen försök igen! ");

        }catch (InvalidOwnerIDException invalidOwnerIDException){
            System.out.println("Ogiltig personnummer "
                    +"\nVänligen försök igen! ");
        }
    }
    private static String getCutomer(String s, String s2) {
        System.out.println("Antal kunder i systemet: " + allCustomers.size() + "\n");
        int counters = 1;
        System.out.format(s, "Rad", "Förnamn ", "Efternamn ", "Personnummer ");

        for (Map.Entry<String, Customer> ownerID : allCustomers.entrySet()) {
            System.out.format(s, counters + ". ", ownerID.getValue().getFirstName() + " ",
                    ownerID.getValue().getLastName() + " ",
                    ownerID.getValue().getOwnerID());
            counters++;
        }
        System.out.println("Välj kundens personnummer som du vill komma åt: ");
        String ownerID = scanner.nextLine();

        System.out.println(s2 + ownerID + "\n");
        return ownerID;
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
