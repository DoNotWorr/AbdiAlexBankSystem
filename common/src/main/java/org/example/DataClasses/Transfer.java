package org.example.DataClasses;

import org.example.UnitConversion;

import java.time.LocalDate;

/**
 * Fyra variabler (fromAccountNumber, toAccountNumber, amount, transferDate) är obligatoriska i klassens enda konstruktor. Dessa fyra saknar setter-metoder.
 * Den femte variabeln status, har både getter och setter.
 *
 * @author Alex
 */
public class Transfer {
    private String fromAccountNumber;
    private String toAccountNumber;
    private long amount;
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
     * @param transferDate      vilket datum överföringen ska göras
     */
    protected Transfer(String fromAccountNumber, String toAccountNumber, long amount, LocalDate transferDate) {
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
        this.transferDate = transferDate;
        this.status = TransferStatus.PENDING;
    }

    /**
     * Getter fromAccountNumber
     *
     * @return kontonumret som pengar ska skickas ifrån
     */
    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    /**
     * Getter toAccountNumber
     *
     * @return kontnumret som pengar ska göras till
     */
    public String getToAccountNumber() {
        return toAccountNumber;
    }

    /**
     * @return
     */
    public long getAmount() {
        return amount;
    }

    /**
     * Getter transferDate
     *
     * @return Datum som pengarna ska skickas
     */
    public LocalDate getTransferDate() {
        return transferDate;
    }

    /**
     * Getter transferStatus
     *
     * @return status på transfer
     */
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

    /**
     * Metod för utskrift av överföring. Används i CustomerApp-modulen.
     *
     * @return Utskrift i formatet "XYZ kr från AAAA-AAAA-AAAA till BBBB-BBBB-BBBB den YYYY-MM-DD"
     */
    public String toString() {
        return UnitConversion.convertToSek(this.getAmount()) + " kr från " + this.getFromAccountNumber() + " till " + this.getToAccountNumber() + " den " + this.getTransferDate() + "(" + this.getStatus() + ")";
    }
}
