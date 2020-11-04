package org.example;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 *
 * @author Alex
 */
public class backgroundApp {


    //customers.txt innehåll:  {"20000723-2382":{"firstName":"Abdi","lastName":"Hussein","ownerID":"20000723-2382"},"19920317-0577":{"firstName":"Alex","lastName":"Wiklund","ownerID":"19920317-0577"}}
    //accounts.txt innehåll: {"5536-5044-4718":{"accountName":"Abdis konto","accountNumber":"5536-5044-4718","ownerID":"20000723-2382","balance":10000,"previousTransaction":0},"5521-1299-9573":{"accountName":"Alex konto","accountNumber":"5521-1299-9573","ownerID":"19920317-0577","balance":10000,"previousTransaction":0}}
    //transfer.txt innehåll: [{"fromAccountNumber":"5521-1299-9573","toAccountNumber":"5536-5044-4718","amount":100,"transferDate":{"year":2020,"month":8,"day":3},"status":"PENDING"},{"fromAccountNumber":"5521-1299-9573","toAccountNumber":"5536-5044-4718","amount":100000,"transferDate":{"year":2020,"month":8,"day":4},"status":"PENDING"},{"fromAccountNumber":"5521-1299-9573","toAccountNumber":"5536-5044-4718","amount":100,"transferDate":{"year":2020,"month":8,"day":5},"status":"PENDING"},{"fromAccountNumber":"5521-1299-9573","toAccountNumber":"4536-5044-4718","amount":100,"transferDate":{"year":2020,"month":11,"day":3},"status":"PENDING"},{"fromAccountNumber":"5521-1299-9573","toAccountNumber":"5536-5044-4718","amount":100,"transferDate":{"year":2020,"month":11,"day":4},"status":"CANCELLED"},{"fromAccountNumber":"5521-1299-9573","toAccountNumber":"5536-5044-4718","amount":100,"transferDate":{"year":2020,"month":11,"day":5},"status":"PENDING"}]

    //Ger följande tester:
    //2020-08-03 -> Deleted  3 months old
    //2020-08-04 -> False: Belopp
    //2020-08-05 -> True på alla -> COMPLETED
    //2020-11-03 -> False: mottagarens konton existerar
    //2020-11-04 -> False: "pending"
    //2020-11-05 -> False: Om transferDate idag eller tidigare

    private static HashMap<String, Account> allAccounts = FileService.INSTANCE.loadAccounts();
    private static ArrayList<Transfer> allTransfers = FileService.INSTANCE.loadTransfers();

    public static void main(String[] args) {
        final long monthsBeforeDeletingHistory = 3; //Månader innan gammal historik raderas
        Collections.sort(allTransfers, Comparator.comparing(Transfer::getTransferDate)); //Samtlig kod nedanför bygger på att listan är sorterad.

        //Raderar gammal historik
        boolean keepDeleting = true;
        if (allTransfers.size() > 0) {
            while (keepDeleting) {
                if (allTransfers.get(0).getTransferDate().plusMonths(monthsBeforeDeletingHistory).isBefore(LocalDate.now())) {
                    allTransfers.remove(0);
                } else {
                    keepDeleting = false;
                }
            }
        }

        //Hanterar resterande bankuppdrag
        for (Transfer transfer : allTransfers) {
            //Om transferDate inträffar före imorgon (idag eller tidigare)
            if (transfer.getTransferDate().isBefore(LocalDate.now().plusDays(1))) {
                //Hanterar status pending och struntar i cancelled, failed, complete
                if (transfer.getStatus() == Transfer.TransferStatus.PENDING) {
                    //Om avsändaren och mottagarens konton existerar
                    if (allAccounts.containsKey(transfer.getFromAccountNumber()) && allAccounts.containsKey(transfer.getToAccountNumber())) {
                        //Om det finns täckning för betalningen på kontot
                        if (transfer.getAmount() <= allAccounts.get(transfer.getFromAccountNumber()).getBalance()) {
                            //todo Kolla vilka kontroller som verkligen behöver göras och vilka som redan görs i directTransfer()
                            if (allAccounts.get(transfer.getFromAccountNumber()).directTransfer(allAccounts.get(transfer.getToAccountNumber()), transfer.getAmount())) {
                                transfer.setStatus(Transfer.TransferStatus.COMPLETED);
                            } else {
                                transfer.setStatus(Transfer.TransferStatus.FAILED);
                            }
                        } else {
                            transfer.setStatus(Transfer.TransferStatus.FAILED);
                        }
                    } else {
                        transfer.setStatus(Transfer.TransferStatus.FAILED);
                    }
                }
            } else {
                //Eftersom listan är sorterad så avbryts loopen när man kommit till bankuppdrag som ska genomföras imorgon eller längre fram.
                break;
            }
        }

        FileService.INSTANCE.saveAccounts(allAccounts);
        FileService.INSTANCE.saveTransfers(allTransfers);
    }
}
