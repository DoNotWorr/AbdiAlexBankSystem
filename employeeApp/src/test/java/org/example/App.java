package org.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Unit test for simple App.
 */
public class App {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws Exception {
        //Testar UnitConversion: Går bra att ta bort
        //int tal = 2147483647;
        //System.out.println("Öre: " + tal + " Kr: " + UnitConversion.convertToSek(tal));
        //int test = UnitConversion.convertFromSek(21474836.47);
        //System.out.println(UnitConversion.convertFromSek(1000000000000000000.0));
        System.out.println("Öre: " + new BigInteger("123456789456123456789123456"));
        System.out.println("Kr:  " + UnitConversion.convertToSek(new BigInteger("123456789456123456789123456")));
    }

    @Test
    public void newTest() throws Exception {
        //Testar UnitConversion: Går bra att ta bort
        //int tal = 2147483647;
        //System.out.println("Öre: " + tal + " Kr: " + UnitConversion.convertToSek(tal));
        //int test = UnitConversion.convertFromSek(21474836.47);
        //System.out.println(UnitConversion.convertFromSek(1000000000000000000.0));
        System.out.println("Öre: " + new BigInteger("123456789456123456789123456"));
        System.out.println("Kr:  " + UnitConversion.convertToSek(new BigInteger("123456789456123456789123456")));
    }
}
