package org.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class App {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws TooManyDecimalsException, TooBigNumberException {
        //Testar UnitConversion: Går bra att ta bort
        int tal = 2147483647;
        System.out.println("Öre: " + tal + " Kr: " + UnitConversion.convertToSek(tal));
        double decimaltal = 21474836.47;
        System.out.println("Kr: " + decimaltal + " Öre: " + UnitConversion.convertFromSek(decimaltal));
    }
}
