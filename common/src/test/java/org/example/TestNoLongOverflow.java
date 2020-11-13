package org.example;

import org.example.DataClasses.Account;
import org.example.DataClasses.Customer;
import org.example.DataClasses.Transfer;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class TestNoLongOverflow {

    /**
     * Account.deposit()
     */
    @Test
    public void testOverflow() {
        long amount = 1;
        long amount2 = Long.MAX_VALUE;
        Customer testCustomer = null;
        try {
            testCustomer = new Customer("Test", "Testsson", "20170101-2393", "testpassword");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Account testAccount = new Account("testkonto", testCustomer);
        Assert.assertTrue(testAccount.depositMoney(amount));
        Assert.assertFalse(testAccount.depositMoney(amount2));
    }

    /**
     * Account.deposit()
     */
    @Test
    public void testNegative() {
        long amount = -1;
        Customer testCustomer = null;
        try {
            testCustomer = new Customer("Test", "Testsson", "20170101-2393", "testpassword");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Account testAccount = new Account("testkonto", testCustomer);
        Assert.assertFalse(testAccount.depositMoney(amount));
    }

    /**
     * Account.deposit()
     */
    @Test
    public void testMax() {
        long amount = Long.MAX_VALUE;
        long amount2 = 1;
        Customer testCustomer = null;
        try {
            testCustomer = new Customer("Test", "Testsson", "20170101-2393", "testpassword");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Account testAccount = new Account("testkonto", testCustomer);
        Assert.assertTrue(testAccount.depositMoney(amount));
        Assert.assertFalse(testAccount.depositMoney(amount2));
    }
}
