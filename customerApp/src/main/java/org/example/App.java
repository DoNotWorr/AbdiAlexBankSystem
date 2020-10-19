package org.example;

import org.example.Account;
import org.example.Customer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Customer cust = new Customer("Alex", "Wiklund", "920317");
        Account acc1 = new Account("123123", cust);
        cust.addNewAccount("ännumerfel");
        cust.getYourAccounts().forEach(konto -> {
            System.out.println(konto);
        });
        cust.getYourAccounts().add("felfel");
        cust.getYourAccounts().forEach(konto -> {
            System.out.println(konto);
        });
        System.out.println();

    }
}
