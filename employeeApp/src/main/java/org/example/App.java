package org.example;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Abdi
 */
public class App {
    public static void main(String[] args) {
        HashMap<String, Customer> allCustomers = FileService.INSTANCE.loadCustomers(); //HashMap K: ownerID (String), V: customer (Customer)
        HashMap<String, Account> allAccounts = FileService.INSTANCE.loadAccounts(); //HashMap K: accountNumber (String), V: account (Account)
        ArrayList<Transfer> allTransfers = FileService.INSTANCE.loadTransfers();

        System.out.println("--------Välkommen till Newton bank--------");
        menuNavigation();

    }

    private static void menuNavigation() {
        boolean keepRunning = true;
        while (keepRunning) {

            System.out.println("[1] Skapa konto"
                    + "\n[2] Lista konto"
                    + "\n[3] Sätta in pengar på konto"
                    + "\n[4] Ta ut pengar från konto"
                    + "\n[5] Lägg upp betalningsuppdrag i en textfil"
                    + "\n[6] Ta bort betalningsuppdrag"
                    + "\n[7] Visa kassavalv"
                    + "\n[8] Gör överföring mellan två konton"
                    + "\n[0] Avsluta program");

            int menuChoice = Integer.parseInt(SingletonInput.getInstance().scanner.nextLine());
            //Skapade ett Singleton klass för att försöka fatta mig på hur det funkar och jag lyckades. Plon Jon har kollat genom koden och det är rätt.

            switch (menuChoice) {
                case 1:
                    //metod ör att skapa
                    break;

                case 2:
                    //Metod för att lista konto
                    break;

                case 3:
                    //Sätta in pengar på konto
                    break;

                case 4:
                    //Ta ut pengar från konto
                    break;

                case 5:
                    //Lägg upp betalningsuppdrag
                    break;

                case 6:
                    //Ta bort betalningsuppdrag
                    break;

                case 7:
                    //Visa kassavalv
                    break;

                case 8:
                    //Överföring mellan två konton
                    break;

                case 0:
                    //Avsluta program
                    keepRunning = false;
                    break;

                default:
                    System.out.println("Felaktig inmatning.\n");
                    break;
            }
        }
    }
}
