package org.example;

import java.math.BigInteger;
import java.time.LocalDate;
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

    public static void main(String[] args) {
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

                switch (menuChoice) {

                    case 1:
                        addNewCutomer();
                        break;

                    case 2:
                        addCustomerToNewAccount();
                        break;

                    case 3:
                        printAccount();
                        break;

                    case 4:
                        depositeMoney();
                        break;

                    case 5:
                        withdrawMoney();
                        break;

                    case 6:
                        makePayment();
                        break;

                    case 7:
                        deletePayment();
                        break;

                    case 8:
                        inspectSafe();
                        break;

                    case 9:
                        createNewTransfer();
                        break;

                    case 0:
                        FileService.INSTANCE.saveCustomers(allCustomers);
                        FileService.INSTANCE.saveAccounts(allAccounts);
                        FileService.INSTANCE.saveTransfers(allTransfers);
                        keepRunning = false;
                        break;

                    default:
                        System.out.println("Felaktig inmatning.\nVänligen välj det som finns i menyn!");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Felaktig inmatning!\nVänligen försök igen ");
                e.printStackTrace();
            }
        }
    }

    /**
     * @author Abdi
     * Den här metoden tar fram kunden först och sen den personens konto och deras betalningsuppdrag som finns. Därefter kan man välja från listan vilken av dom som man vill avbryta.
     */
    private static void deletePayment() {
        System.out.println("Vill du ta bort ett betalningsuppdrag?\n[1] Ja\n[2] Nej ");
        int choice1 = Integer.parseInt(scanner.nextLine());

        boolean keepgoing = true;
        boolean keepgoings = true;

        while (keepgoing) {
            switch (choice1) {

                case 1:
                    String ownerID = findCutomer("%-5s %-10s %-10s %-10s\n", "Den valda kundens personnumer är: ");
                    if (!allCustomers.containsKey(ownerID)) {
                        System.out.println("Felaktig inmatning! ");
                        System.out.println("Vänligen skriv rätt personnummer som finns i listan.\n");
                    } else {
                        ArrayList<Transfer> yourTransfers = new ArrayList<>();
                        allTransfers.sort((Comparator.comparing(Transfer::getTransferDate)));
                        int counters = 0;
                        String formats = "%-4s%-18s%-18s%-8s%-11s%-10s\n";
                        System.out.format(formats, "Rad", "Från kontonummer ", "Till kontonummer ", "Belopp ", "Datum", "Status ");
                        for (Account accounts : allAccounts.values()) {
                            if (accounts.getOwnerID().equals(ownerID)) {
                                for (Transfer transfer : allTransfers) {
                                    if (transfer.getFromAccountNumber().equals(accounts.getAccountNumber()) && transfer.getStatus() == Transfer.TransferStatus.PENDING) {
                                        System.out.format(formats, counters + 1 + ". ",
                                                transfer.getFromAccountNumber(),
                                                transfer.getToAccountNumber(),
                                                UnitConversion.convertToSek(transfer.getAmount()) + " kr",
                                                transfer.getTransferDate(),
                                                transfer.getStatus()
                                        );
                                        counters++;
                                        keepgoing = false;
                                        yourTransfers.add(transfer);

                                    }
                                }
                            }
                        }
                        while (keepgoings) {
                            try {
                                System.out.println("Välj siffran som du vill avbryta betalningen på från listan: ");
                                int listNumber = Integer.parseInt(scanner.nextLine());

                                Transfer thisTransfer = yourTransfers.get(listNumber - 1);
                                System.out.println("Status på betalningen är :" + thisTransfer.getStatus());

                                System.out.println("Vill du verkligen avbryta?" + "[1] Ja [2] Nej");
                                int choice = Integer.parseInt(scanner.nextLine());

                                switch (choice) {
                                    case 1:
                                        thisTransfer.setStatus(Transfer.TransferStatus.CANCELLED);
                                        System.out.println("Statusen på betalningen nu är: " + thisTransfer.getStatus());
                                        keepgoings = false;
                                        break;

                                    case 2:
                                        System.out.println("Återgår till menyn.");
                                        keepgoings = false;
                                        break;

                                    default:
                                        System.out.println("Felaktig inmatning!\nVänligen försök igen!");
                                        break;
                                }
                            } catch (Exception e) {
                                System.out.println("Felaktig inmatning!\nVänligen försök igen!");
                            }
                        }
                    }
                    scanner.nextLine();

                    break;

                case 2:
                    System.out.println("Återgår till menyn.");
                    break;

                default:
                    System.out.println("Felaktig inmatning! ");
                    break;
            }
        }
    }

    /**
     * Den här metoden tar fram kunden först och den personens konton. Därefter väljer man vilken av dom konton som man vill göra betalningen ifrån.
     */
    private static void makePayment() throws TooBigNumberException, TooManyDecimalsException {
        System.out.println("Vill du ska ett nytt betalningsuppdrag?\n[1] Ja\n[2] Nej ");
        int choice = Integer.parseInt(scanner.nextLine());

        boolean keepgoing = true;
        boolean keepgoings = true;

        switch (choice) {

            case 1:
                while (keepgoing) {
                    String ownerID = findCutomer("%-5s %-10s %-10s %-10s\n", "Den valda kundens personner är: ");
                    if (!allCustomers.containsKey(ownerID)) {
                        System.out.println("Felaktig inmatning! ");
                        System.out.println("Vänligen skriv rätt personnummer som finns i listan.\n");
                    } else {
                        int counters = 0;
                        String formats = "%-4s%-12s%-16s%-18s%-8s\n";
                        System.out.format(formats, "Rad", "Kontonamn ", "Kontonummer ", "Personnummer ", "Saldo ");
                        for (Account accounts : allAccounts.values()) {
                            if (accounts.getOwnerID().equals(ownerID)) {
                                System.out.format(formats, counters + 1 + ". ",
                                        accounts.getAccountName() + " ",
                                        accounts.getAccountNumber() + " ",
                                        accounts.getOwnerID() + " ",
                                        UnitConversion.convertToSek(accounts.getBalance()) + " kr"
                                );
                                counters++;
                                keepgoing = false;
                            }
                        }
                    }
                }
                while (keepgoings) {
                    double amount = 0;
                    System.out.println("Ange kontonummret xxxx-xxxx-xxxx som du vill skicka betalningsuppdraget ifrån: ");
                    String fromAccountNumber = scanner.nextLine();
                    Account fromAccount = allAccounts.get(fromAccountNumber);

                    if (allAccounts.containsKey(fromAccountNumber)) {
                        System.out.println("Ange kontonummret xxxx-xxxx-xxxx som du vill skicka till: ");
                        String toAccountNumber = scanner.nextLine();
                        Account toAccount = allAccounts.get(toAccountNumber);
                        if (allAccounts.containsKey(toAccountNumber)) {
                            System.out.println("Hur mycket pengar vill du skicka över: ");
                            amount = Double.parseDouble(scanner.nextLine());
                            System.out.println("Ange datumet yy-mm-dd när du vill betalningsuppdraget ska skickas: ");
                            String transferDate = scanner.nextLine();

                            allTransfers.add(fromAccount.addTransfer(toAccount, UnitConversion.convertFromSek(amount), LocalDate.parse(transferDate)));

                            System.out.println("Pengarna kommer att skickas till det här kontonummret: "
                                    + toAccountNumber + "\n"
                                    + "Belopp är: "
                                    + amount + " kr\n"
                                    + "Datum som betalningen kommer skickas är: "
                                    + transferDate + "\n"
                                    + "Status på betalningen: "
                                    + Transfer.TransferStatus.PENDING
                            );
                            keepgoings = false;
                        }
                    }
                }
                scanner.nextLine();
                break;

            case 2:
                System.out.println("Återgår till menyn.");
                break;

            default:
                System.out.println("Felaktig inmatning! ");
                break;
        }
    }

    /**
     * Den här metoden loppar genom alla kunder för att hitta specifik kund och sen hitta konton som
     * tillhör den. Sen väljer man kontot som man vill skicka pengar ifrån och sen väljer man kontot som man vill skicka pengarna till.
     */
    private static void createNewTransfer() throws TooBigNumberException, TooManyDecimalsException {
        boolean keepgoing = true;
        boolean keepgoings = true;

        while (keepgoing) {
            String ownerID = findCutomer("%-5s %-10s %-10s %-10s\n", "Den valda kundens personnumer är: ");
            if (!allCustomers.containsKey(ownerID)) {
                System.out.println("Felaktig inmatning! ");
                System.out.println("Vänligen skriv rätt personnummer som finns i listan.\n");
            } else {
                int counters = 0;
                String formats = "%-4s%-12s%-16s%-18s%-8s\n";
                System.out.format(formats, "Rad", "Kontonamn ", "Kontonummer ", "Personnummer ", "Saldo ");
                for (Account accounts : allAccounts.values()) {
                    if (accounts.getOwnerID().equals(ownerID)) {
                        System.out.format(formats, counters + 1 + ". ",
                                accounts.getAccountName() + " ",
                                accounts.getAccountNumber() + " ",
                                accounts.getOwnerID() + " ",
                                UnitConversion.convertToSek(accounts.getBalance()) + " kr"
                        );
                        counters++;
                        keepgoing = false;
                    }
                }
            }
        }
        while (keepgoings) {
            System.out.println("Ange kontonummret xxxx-xxxx-xxxx som du vill skicka ifrån: ");
            String fromAccountNumber = scanner.nextLine();

            if (allAccounts.containsKey(fromAccountNumber)) {
                Account fromAccount = allAccounts.get(fromAccountNumber);
                System.out.println("Ange kontonummret xxxx-xxxx-xxxx som du vill skicka till: ");
                String toAccountNumber = scanner.nextLine();
                if (allAccounts.containsKey(toAccountNumber)) {
                    Account toAccount = allAccounts.get(toAccountNumber);
                    System.out.println("Hur mycket pengar vill du skicka över: ");
                    double amount = Double.parseDouble(scanner.nextLine());
                    if (fromAccount.directTransfer(toAccount, UnitConversion.convertFromSek(amount))) {
                        System.out.println("Pengarnade överförades till: "
                                + toAccount.getAccountNumber());
                        keepgoings = false;
                    } else {
                        System.out.println("Pengarna kunde inte skickas till kontot: "
                                + toAccount.getAccountNumber());
                    }
                } else {
                    System.out.println("Kunde inte hitta den valda kontot som du ville skicka penagr till...\nVänligen försök igen! ");
                }
            } else {
                System.out.println("Fanns inget konto som matchade det du skrev in: " + fromAccountNumber
                        + "\nVänligen försök igen! ");
            }
        }
        scanner.nextLine();
    }

    /**
     * Den här metoden loppar genom alla kunder för att hitta specifik kund och sen hitta konton som
     * tillhör den. Sen väljer man kontot som man vill göra ett uttag från.
     */
    private static void withdrawMoney() throws TooBigNumberException, TooManyDecimalsException {
        boolean keepgoing = true;
        boolean keepgoings = true;

        while (keepgoing) {
            String ownerID = findCutomer("%-5s %-10s %-10s %-10s\n", "Den valda kundens personnumer är: ");
            if (!allCustomers.containsKey(ownerID)) {
                System.out.println("Felaktig inmatning! ");
                System.out.println("Vänligen skriv rätt personnummer som finns i listan.\n");
            } else {
                int counters = 0;
                String formats = "%-4s%-12s%-16s%-18s%-8s \n";
                System.out.format(formats, "Rad", "Kontonamn ", "Kontonummer ", "Personnummer ", "Saldo ");
                for (Account accounts : allAccounts.values()) {
                    if (accounts.getOwnerID().equals(ownerID)) {
                        System.out.format(formats, counters + 1 + ". ",
                                accounts.getAccountName() + " ",
                                accounts.getAccountNumber() + " ",
                                accounts.getOwnerID() + " ",
                                UnitConversion.convertToSek(accounts.getBalance()) + " kr"
                        );
                        counters++;
                        keepgoing = false;
                    }
                }
            }
        }
        while (keepgoings) {
            System.out.println("Ange kontonummret xxxx-xxxx-xxxx som du vill ta ut pengarna ifrån: ");
            String accountNumber = scanner.nextLine();

            for (Account thisAccount : allAccounts.values()) {
                if (thisAccount.getAccountNumber().equals(accountNumber)) {
                    System.out.println("Hur mycket vill du ta ut från kontot: ");
                    double amount = Double.parseDouble(scanner.nextLine());
                    if (thisAccount.withdrawMoney(UnitConversion.convertFromSek(amount))) {
                        System.out.println("Den valda kundens personnummer är: " + thisAccount.getOwnerID());
                        System.out.println("Kontonamn: "
                                + thisAccount.getAccountName() + "\n"
                                + "Kontonummer: "
                                + thisAccount.getAccountNumber() + "\n"
                                + "Saldo: "
                                + UnitConversion.convertToSek(thisAccount.getBalance()) + " kr");
                        keepgoings = false;
                    } else {
                        System.out.println("För lite saldo i kontot!\n"
                                + "Kontonamn: "
                                + thisAccount.getAccountName() + "\n"
                                + "Kontonummer:"
                                + accountNumber + "\n"
                                + "Saldo: "
                                + UnitConversion.convertToSek(thisAccount.getBalance()) + " kr");
                    }
                }
            }
        }
        scanner.nextLine();
    }

    /**
     * Den här metoden loppar genom alla kunder för att hitta specifik kund och sen hitta konton som
     * tillhör den. Sen väljer man kontot som man vill göra insättning i.
     */
    private static void depositeMoney() throws TooBigNumberException, TooManyDecimalsException {
        boolean keepgoing = true;
        boolean keepgoings = true;
        while (keepgoing) {
            String ownerID = findCutomer("%-4s%-8s%-10s%-10s\n", "Den valda kundens personnummer är: ");
            if (!allCustomers.containsKey(ownerID)) {
                System.out.println("Felaktig inmatning! ");
                System.out.println("Vänligen skriv rätt personnummer som finns i listan.\n");
            } else {
                int counter = 0;
                String format = "%-4s %-12s %-16s %-18s %-18s\n";
                System.out.format(format, "Rad", "Kontonamn ", "Kontonummer ", "Personnummer ", "Saldo ");
                for (Account thisAccount : allAccounts.values()) {
                    if (thisAccount.getOwnerID().equals(ownerID)) {
                        System.out.format(format, counter + 1 + ". ",
                                thisAccount.getAccountName() + " ",
                                thisAccount.getAccountNumber() + " ",
                                thisAccount.getOwnerID() + " ",
                                UnitConversion.convertToSek(thisAccount.getBalance()) + " kr");
                        counter++;
                        keepgoing = false;
                    }
                }
            }
        }
        while (keepgoings) {
            System.out.println("Ange kontonummret xxxx-xxxx-xxxx som du vill sätta in pengarna på: ");
            String accountNumber = scanner.nextLine();

            for (Account thisAccount : allAccounts.values()) {
                if (thisAccount.getAccountNumber().equals(accountNumber)) {
                    System.out.println("Hur mycket vill du sätta in: ");
                    double amount = Double.parseDouble(scanner.nextLine());
                    if (thisAccount.depositMoney(UnitConversion.convertFromSek(amount))) {
                        System.out.println("Den valda kundens personnummer är: " + thisAccount.getOwnerID());
                        System.out.println("Kontonamn: " + thisAccount.getAccountName() + "\n"
                                + "Kontonummer: "
                                + thisAccount.getAccountNumber() + "\n"
                                + "Saldo: " + UnitConversion.convertToSek(thisAccount.getBalance()) + " kr");
                        keepgoings = false;
                    } else {
                        System.out.println("Insättningar måste vara positiv och inget negativa belopp.\nVänligen försök igen!");
                    }
                }
            }
        }
        scanner.nextLine();
    }

    /**
     * Metoden är till för att kunna printa kundens konto efter man har valt specifik kund.
     */
    public static void printAccount() {
        boolean keepgoing = true;
        while (keepgoing) {
            String ownerID = findCutomer("%-4s%-8s%-10s%-10s\n", "Den valda kundens personnummer är: ");
            if (!allCustomers.containsKey(ownerID)) {
                System.out.println("Felaktig inmatning! ");
                System.out.println("Vänligen skriv rätt personnummer som finns i listan.\n");
            } else {
                System.out.println("Den valda kundens personnummer är : " + ownerID);

                int counter = 1;
                String format = "%-4s %-12s%-16s%-18s%-18s\n";
                System.out.format(format, "Rad ", "Kontonamn ", "Kontonummer ", "Personnumer ", "Saldo ");
                for (Account thisAccounts : allAccounts.values()) {
                    if (thisAccounts.getOwnerID().equals(ownerID))
                        System.out.format(format, counter + ". ",
                                thisAccounts.getAccountName() + " ",
                                thisAccounts.getAccountNumber() + " ",
                                thisAccounts.getOwnerID() + " ",
                                UnitConversion.convertToSek(thisAccounts.getBalance()) + " kr"
                        );
                    counter++;
                    keepgoing = false;
                }
                scanner.nextLine();
            }
        }
    }

    /**
     * Den här metoden kopplar kunden till ett nytt konto och sen lägger det i hashmapen.
     */
    private static void addCustomerToNewAccount() {
        System.out.println("Vill du skapa ett nytt konto?\n[1] Ja\n[2] Nej. Tillbaka till menyn. ");
        int choice = Integer.parseInt(scanner.nextLine());

        boolean keepgoing = true;

        switch (choice) {

            case 1:
                while (keepgoing) {
                    String ownerID = findCutomer("%-4s%-8s%-10s%-10s\n", "Den valda kundens personnummer är: ");

                    if (allCustomers.containsKey(ownerID)) {

                        Customer customer = allCustomers.get(ownerID);

                        System.out.println("Lönekonto eller Sparkonto? ");
                        System.out.println("Namnge Kontot: ");
                        String accountName = scanner.nextLine();

                        Account newAccount = new Account(accountName, customer);
                        allAccounts.put(newAccount.getAccountNumber(), newAccount);

                        System.out.println("Nu skapas ett nytt konto i systemet:\n"
                                + "Kontonamn:"
                                + newAccount.getAccountName() + "\n"
                                + "Kontonummer:"
                                + newAccount.getAccountNumber() + "\n"
                                + "Saldo: "
                                + UnitConversion.convertToSek(newAccount.getBalance()) + " kr"
                        );
                        keepgoing = false;

                        scanner.nextLine();

                    } else {
                        System.out.println("Felaktig personnummer eller så finns inte den personen!\n");
                    }
                }
                break;

            case 2:
                System.out.println("Tillbaka till menyn. ");
                break;

            default:
                System.out.println("Felaktg inmatning!\nVänligen välj mellan dom alternativen som finns...");
                break;
        }
    }

    /**
     * Den här metoden skapar en ny kund och sen lägger till den kunden till hashmapen.
     */
    private static void addNewCutomer() {
        boolean keepgoing = true;
        while (keepgoing) {
            try {

                System.out.println("Förnamn: ");
                String firstname = scanner.nextLine();

                System.out.println("Efternamn: ");
                String lastname = scanner.nextLine();

                System.out.println("Personnummer: yyyymmdd-XXXX ");
                String ownerID = scanner.nextLine();

                Customer newCustomer = new Customer(firstname, lastname, ownerID);
                allCustomers.put(ownerID, newCustomer);

                System.out.println("Nu skapas en ny kund i systemet:\n"
                        + "Förnamn: "
                        + newCustomer.getFirstName() + "\n"
                        + "Efternamn: "
                        + newCustomer.getLastName() + "\n"
                        + "Personnummer: "
                        + newCustomer.getOwnerID());
                keepgoing = false;
            } catch (InvalidNameException invalidNameException) {
                System.out.println("Namnet får inte innehålla siffor!\nVänligen försök igen!\n");

            } catch (InvalidOwnerIDException invalidOwnerIDException) {
                System.out.println("Ogiltig personnummer\nVänligen försök igen! ");
            }
        }
    }

    /**
     * Den här metoden loopar genom alla kunder och för att hitta kunden som vi söker efter.
     *
     * @return Den retunerar kunden som vi hittade.
     */
    private static String findCutomer(String s, String s2) {
        System.out.println("Antal kunder i systemet: " + allCustomers.size() + "\n");
        int counters = 1;
        System.out.format(s, "Rad", "Förnamn ", "Efternamn ", "Personnummer ");

        for (Customer thisCustomer : allCustomers.values()) {
            System.out.format(s, counters + ". ", thisCustomer.getFirstName() + " ",
                    thisCustomer.getLastName() + " ",
                    thisCustomer.getOwnerID());
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
        BigInteger totalBalance = new BigInteger("0");
        for (Account account : allAccounts.values()) {
            totalBalance.add(BigInteger.valueOf(account.getBalance()));
        }
        System.out.println("Det finns " + UnitConversion.convertToSek(totalBalance.toString()) + " kr i kassavalvet.\n");
    }
}