package org.example;

import java.time.LocalDate;

//frånkonto;tillkonto,belopp,överföringsdatum
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
     * Transfer är tänkt att användas i Account när överföring skapas.
     * @param fromAccountNumber kontot som pengar ska dras ifrån
     * @param toAccountNumber kontot som pengar ska föras över till
     * @param amount ören som ska föras över
     * @param transferDate vilket datum överföringen ska göras
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
     * //todo kom på bra sätt att kontrollera så man inte ändrar status felaktigt, kanske return boolean som visar om det gick eller inte?
     * @param newStatus
     */

    public void setStatus(TransferStatus newStatus) {
        this.status = newStatus;
    }
}
