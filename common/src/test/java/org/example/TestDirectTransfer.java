package org.example;

import org.example.DataClasses.*;
import org.junit.Assert;
import org.junit.Test;

public class TestDirectTransfer {
    public String senderPassword = "1234";
    public String receiverPassword = "1234";


    @Test
    public void testSuccessful() {
        long amountInSenderAccount = 10000;
        long amountToSend = 10000;
        Customer senderCustomer = null;
        Customer receiverCustomer = null;
        try {
            senderCustomer = new Customer("Sender", "Sendersson", "20170111-2391", senderPassword);
            receiverCustomer = new Customer("Receiver", "Receiversson", "20170112-2382", receiverPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Account senderAccount = new Account("SenderAccount", senderCustomer);
        Account receiverAccount = new Account("ReceiverAccount", receiverCustomer);

        senderAccount.depositMoney(amountInSenderAccount);

        Assert.assertTrue(senderAccount.directTransfer(receiverAccount, amountToSend));
    }

    @Test
    public void testNullToAccount() {
        long amountInSenderAccount = 10000;
        long amountToSend = 10000;
        Customer senderCustomer = null;
        Customer receiverCustomer = null;
        try {
            senderCustomer = new Customer("Sender", "Sendersson", "20170111-2391", senderPassword);
            receiverCustomer = new Customer("Receiver", "Receiversson", "20170112-2382", receiverPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Account senderAccount = new Account("SenderAccount", senderCustomer);
        Account receiverAccount = null;


        senderAccount.depositMoney(amountInSenderAccount);

        Assert.assertFalse(senderAccount.directTransfer(receiverAccount, amountToSend));
    }

    @Test
    public void testNotTheSameToAccount() {
        long amountInSenderAccount = 10000;
        long amountToSend = 10000;
        Customer senderCustomer = null;
        Customer receiverCustomer = null;
        try {
            senderCustomer = new Customer("Sender", "Sendersson", "20170111-2391", senderPassword);
            receiverCustomer = new Customer("Receiver", "Receiversson", "20170112-2382", receiverPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Account senderAccount = new Account("SenderAccount", senderCustomer);
        Account receiverAccount = new Account("ReceiverAccount", receiverCustomer);

        senderAccount.depositMoney(amountInSenderAccount);

        Assert.assertFalse(senderAccount.directTransfer(senderAccount, amountToSend));
    }

    @Test
    public void testNotMoreThanZeroAmount() {
        long amountInSenderAccount = 10000;
        long amountToSend = 0;
        Customer senderCustomer = null;
        Customer receiverCustomer = null;
        try {
            senderCustomer = new Customer("Sender", "Sendersson", "20170111-2391", senderPassword);
            receiverCustomer = new Customer("Receiver", "Receiversson", "20170112-2382", receiverPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Account senderAccount = new Account("SenderAccount", senderCustomer);
        Account receiverAccount = new Account("ReceiverAccount", receiverCustomer);

        senderAccount.depositMoney(amountInSenderAccount);

        Assert.assertFalse(senderAccount.directTransfer(receiverAccount, amountToSend));
    }

    @Test
    public void testAmountBiggerThanBalanceCase() {
        long amountInSenderAccount = 10000;
        long amountToSend = 10001;
        Customer senderCustomer = null;
        Customer receiverCustomer = null;
        try {
            senderCustomer = new Customer("Sender", "Sendersson", "20170111-2391", senderPassword);
            receiverCustomer = new Customer("Receiver", "Receiversson", "20170112-2382", receiverPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Account senderAccount = new Account("SenderAccount", senderCustomer);
        Account receiverAccount = new Account("ReceiverAccount", receiverCustomer);

        senderAccount.depositMoney(amountInSenderAccount);

        Assert.assertFalse(senderAccount.directTransfer(receiverAccount, amountToSend));
    }

    @Test
    public void testReceiverWouldOverflow() {
        long amountInSenderAccount = 10000;
        long amountToSend = 1;
        long amountInReceiverAccount = Long.MAX_VALUE;
        Customer senderCustomer = null;
        Customer receiverCustomer = null;
        try {
            senderCustomer = new Customer("Sender", "Sendersson", "20170111-2391", senderPassword);
            receiverCustomer = new Customer("Receiver", "Receiversson", "20170112-2382", receiverPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Account senderAccount = new Account("SenderAccount", senderCustomer);
        Account receiverAccount = new Account("ReceiverAccount", receiverCustomer);

        senderAccount.depositMoney(amountInSenderAccount);
        receiverAccount.depositMoney(amountInReceiverAccount);

        Assert.assertFalse(senderAccount.directTransfer(receiverAccount, amountToSend));
    }

    //@Test
    public void testBaseCase() {
        long amountInSenderAccount = 10000;
        long amountToSend = 10000;
        Customer senderCustomer = null;
        Customer receiverCustomer = null;
        try {
            senderCustomer = new Customer("Sender", "Sendersson", "20170111-2391", senderPassword);
            receiverCustomer = new Customer("Receiver", "Receiversson", "20170112-2382", receiverPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Account senderAccount = new Account("SenderAccount", senderCustomer);
        Account receiverAccount = new Account("ReceiverAccount", receiverCustomer);

        senderAccount.depositMoney(amountInSenderAccount);

        Assert.assertFalse(senderAccount.directTransfer(receiverAccount, amountToSend));
    }
}
