package org.example.DataClasses;

import org.example.Exceptions.NotLaterDateException;
import org.example.Exceptions.NullToAccountException;
import org.example.Exceptions.SameAccountException;
import org.example.Exceptions.TooSmallNumberException;
import org.example.UnitConversion;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;

/**
 * @author Abdi, Alex
 * Abdi skrev klassen. Alex la till kontroller i metoder som använder setBalance() för att man inte ska kunna skapa tal som inte kan hanteras av en long.
 */
public class Account {

    private String accountName;
    private String accountNumber;
    private String ownerID;
    private long balance; //Kan inte vara ett negativt tal pga
    private int previousTransaction;

    /**
     * @param accountName Kontonamn.
     * @param customer    kundens ownerID/personnummer.
     */
    public Account(String accountName, Customer customer) {
        this.accountName = accountName;
        this.accountNumber = generateAccountNumber();
        this.ownerID = customer.getOwnerID();
    }

    /**
     * Metod för att få fram namnet på kontot.
     *
     * @return retunerar kontots namn.
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Metod som får fram kundens kontonummer.
     *
     * @return AccountNumber/Kontonummer (String)
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Den här metoden ska generera kontonummer som blir nyckeln i Hashmapen.
     *
     * @return ska retunera kontonummmret.
     */
    public static String generateAccountNumber() {
        return generateAccountNumber("55");
    }

    /**
     * Metod fär att få fram kundens personnummer.
     *
     * @return OwnerID/personnummer (String)
     */
    public String getOwnerID() {
        return ownerID;
    }

    /**
     * Metod som får fram kundens saldo i kontot.
     *
     * @return saldo på konto i ören.
     */
    public long getBalance() {
        return balance;
    }

    /**
     * Metod för att skapa
     *
     * @param amount beloppet som personen ska sätta in i kontot.
     * @return den retunerar true om det gick att sätta in pengar i kontot eller false om det inte gick.
     * @author Abdi, Alex
     * Abdi skrev metoden. Alex la till kontroll för att hantering
     */

    public boolean depositMoney(long amount) {
        //Beloppet måste vara större än 0
        if (amount > 0) {
            //Long.MAX_VALUE - this.getBalance() ger det maximala beloppet som kan sättas in utan att orsaka overflow
            if (amount <= Long.MAX_VALUE - this.getBalance()) {
                this.setBalance(this.getBalance() + amount);
                return true;
            }
        }
        return false;
    }

    /**
     * Metod för att få fram rätt balance för att kunna ändra saldot i kontot.
     */
    private void setBalance(long balance) {
        this.balance = balance;
    }

    /**
     * @param amount beloppet som personen ska ta ut från kontot.
     * @return den retunerar true om det gick att göra ett uttag från kontot eller false om det inte gick.
     */
    public boolean withdrawMoney(long amount) {
        //Belopp är större än noll
        if (amount > 0) {
            //Belopp är mindre än eller lika med saldo på kontot
            if (amount <= this.balance) {
                this.setBalance(this.balance - amount);
                return true;
            }
        }
        return false;
    }

    /**
     * Den här metoden visar historiken i alla insättningar och kontantuttag.
     */
    public void previousTransaction() {
        if (previousTransaction > 0) {
            System.out.println("Insättningar: " + previousTransaction);
        } else if (previousTransaction < 0) {
            System.out.println("Kontantuttag: " + Math.abs(previousTransaction));// Använder metoden Math för att kunna se uttraget som positivt istället för minus 1000kr
        } else {
            System.out.println("inget transaktionen har hänt än ");
        }
    }

    /**
     * Gör en direktöverföring om det är möjligt. Kontrollerar att både mottagare klarar av att ta emot pengar och avsändare klarar av att skicka pengar.
     *
     * @param toAccount mottagarens konto
     * @param amount    belopp i öre
     * @return True om direktöverföring genomfördes, annars false.
     */
    public boolean directTransfer(Account toAccount, long amount) {
        //toAccount kan inte vara null så går det inte att göra en överföring
        if (Objects.nonNull(toAccount)) {
            //Kontrollerar att mottagarkonto och avsändarkonto inte är samma konto
            if (!this.accountNumber.equals(toAccount.getAccountNumber())) {
                //Kontroll att avsändare klarar att skicka pengar
                if (amount > 0 && amount <= this.balance) {
                    //Kontroll att mottagare klarar av att ta emot pengar. Behöver inte kontrollera att belopp är större än 0 igen.
                    if (amount <= Long.MAX_VALUE - toAccount.getBalance()) {
                        this.setBalance(this.getBalance() - amount);
                        toAccount.setBalance(toAccount.getBalance() + amount);
                        return true;
                    }
                }
            }
        }
        //Om någon av kontrollerna inte klarades så returnerar metoden false
        return false;
    }

    /**
     * Metod för att skapa bankuppdrag.
     *
     * @param toAccount    kontonummer betalning ska ske till
     * @param amount       antal ören (inte SEK)
     * @param transferDate datum i format YYYY-MM-DD för överföringen
     * @return retunerar den nya Transfer objektet.
     * @author Alex
     */
    public Transfer addTransfer(Account toAccount, long amount, LocalDate transferDate) throws NullToAccountException, NotLaterDateException, TooSmallNumberException, SameAccountException {
        if (Objects.isNull(toAccount)) {
            throw new NullToAccountException("toAccount är null");
        }
        if (toAccount.getAccountNumber().equals(this.accountNumber)) {
            throw new SameAccountException("Kan inte lägga upp transfer till sitt eget konto");
        }
        if (amount <= 0) {
            throw new TooSmallNumberException("amount är 0 eller mindre");
        }
        if (transferDate.isBefore(LocalDate.now().plusDays(1))) {
            throw new NotLaterDateException("Datumet måste vara imorgon eller längre fram i tiden");
        }
        return new Transfer(this.accountNumber, toAccount.accountNumber, amount, transferDate);
    }

    /**
     * @param accountNumber ska generera kontonummer som börjar med 55
     * @author Abdi
     */
    public static String generateAccountNumber(String accountNumber) {
        //accountNumber = "55";
        Random value = new Random();

        // Generera 8 tal som ska ha 55 i början.
        int rad1 = value.nextInt(10);
        int rad2 = value.nextInt(10);
        accountNumber += rad1 + Integer.toString(rad2) + "-";

        int count = 0;
        int number = 0;
        StringBuilder accountNumberBuilder = new StringBuilder(accountNumber);
        for (int i = 0; i < 8; i++) {
            if (count == 4) {
                accountNumberBuilder.append("-");
                count = 0;
            } else
                number = value.nextInt(10);
            accountNumberBuilder.append(number);
            count++;
        }
        accountNumber = accountNumberBuilder.toString();
        return accountNumber;
    }

    /**
     * Metod för att skriva ut konton. Används i CustomerApp.
     *
     * @return formatet på utskriften
     */
    public String toString() {
        return this.accountName + "(" + this.getAccountNumber() + ")" + UnitConversion.convertToSek(this.getBalance()) + " kr";
    }
}


