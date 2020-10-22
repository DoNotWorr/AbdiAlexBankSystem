package org.example;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Abdi
 *
 */

// Metod addTransfer (från konto: till konto, belopp, överföringsdatum)
    // metod removeTransfer
    // metod insertMoney
    // metod withrawMoney
    // Kolla så att personen finns

public class Account {

    private String accountNumber;
    private String ownerID;
    private int balance;

    /**
     * @param accountNumber kundens kontonummer
     * @param customer      kundens ownerID/personnummer
     * @param balance       belopp i kontot
     */

    public Account(String accountNumber, Customer customer, int balance) {
        this.accountNumber = accountNumber;
        customer.addNewAccount(accountNumber);
        this.ownerID = customer.getOwnerID();
        this.balance = balance;
        this.balance = 0; // vet inte vad du menar här Alex...
    }
    
    /**
     * @return AccountNumber/Kontonummer (String)
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * @return OwnerID/personnummer (String)
     */
    public String getOwnerID() {
        return ownerID;
    }

    /**
     *@return Balace/Belopp på kontot (int)
     */
    public int getBalance() {
        return balance;
    }

    /**
     *@author Abdi
     * Metod för att ta ut pengar.
     */
    public void withdraMoney(){
        //  System.out.println("Enter the Amount you want to withdraw: ");
        //  int amount = Scanner.nextLine()
       //   if ( balance >= amount){
       //   balance = balance - amount;
       //   else {
       //   System.out.println("Less Balance...Transaction faild");
    }

    /**
     * Metod för att sätta in pengar.
     */
    public void depositMoney(int amoun){
        //    System.out.println("Enter the Amount you want to Deposit: );
        //    int amount = Scanner.nextInt();
        //     if (balance != 0){
        //     balance = balance + amount;
    }

    // TODO Vi kanske behöver skapa en till metod som vi kan skriva ut sen hur mycket penagr som man har tagit ut eller lagt in.

    /**
     * Metod för att direktövering.
     * ska vi skapa metoden här eller i Transfer klassen ? // vi behöver snacka om det.
     */
    public void directTransfer(){

    }

    /**
     * Metod för att skapa bankuppdrag.
     * @author Alex
     * @param toAccount kontonummer betalning ska ske till
     * @param amount antal ören (inte SEK)
     * @param transferDate datum i format YYYY-MM-DD för överföringen
     */
    public void addTransfer(String toAccount, int amount, LocalDate transferDate) {
        Transfer transfer = new Transfer(this.accountNumber, toAccount, amount, transferDate);
    }

    public void printAccount(ArrayList<String> allAccounts) {
        //for loop som går igenom alla konton
        //printar ut info som vi vill printa ut för varje konto
    }
}
