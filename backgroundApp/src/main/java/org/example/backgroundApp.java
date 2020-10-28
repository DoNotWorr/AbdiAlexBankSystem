package org.example;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class backgroundApp {

    //allCustomers behövs inte?
    public static HashMap<String, Customer> allCustomers = FileService.INSTANCE.loadCustomers();

    public static HashMap<String, Account> allAccounts = FileService.INSTANCE.loadAccounts();
    public static ArrayList<Transfer> allTransfers = FileService.INSTANCE.loadTransfers();

    public static void main(String[] args) {
        final long monthsBeforeDeletingHistory = 3; //Månader innan historik raderas

        //TEST SKAPA KUNDER v
        Customer c1 = null;
        Customer c2 = null;
        try {
            c1 = new Customer("Alex", "Wiklund", "19920317-0577");
            c2 = new Customer("Abdi", "Hussein", "19920317-0577");
            allCustomers.put(c1.getOwnerID(), c1);
            allCustomers.put(c2.getOwnerID(), c2);
        } catch (InvalidNameException e) {
            e.printStackTrace();
        } catch (InvalidOwnerIDException e) {
            e.printStackTrace();
        }

        Account a1 = new Account("Alex konto", c1); //kontonummer "5593 1426 6562"
        Account a2 = new Account("Abdis konto", c2); //kontonummer "5518 0751 1257"
        allAccounts.put(a1.getAccountNumber(), a1);
        allAccounts.put(a2.getAccountNumber(), a2);



        Transfer t1 = new Transfer("5593 1426 6562", "5518 0751 1257", 100, LocalDate.parse("2020-10-28"));
        Transfer t2 = new Transfer("5593 1426 6562", "5518 0751 1257", 100, LocalDate.parse("2020-07-28"));
        Transfer t3 = new Transfer("5593 1426 6562", "5518 0751 1257", 100, LocalDate.parse("2020-07-27"));
        Transfer t4 = new Transfer("5593 1426 6562", "5518 0751 1257", 100, LocalDate.parse("2020-10-29"));
        Transfer t5 = new Transfer("5593 1426 6562", "5518 0751 1257", 100, LocalDate.parse("2020-10-30"));
        Transfer t6 = new Transfer("5593 1426 6562", "5518 0751 1257", 100, LocalDate.parse("2020-10-27"));
        allTransfers.add(t1);
        allTransfers.add(t2);
        allTransfers.add(t3);
        allTransfers.add(t4);
        allTransfers.add(t5);
        allTransfers.add(t6);
        //TEST SKAPA KUNDER ^

        tempSortAndPrint("Har skapat kunder"); //TEST SKRIVA UT

        //Sortera lista på transferDate, tidigast datum först
        Collections.sort(allTransfers, (a, b) -> a.getTransferDate().compareTo(b.getTransferDate()));

        //Tar bort index 0 från listan så länge index 0 har transferDate som är mer än X månader tillbaka i tiden (monthsBeforeDeletingHistory).
        boolean keepDeleting = true;
        while (keepDeleting) {
            if (allTransfers.get(0).getTransferDate().plusMonths(monthsBeforeDeletingHistory).isBefore(LocalDate.now())) {
                allTransfers.remove(0);
            } else {
                keepDeleting = false;
            }
        }

        tempSortAndPrint("Har tagit bort alla som är äldre än 3 månader"); //TEST SKRIVA UT

        for(Transfer transfer : allTransfers) {
            //Om transferDate ska inträffa idag eller tidigare
            if (transfer.getTransferDate().isBefore(LocalDate.now().plusDays(1))) {
                //Programmet hanterar enbart bankuppdragt som har status "pending"
                if (transfer.getStatus() == Transfer.TransferStatus.PENDING) {
                    //Försök göra betalning

                    transfer.getFromAccountNumber(); //todo fortsätt



                }
            } else {
                //Om transferDate ska inträffa imorgon eller senare så avbryts loopen
                break;
            }
        }

        //Loopa igenom transfer-lista
        //a. radera alla som är mer än 3 månader gamla
        //b. behandla bankuppdrag med dagens datum
        //
        //COMPLETED: Hoppa över
        //CANCELLED: Hoppa över
        //FAILED: Hoppa över
        //PENDING: Försök göra överföring
        // * Om pengar fattas på kontot, markera som FAILED och gå vidare
        // * Om mottagare inte hittas, markera som FAILED och gå vidare
        // * Annars gör överföring och markera som COMPLETED, gå vidare
        //c. avsluta loop när transferDate är morgondagens datum


    }

    public static void tempSortAndPrint(String message) {
        System.out.println(message);
        Collections.sort(allTransfers, (a, b) -> a.getTransferDate().compareTo(b.getTransferDate()));
        allTransfers.forEach(transfer -> {
            System.out.println("FromAcc: " + transfer.getFromAccountNumber()
                    + " ToAcc: " + transfer.getToAccountNumber()
                    + " Amount: " + transfer.getAmount()
                    + " Transferdate: " + transfer.getTransferDate()
                    + " Status: " + transfer.getStatus());
        });
    }
}
