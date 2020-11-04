package org.example;

import java.time.LocalDate;

/**
 * Transfer har fyra variabler (fromAccountNumber, toAccountNumber, amount, transferDate) som
 *
 * @author Alex
 */
public class Transfer {
    private String fromAccountNumber;
    private String toAccountNumber;
    private int amount;
    private LocalDate transferDate;
    private TransferStatus status;

    public enum TransferStatus {
        PENDING,
        COMPLETED,
        CANCELLED,
        FAILED
    }

    /**
     * //todo kolla med Jon om användning av accessmodifier
     * Konstruktor för att skapa bankuppdrag.
     *
     * @param fromAccountNumber kontot som pengar ska dras ifrån
     * @param toAccountNumber   kontot som pengar ska föras över till
     * @param amount            ören som ska föras över
     * @param transferDate      vilket datum överföringen ska görasgi
     */
    public Transfer(String fromAccountNumber, String toAccountNumber, int amount, LocalDate transferDate) {
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
        this.transferDate = transferDate;
        this.status = TransferStatus.PENDING;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public TransferStatus getStatus() {
        return status;
    }

    /**
     * Ändrar status på en Transfer-instans. En instans kan ändra status enbart en gång.
     *
     * @param newStatus den nya statusen
     * @return True om status ändrades. False om status inte är tillåten. Den nuvarande statusen måste vara PENDING för att en ändring ska vara tillåten
     */
    public boolean setStatus(TransferStatus newStatus) {
        //Det går inte att ändra status till pending
        if (newStatus != TransferStatus.PENDING) {
            //Den nuvarande statusen måste vara pending för att kunna ändra status
            if (this.getStatus() == TransferStatus.PENDING) {
                this.status = newStatus;
                return true;
            }
        }
        return false;
    }
}
