package org.example;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Hämtar gemensamma klasser från Common-modulen
 *
 * @author Alex
 */
public class backgroundApp {
    //Läser från fil
    private static HashMap<String, Account> allAccounts = FileService.INSTANCE.loadAccounts();
    private static ArrayList<Transfer> allTransfers = FileService.INSTANCE.loadTransfers();

    /**
     * Raderar gammal historik och hanterar resterande betalningsuppdrag. Programmet bygger på att listan är sorterad under hela körningen.
     */
    public static void main(String[] args) {
        //Månader innan gammal historik raderas
        final long monthsBeforeDeletingHistory = 3;

        //Samtlig kod nedanför bygger på att listan är sorterad.
        allTransfers.sort(Comparator.comparing(Transfer::getTransferDate));

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
                        //Låter directTransfer() göra resterande kontroller
                        if (allAccounts.get(transfer.getFromAccountNumber()).directTransfer(allAccounts.get(transfer.getToAccountNumber()), transfer.getAmount())) {
                            transfer.setStatus(Transfer.TransferStatus.COMPLETED);
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

        //Sparar till fil
        FileService.INSTANCE.saveAccounts(allAccounts);
        FileService.INSTANCE.saveTransfers(allTransfers);
    }
}
