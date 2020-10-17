package org.example;

import java.time.LocalDate;

//frånkonto;tillkonto,belopp,överföringsdatum
public class Transfer {
    private String fromAccount;
    private String toAccount;
    private int amount;
    private LocalDate transferDate;

    /**
     * Protected Transfer är tänkt att användas i Account när överföring skapas.
     * @param fromAccount kontot som pengar ska dras ifrån
     * @param toAccount kontot som pengar ska föras över till
     * @param amount ören som ska föras över
     * @param transferDate vilket datum överföringen ska göras
     */
    protected Transfer(String fromAccount, String toAccount, int amount, LocalDate transferDate) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.transferDate = transferDate;
    }
}
