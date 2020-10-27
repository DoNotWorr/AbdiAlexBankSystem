package org.example;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

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
    private static int balance;
    public String accountName;
    private String accountNumber;
    private String ownerID;


    public Account(String accountName, String accountNumber, Customer customer, int balance) {
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        //customer.addNewAccount(accountNumber);
        this.ownerID = customer.getOwnerID();
        this.balance = 0;

    }

    /**
     * Enbart vid skapande av ny konto. Aldrig vid inläsning av konto från fil.
     *
     * @param accountName Kontonamn
     * @param customer    kundens ownerID/personnummer
     * @param balance     belopp i kontot
     */
    public Account(String accountName, Customer customer, int balance) {
        this.accountName = accountName;
        this.accountNumber = getAccountNumber();
        this.ownerID = customer.getOwnerID();
        this.balance = 0;
    }

    /**
     * @return AccountNumber/Kontonummer (String)
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Den här metoden ska generera kontonummer som sen ska kopplas till ownerID
     * @return ska retunera kontonummmret.
     */
    public static String generateAccountNumber(){

        return generateAccountNumber("55");
    }

    /**
     * @return OwnerID/personnummer (String)
     */
    public String getOwnerID() {
        return ownerID;
    }

    /**
     *@return saldo på konto i ören.
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
    public static int depositMoney(){
        return depositMoney();
    }

    /**
     * Metod för att sätta in pengar.
     */
    public static void depositMoney(int amount) {
       amount = SingletonInput.getInstance().scanner.nextInt();
        if (balance != 0) {
            balance = balance + amount;
        }
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

    /**
     *
     * @Author Abdi
     * @param accountNumber ska generera kontonummer som börjar med 55
     *
     */
    public static String generateAccountNumber(String accountNumber) {
        //accountNumber = "55";
        Random value = new Random();

        // Generera 8 tal som ska 55 i början.
        int rad1 = value.nextInt(10);
        int rad2 = value.nextInt(10);
        accountNumber += Integer.toString(rad1) + Integer.toString(rad2) + " ";

        int count = 0;
        int number = 0;
        for (int i = 0; i < 8; i++) {
            if (count == 4) {
                accountNumber += " ";
                count = 0;
            } else
                number = value.nextInt(10);
            accountNumber += Integer.toString(number);
            count++;
        }
        return accountNumber;
    }
}


