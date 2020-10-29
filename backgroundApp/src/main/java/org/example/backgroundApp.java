package org.example;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @author Alex
 */
public class backgroundApp {

    //todo allCustomers behövs inte?
    private static HashMap<String, Customer> allCustomers = FileService.INSTANCE.loadCustomers();

    private static HashMap<String, Account> allAccounts = FileService.INSTANCE.loadAccounts();
    private static ArrayList<Transfer> allTransfers = FileService.INSTANCE.loadTransfers();

    public static void main(String[] args) {
        long monthsBeforeDeletingHistory = 3; //Månader innan historik raderas


/*
        //TEST SKAPA KUNDER v
        Customer c1 = null;
        Customer c2 = null;
        try {
            c1 = new Customer("Alex", "Wiklund", "19920317-0577");
            c2 = new Customer("Abdi", "Hussein", "20000723-2382");
            allCustomers.put(c1.getOwnerID(), c1);
            allCustomers.put(c2.getOwnerID(), c2);
        } catch (InvalidNameException e) {
            e.printStackTrace();
        } catch (InvalidOwnerIDException e) {
            e.printStackTrace();
        }

        Account a1 = new Account("Alex konto", c1); //kontonummer "5579 3237 7830"
        Account a2 = new Account("Abdis konto", c2); //kontonummer "5505 5023 3139"
        allAccounts.put(a1.getAccountNumber(), a1);
        allAccounts.put(a2.getAccountNumber(), a2);

        System.out.println("Alex: " + a1.getAccountNumber());
        System.out.println("Abdi: " + a2.getAccountNumber());
*/

        Transfer t1 = new Transfer("5579 3237 7830", "5505 5023 3139", 100, LocalDate.parse("2020-10-28"));
        Transfer t2 = new Transfer("5579 3237 7830", "5505 5023 3139", 100, LocalDate.parse("2020-07-28"));
        Transfer t3 = new Transfer("5579 3237 7830", "5505 5023 3139", 100, LocalDate.parse("2020-07-27"));
        Transfer t4 = new Transfer("5579 3237 7830", "5505 5023 3139", 100, LocalDate.parse("2020-10-29"));
        Transfer t5 = new Transfer("5579 3237 7830", "5505 5023 3139", 100, LocalDate.parse("2020-10-30"));
        Transfer t6 = new Transfer("5579 3237 7830", "5505 5023 3139", 100, LocalDate.parse("2020-10-27"));
        allTransfers.add(t1);
        allTransfers.add(t2);
        allTransfers.add(t3);
        allTransfers.add(t4);
        allTransfers.add(t5);
        allTransfers.add(t6);
        //TEST SKAPA KUNDER ^



        tempSortAndPrint("Lista från början:"); //TEST SKRIVA UT

        //Sortera lista på transferDate, tidigast datum först
        Collections.sort(allTransfers, (a, b) -> a.getTransferDate().compareTo(b.getTransferDate()));

        deleteHistory(monthsBeforeDeletingHistory);

        tempSortAndPrint("Har tagit bort alla som är äldre än 3 månader:"); //TEST SKRIVA UT

        for (Transfer transfer : allTransfers) {

            //Om transferDate ska inträffa idag eller tidigare
            if (transfer.getTransferDate().isBefore(LocalDate.now().plusDays(1))) {

                //Programmet hanterar enbart bankuppdragt som har status "pending"
                if (transfer.getStatus() == Transfer.TransferStatus.PENDING) {

                    //Kontrollera att avsändaren och mottagarens konton existerar
                    if (allAccounts.containsKey(transfer.getFromAccountNumber()) && allAccounts.containsKey(transfer.getToAccountNumber())) {

                        //Kontrollera att det finns pengar att betala med
                        if (transfer.getAmount() <= allAccounts.get(transfer.getFromAccountNumber()).getBalance()) {
                            //todo bugtesta att kontroll av belopp fungerar

                            allAccounts.get(transfer.getFromAccountNumber()).directTransfer();
                            //transfer.setStatus(Transfer.TransferStatus.COMPLETED);
                            //todo se över när directTransfer() är färdig.
                            // Ta bort eventuella kontroller som redan sker i directTransfer.
                            // Använd return-value i directtransfer
                        } else {
                            transfer.setStatus(Transfer.TransferStatus.FAILED);
                        }
                    } else {
                        transfer.setStatus(Transfer.TransferStatus.FAILED);
                    }
                }
            } else {
                //Eftersom listan är sorterad så avbryts loopen när man kommit till bankuppdrag som ska genomföras imorgon eller senare.
                break;
            }
        }

        //Sparar listorna igen
        FileService.INSTANCE.saveAccounts(allAccounts);
        FileService.INSTANCE.saveTransfers(allTransfers);

        tempSortAndPrint("Alla borde vara markerade som FAILED pga felaktigt kontonr"); //todo ta bort alla tempSortAndPrint()
    }

    /**
     * Raderar gamla transaktioner ifrån historiken
     * @param monthsBeforeDeletingHistory antal månader som ska passera efter transaktionsdatum innan transaktionen tas bort ifrån historiken.
     */
    private static void deleteHistory(long monthsBeforeDeletingHistory) {
        boolean keepDeleting = true;
        //
        if (allTransfers.size() > 0) {
            while (keepDeleting) {
                if (allTransfers.get(0).getTransferDate().plusMonths(monthsBeforeDeletingHistory).isBefore(LocalDate.now())) {
                    allTransfers.remove(0);
                } else {
                    keepDeleting = false;
                }
            }
        }
    }

    /**
     * Temporär metod för att skriva ut allTransfers. Används bara för att kolla steg för steg att backgroundApp fungerar som den ska
     * @param message meddelande före utskrift av allTransfers (till exempel "tog nyss bort alla transfers med belopp större än 500").
     */
    private static void tempSortAndPrint(String message) {
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
