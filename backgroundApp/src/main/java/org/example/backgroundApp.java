package org.example;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class backgroundApp {

    //allCustomers behövs inte?
    //public static HashMap<String, Customer> allCustomers = FileService.INSTANCE.loadCustomers();

    public static HashMap<String, Account> allAccounts = FileService.INSTANCE.loadAccounts();
    public static ArrayList<Transfer> allTransfers = FileService.INSTANCE.loadTransfers();

    public static void main(String[] args) {
        final long monthsBeforeDeletingHistory = 3; //Månader innan historik raderas

        //testkod nedan
        Transfer t1 = new Transfer("123345", "123123", 300, LocalDate.parse("2020-07-28"));
        allTransfers.add(t1);
        Transfer t2 = new Transfer("123345", "123123", 300, LocalDate.parse("2020-07-27"));
        allTransfers.add(t2);
        Transfer t3 = new Transfer("123345", "123123", 300, LocalDate.parse("2020-07-26"));
        allTransfers.add(t3);

        System.out.println("Nuvarande datum, sorterade");
        Collections.sort(allTransfers, (a, b) -> a.getTransferDate().compareTo(b.getTransferDate()));
        allTransfers.forEach(transfer -> {
            System.out.println("FromAcc: " + transfer.getFromAccountNumber() + " ToAcc: " + transfer.getToAccountNumber() + " Amount: " + transfer.getAmount() + " Transferdate: " + transfer.getTransferDate());
        });

        //testkod ovan

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
        for(Transfer transfer : allTransfers) {
            //Om transferDate ska inträffa idag eller tidigare
            if (transfer.getTransferDate().isBefore(LocalDate.now().plusDays(1))) {
                //Hanterar enbart betalningar som är PENDING
                if (transfer.getStatus() == Transfer.TransferStatus.PENDING) {
                    //Försök göra betalning
                    System.out.println("Gör betalning");
                    System.out.println("FromAcc: " + transfer.getFromAccountNumber() + " ToAcc: " + transfer.getToAccountNumber() + " Amount: " + transfer.getAmount() + " Transferdate: " + transfer.getTransferDate());


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
}
