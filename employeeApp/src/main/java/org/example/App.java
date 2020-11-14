package org.example;

import org.example.DataClasses.Account;
import org.example.DataClasses.Customer;
import org.example.DataClasses.Transfer;
import org.example.Exceptions.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
                    System.out.println("Tryck enter för att återgå till huvudmenyn.");
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
     *
     * @author Abdi, Alex
     * Abdi skrev metoden. Alex skapade flera loopar så man slipper skriva om så mycket ifall det blir fel.
     */
    private static void makePayment() {

        System.out.println("Vill du skapa ett nytt betalningsuppdrag?\n[1] Ja\n[2] Nej ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                boolean keepRunningPartOne = true;
                boolean keepRunningPartTwo = true;
                boolean keepRunningPartThree = true;
                boolean keepRunningPartFour = true;
                boolean keepRunningPartFive = true;
                Account fromAccount = null;
                Account toAccount = null;
                String toAccountNumber = null;
                String fromAccountNumber = null;
                long amount = 0;
                String amountInput = null;
                LocalDate transferDate = null;

                //Väljer den kund som ska lägga upp betalningsuppdrag
                while (keepRunningPartOne) {
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
                                keepRunningPartOne = false;
                            }
                        }
                    }
                }

                //Väljer vilket av kundens konto som ska användas
                while (keepRunningPartTwo) {
                    System.out.println("Ange kontonummret xxxx-xxxx-xxxx som du vill skicka betalningsuppdraget ifrån: ");
                    fromAccountNumber = scanner.nextLine();
                    if (fromAccountNumber.length() == 0) {
                        return;
                    }
                    if (allAccounts.containsKey(fromAccountNumber)) {
                        fromAccount = allAccounts.get(fromAccountNumber);
                        keepRunningPartTwo = false;
                    } else {
                        System.out.println("Hittade inte kontot");
                    }
                }

                //Väljer mottagarens konto
                while (keepRunningPartThree) {
                    System.out.println("Ange kontonummret xxxx-xxxx-xxxx som du vill skicka till: ");
                    toAccountNumber = scanner.nextLine();
                    //För att komma ur loopen ifall man inte vet kontonumret eller det inte finns några konton att skicka till.
                    if (toAccountNumber.length() == 0) {
                        return;
                    }
                    if (allAccounts.containsKey(toAccountNumber)) {
                        if (fromAccountNumber.equals(toAccountNumber)) {
                            System.out.println("Mottagarkonto kan inte vara samma som avsändarkonto.");
                        } else {
                            toAccount = allAccounts.get(toAccountNumber);
                            keepRunningPartThree = false;
                        }
                    } else {
                        System.out.println("Hittade inte kontot");
                    }
                }

                while (keepRunningPartFour) {
                    try {
                        System.out.println("Ange hur många kronor du vill skicka över: ");
                        amountInput = scanner.nextLine();
                        amount = UnitConversion.convertFromSek(amountInput);
                        if (amount > 0) {
                            keepRunningPartFour = false;
                        }
                    } catch (NonNumericalException e) {
                        System.out.println("Felaktig inmatning. Beloppet måste vara ett tal.");
                    } catch (NumberNotInBoundsException e) {
                        System.out.println("Felaktig inmatning. Kan inte hantera för stora eller små tal. Kan inte hantera tal med mer än två decimaler.");
                    }
                }

                while (keepRunningPartFive) {
                    System.out.println("Vilket datum ska betalningen skickas? Ange ett datum (imorgon eller senare) i formatet yyyy-mm-dd: ");
                    try {
                        transferDate = LocalDate.parse(scanner.nextLine());
                        if (transferDate.isBefore(LocalDate.now().plusDays(1))) {
                            System.out.println("Datumet måste vara imorgon eller senare.");
                        } else {
                            keepRunningPartFive = false;
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Ogiltigt datum");
                    }
                }
                try {
                    allTransfers.add(fromAccount.addTransfer(toAccount, amount, transferDate));
                    System.out.println("Pengarna kommer att skickas till det här kontonummret: "
                            + toAccountNumber + "\n"
                            + "Belopp är: "
                            + amountInput + " kr\n"
                            + "Datum som betalningen kommer skickas är: "
                            + transferDate + "\n"
                            + "Status på betalningen: "
                            + Transfer.TransferStatus.PENDING
                    );
                    if (amount > fromAccount.getBalance()) {
                        System.out.println("Observera att du för tillfället inte har tillräckligt med pengar på kontot.");
                        System.out.println("Se till att beloppet finns på kontot före " + transferDate.toString() + ", annars kommer betalning inte gå igenom!");
                    }
                } catch (NullToAccountException e) {
                    e.printStackTrace(); //Har redan kontrollerat. Behåller dubbletter ifall vi lägger till ett annat exception.
                } catch (NotLaterDateException e) {
                    e.printStackTrace(); //Har redan kontrollerat.
                } catch (TooSmallNumberException e) {
                    e.printStackTrace(); //Har redan kontrollerat.
                } catch (SameAccountException e) {
                    e.printStackTrace(); //Har redan kontrollerat.
                }

                System.out.println("Tryck enter för att återgå till huvudmenyn.");
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
    private static void createNewTransfer() {
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
            try {
                System.out.println("Ange kontonummret xxxx-xxxx-xxxx som du vill skicka ifrån: ");
                String fromAccountNumber = scanner.nextLine();
                if (allAccounts.containsKey(fromAccountNumber)) {
                    Account fromAccount = allAccounts.get(fromAccountNumber);
                    System.out.println("Ange kontonummret xxxx-xxxx-xxxx som du vill skicka till: ");
                    String toAccountNumber = scanner.nextLine();
                    if (allAccounts.containsKey(toAccountNumber)) {
                        Account toAccount = allAccounts.get(toAccountNumber);
                        System.out.println("Hur mycket pengar vill du skicka över: ");
                        String amount = scanner.nextLine();
                        if (fromAccount.directTransfer(toAccount, UnitConversion.convertFromSek(amount))) {
                            System.out.println("Pengarna överfördes till: "
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
            } catch (NonNumericalException e) {
                System.out.println("Felaktig inmatning. Beloppet måste vara ett tal.");
            } catch (NumberNotInBoundsException e) {
                System.out.println("Felaktig inmatning. Kan inte hantera för stora eller små tal. Kan inte hantera tal med mer än två decimaler.");
            }
        }
        System.out.println("Tryck enter för att återgå till huvudmenyn.");
        scanner.nextLine();
    }

    /**
     * Den här metoden loppar genom alla kunder för att hitta specifik kund och sen hitta konton som
     * tillhör den. Sen väljer man kontot som man vill göra ett uttag från.
     */
    private static void withdrawMoney() {
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
            try {
                System.out.println("Ange kontonummret xxxx-xxxx-xxxx som du vill ta ut pengarna ifrån: ");
                String accountNumber = scanner.nextLine();

                for (Account thisAccount : allAccounts.values()) {
                    if (thisAccount.getAccountNumber().equals(accountNumber)) {
                        System.out.println("Hur mycket vill du ta ut från kontot: ");
                        String amount = scanner.nextLine();
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
            } catch (NonNumericalException e) {
                System.out.println("Felaktig inmatning. Beloppet måste vara ett tal.");
            } catch (NumberNotInBoundsException e) {
                System.out.println("Felaktig inmatning. Kan inte hantera för stora eller små tal. Kan inte hantera tal med mer än två decimaler.");
            }
        }
        System.out.println("Tryck enter för att återgå till huvudmenyn.");
        scanner.nextLine();
    }

    /**
     * Den här metoden loppar genom alla kunder för att hitta specifik kund och sen hitta konton som
     * tillhör den. Sen väljer man kontot som man vill göra insättning i.
     */
    private static void depositeMoney() {
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
            try {
                System.out.println("Ange kontonummret xxxx-xxxx-xxxx som du vill sätta in pengarna på: ");
                String accountNumber = scanner.nextLine();

                for (Account thisAccount : allAccounts.values()) {
                    if (thisAccount.getAccountNumber().equals(accountNumber)) {
                        System.out.println("Hur mycket vill du sätta in: ");
                        String amount = scanner.nextLine();
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
            } catch (NonNumericalException e) {
                System.out.println("Felaktig inmatning. Beloppet måste vara ett tal.");
            } catch (NumberNotInBoundsException e) {
                System.out.println("Felaktig inmatning. Kan inte hantera för stora eller små tal. Kan inte hantera tal med mer än två decimaler.");
            }
        }
        System.out.println("Tryck enter för att återgå till huvudmenyn.");
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
                System.out.println("Tryck enter för att återgå till huvudmenyn.");
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
                        System.out.println("Tryck enter för att återgå till huvudmenyn.");
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

                String firstname;
                String lastname;
                String ownerID;

                System.out.println("Förnamn: ");
                firstname = scanner.nextLine();

                System.out.println("Efternamn: ");
                lastname = scanner.nextLine();

                System.out.println("Personnummer i formatet yyyymmdd-xxxx: ");
                ownerID = scanner.nextLine();

                if (firstname.isEmpty() || lastname.isEmpty() || ownerID.isEmpty()) {
                    System.out.println("Vänligen fyll i samtliga uppgifter! ");
                } else {

                    if (allCustomers.containsKey(ownerID)) {
                        System.out.println("Tyvärr, den kunden finns redan i systemet.\nVänligen försök igen!\n");
                    } else {

                        String password;
                        while (true) {
                            System.out.println("Ange ett lösenord som är minst 4 tecken långt: ");
                            password = scanner.nextLine();

                            System.out.println("Upprepa lösenordet: ");
                            String passwordRepeat = scanner.nextLine();
                            if (password.equals(passwordRepeat)) {
                                break;
                            } else {
                                System.out.println("Du måste ange samma lösenord två gånger.");
                            }
                        }
                        Customer newCustomer = new Customer(firstname, lastname, ownerID, password);
                        allCustomers.put(ownerID, newCustomer);

                        System.out.println("Nu skapas en ny kund i systemet:\n"
                                + "Förnamn: "
                                + newCustomer.getFirstName() + "\n"
                                + "Efternamn: "
                                + newCustomer.getLastName() + "\n"
                                + "Personnummer: "
                                + newCustomer.getOwnerID());
                        keepgoing = false;

                        System.out.println("Tryck enter för att återgå till huvudmenyn.");
                        scanner.nextLine();
                    }
                }
            } catch (InvalidNameException invalidNameException) {
                System.out.println("Ogiltigt namn\nVänligen försök igen!\n");
            } catch (InvalidOwnerIDException invalidOwnerIDException) {
                System.out.println("Ogiltig personnummer\nVänligen försök igen!\n");
            } catch (InvalidPasswordException e) {
                System.out.println("Ogiltig lösenord\nVänligen försök igen!\n");
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
            totalBalance = totalBalance.add(BigInteger.valueOf(account.getBalance()));
        }
        System.out.println("Det finns " + UnitConversion.convertToSek(totalBalance.toString()) + " kr i kassavalvet.\n");
    }
}