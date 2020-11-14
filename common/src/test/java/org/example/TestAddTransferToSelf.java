package org.example;

import org.example.DataClasses.Account;
import org.example.DataClasses.Customer;
import org.example.Exceptions.NotLaterDateException;
import org.example.Exceptions.NullToAccountException;
import org.example.Exceptions.SameAccountException;
import org.example.Exceptions.TooSmallNumberException;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class TestAddTransferToSelf {
    public String senderPassword = "1234";
    public String receiverPassword = "1234";

    @Test
    public void testTransferToSelfCase() {
        String errorMsg = "Shouldn't be this"; //Samma från början
        String actualErrorMsg = "Shouldn't be this"; //Samma från början
        long amountInSenderAccount = 10000;
        long amountToSend = 10000;
        LocalDate sendDate = LocalDate.now().plusDays(1);
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
        try {
            senderAccount.addTransfer(senderAccount, amountToSend, sendDate);
        } catch (NullToAccountException e) {
            e.printStackTrace();
        } catch (NotLaterDateException e) {
            e.printStackTrace();
        } catch (TooSmallNumberException e) {
            e.printStackTrace();
        } catch (SameAccountException e) {
            actualErrorMsg = e.getMessage(); //Om man matar in avsändarkonto som mottagarkonto så ska actualErrorMsg ändras
        }

        Assert.assertFalse(errorMsg.equals(actualErrorMsg)); //Om felet hanteras korrekt så ska errorMsg inte ha samma värde som actualErrorMsg längre
    }
}
