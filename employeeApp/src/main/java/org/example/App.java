package org.example;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Abdi
 */
public class App {
    public static HashMap<String, Customer> allCustomers = FileService.INSTANCE.loadCustomers(); //HashMap K: ownerID (String), V: customer (Customer)
    public static HashMap<String, Account> allAccounts = FileService.INSTANCE.loadAccounts(); //HashMap K: accountNumber (String), V: account (Account)
    public static ArrayList<Transfer> allTransfers = FileService.INSTANCE.loadTransfers();

    public static void main(String[] args) {
        System.out.println("--------Välkommen till Newton bank--------");

        menuNavigation();

    }

    private static void menuNavigation() {
        boolean keepRunning = true;
        while (keepRunning) {

            System.out.println("[1] Skapa kund"
                    + "\n[2] Skapa konto"
                    + "\n[3] Lista konto"
                    + "\n[4] Sätta in pengar på konto"
                    + "\n[5] Ta ut pengar från konto"
                    + "\n[6] Lägg upp betalningsuppdrag i en textfil"
                    + "\n[7] Ta bort betalningsuppdrag"
                    + "\n[8] Visa kassavalv"
                    + "\n[9] Gör direktöverföring"
                    + "\n[0] Avsluta program");

            int menuChoice = Integer.parseInt(SingletonInput.getInstance().scanner.nextLine());
            //Skapade ett Singleton klass för att försöka fatta mig på hur det funkar och jag lyckades. Plon Jon har kollat genom koden och det är rätt.

            switch (menuChoice) {
                case 1:
                    //skapa kund
                    break;
                case 2:
                    //skapa konto
                    break;

                case 3:
                    //lista konto
                    break;

                case 4:
                    //Sätta in pengar på konto
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

    private static void inspectSafe() {
        allAccounts.forEach((ownerID, account) -> {
            System.out.println(ownerID);
        });
    }
}
