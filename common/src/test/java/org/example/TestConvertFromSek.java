package org.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Unit test for simple App.
 */
public class TestConvertFromSek {
    /**
     * Test inmatning utan decimal
     */
    @Test
    public void testNoDecimal() {
        String inputSek = "100";
        try {
            assertEquals(10000, UnitConversion.convertFromSek(inputSek));
        } catch (Exception e) {
            System.out.println("Oväntat fel testNoDecimal()");
        }
    }

    /**
     * Test inmatning, en decimal
     */
    @Test
    public void testOneDecimal() {
        String inputSek = "100.9";
        try {
            assertEquals(10090, UnitConversion.convertFromSek(inputSek));
        } catch (Exception e) {
            System.out.println("Oväntat fel testOneDecimal()");
        }
    }

    /**
     * Test inmatning, två decimaler
     */
    @Test
    public void testTwoDecimal() {
        String inputSek = "100.99";
        try {
            assertEquals(10099, UnitConversion.convertFromSek(inputSek));
        } catch (Exception e) {
            System.out.println("Oväntat fel testTwoDecimal()");
        }
    }

    /**
     * Test inmatning, tre decimaler
     */
    @Test
    public void testThreeDecimal() {
        String inputSek = "100.999";
        String message = "Oväntat fel i testThreeDecimal";
        try {
            UnitConversion.convertFromSek(inputSek);
        } catch (Exception e) {
            message = e.getMessage();
        }
        assertEquals("Felaktig inmatning. Max två decimaler och inom intervallet -92233720368547758.08 >= amountSek <= 92233720368547758.07", message);
    }

    /**
     * Test inmatning, max heltal utan decimaler
     */
    @Test
    public void testDoubleMaxNoDecimal() {
        String inputSek = "92233720368547758";
        long expected = 9223372036854775800L;
        try {
            assertEquals(expected, UnitConversion.convertFromSek(inputSek));
        } catch (Exception e) {
            System.out.println("Oväntat fel i testDoubleMaxNoDecimal()");
        }
    }

    /**
     * Test inmatning, max heltal med en decimal
     */
    @Test
    public void testDoubleMaxWithOneDecimal() {
        String inputSek = "92233720368547757.9";
        long expected = 9223372036854775790L;
        try {
            assertEquals(expected, UnitConversion.convertFromSek(inputSek));
        } catch (Exception e) {
            System.out.println("Oväntat fel i testDoubleMax()");
        }
    }

    /**
     * Test inmatning, max heltal med två decimaler
     */
    @Test
    public void testDoubleMaxWithTwoDecimal() {
        String inputSek = "92233720368547758.070";
        long expected = 9223372036854775807L;
        try {
            assertEquals(expected, UnitConversion.convertFromSek(inputSek));
        } catch (Exception e) {
            System.out.println("Oväntat fel i testDoubleMax()");
        }
    }

    /**
     * Test inmatning, max heltal med två decimaler och en extra nolla efter
     */
    @Test
    public void testOverflowMaxWithTwoDecimalAndExtraZero() {
        String inputSek = "92233720368547758.07";
        long expected = 9223372036854775807L;
        try {
            assertEquals(expected, UnitConversion.convertFromSek(inputSek));
        } catch (Exception e) {
            System.out.println("Oväntat fel i testOverflowMaxWithThreeDecimal() 1");
        }
    }

    /**
     * Test inmatning, max heltal med tre decimaler
     */
    @Test
    public void testOverflowMaxWithThreeDecimals() {
        String inputSek = "92233720368547758.081";
        String message = "Oväntat fel i testOverflowMaxWithThreeDecimals()";
        //"Input får ha max 2 decimalers precision"
        try {
            UnitConversion.convertFromSek(inputSek);
        } catch (Exception e) {
            message = e.getMessage();
        }
        assertEquals("Felaktig inmatning. Max två decimaler och inom intervallet -92233720368547758.08 >= amountSek <= 92233720368547758.07", message);
    }

    /**
     * Test inmatning, för stort tal
     */
    @Test
    public void testOverflow() {
        String inputSek = "92233720368547758.09";
        String message = "Oväntat fel i testDoubleOverflow()";
        try {
            UnitConversion.convertFromSek(inputSek);
        } catch (Exception e) {
            message = e.getMessage();
        }
        assertEquals("Felaktig inmatning. Max två decimaler och inom intervallet -92233720368547758.08 >= amountSek <= 92233720368547758.07", message);
    }

    //negativa

    /**
     * Test inmatning negativt utan decimal
     */
    @Test
    public void testNegativeNoDecimal() {
        String inputSek = "-100.00";
        long expected = -10000;
        try {
            assertEquals(UnitConversion.convertFromSek(inputSek), expected);
        } catch (Exception e) {
            System.out.println("Oväntat fel testNoDecimal()");
        }
    }

    /**
     * Test inmatning, negativt, en decimal
     */
    @Test
    public void testNegativeOneDecimal() {
        String inputSek = "-100.90";
        long expected = -10090;
        try {
            assertEquals(UnitConversion.convertFromSek(inputSek), expected);
        } catch (Exception e) {
            System.out.println("Oväntat fel testOneDecimal()");
        }
    }

    /**
     * Test inmatning, negativt, två decimaler
     */
    @Test
    public void testNegativeTwoDecimal() {
        String inputSek = "-100.99";
        long expected = -10099;
        try {
            assertEquals(UnitConversion.convertFromSek(inputSek), expected);
        } catch (Exception e) {
            System.out.println("Oväntat fel testTwoDecimal()");
        }
    }

    /**
     * Test inmatning, negativt, tre decimaler
     */
    @Test
    public void testNegativeThreeDecimal() {
        String inputSek = "-100.999";
        String message = "Oväntat fel i testThreeDecimal";
        try {
            UnitConversion.convertFromSek(inputSek);
        } catch (Exception e) {
            message = e.getMessage();
        }
        assertEquals("Felaktig inmatning. Max två decimaler och inom intervallet -92233720368547758.08 >= amountSek <= 92233720368547758.07", message);
    }

    /**
     * Test inmatning, negativt max heltal utan decimaler
     */
    @Test
    public void testNegativeDoubleMaxNoDecimal() {
        String inputSek = "-92233720368547758.00";
        long expected = -9223372036854775800L;
        try {
            assertEquals(expected, UnitConversion.convertFromSek(inputSek));
        } catch (Exception e) {
            System.out.println("Oväntat fel i testDoubleMaxNoDecimal()");
        }
    }

    /**
     * Test inmatning, negativt max heltal med en decimal
     */
    @Test
    public void testNegativeDoubleMaxWithOneDecimal() {
        String inputSek = "-92233720368547757.9";
        long expected = -9223372036854775790L;
        try {
            assertEquals(expected, UnitConversion.convertFromSek(inputSek));
        } catch (Exception e) {
            System.out.println("testNegativeDoubleMaxWithOneDecimal()");
        }
    }

    /**
     * Test inmatning, negativt max heltal med två decimaler
     */
    @Test
    public void testNegativeDoubleMaxWithTwoDecimal() {
        String inputSek = "-92233720368547758.08";
        long expected = -9223372036854775808L;
        try {
            assertEquals(expected, UnitConversion.convertFromSek(inputSek));
        } catch (Exception e) {
            System.out.println("Oväntat fel i testDoubleMax()");
        }
    }

    /**
     * Test inmatning, negativt max heltal med två decimaler och en extra nolla efter
     */
    @Test
    public void testNegativeOverflowMaxWithTwoDecimalAndExtraZero() {
        String inputSek = "-92233720368547758.080";
        long expected = -9223372036854775808L;
        try {
            assertEquals(expected, UnitConversion.convertFromSek(inputSek));
        } catch (Exception e) {
            System.out.println("Oväntat fel i testNegativeOverflowMaxWithTwoDecimalAndExtraZero()");
        }
    }

    /**
     * Test inmatning, negativt max heltal med tre decimaler
     */
    @Test
    public void testNegativeOverflowMaxWithThreeDecimals() {
        String inputSek = "-92233720368547757.073";
        String message = "Oväntat fel i testNegativeOverflowMaxWithThreeDecimals()";
        //"Input får ha max 2 decimalers precision"
        try {
            UnitConversion.convertFromSek(inputSek);
        } catch (Exception e) {
            message = e.getMessage();
        }
        assertEquals("Felaktig inmatning. Max två decimaler och inom intervallet -92233720368547758.08 >= amountSek <= 92233720368547758.07", message);
    }

    /**
     * Test inmatning, för stort tal, negativt
     */
    @Test
    public void testNegativeOverflow() {
        String inputSek = "-92233720368547758.09";
        String message = "Oväntat fel i testNegativeOverflow()";
        try {
            UnitConversion.convertFromSek(inputSek);
        } catch (Exception e) {
            message = e.getMessage();
        }
        assertEquals("Felaktig inmatning. Max två decimaler och inom intervallet -92233720368547758.08 >= amountSek <= 92233720368547758.07", message);
    }

    /**
     * Test med inmatning som inte är ett tal
     */
    @Test
    public void testNonNumerical() {
        String inputSek = "asdf";
        String message = "Felaktig inmatning. Inmatning måste vara ett tal";
        try {
            UnitConversion.convertFromSek(inputSek);
        } catch (Exception e) {
            message = e.getMessage();
        }
        assertEquals("Felaktig inmatning. Inmatning måste vara ett tal", message);
    }

    /**
     * Test med inmatning som är ett tal, men innehåller för många decimaltecken
     */
    @Test
    public void testAlmostNumericalButTooManyDots() {
        String inputSek = "123.33.33";
        String message = "Felaktig inmatning. Inmatning måste vara ett tal";
        try {
            UnitConversion.convertFromSek(inputSek);
        } catch (Exception e) {
            message = e.getMessage();
        }
        assertEquals("Felaktig inmatning. Inmatning måste vara ett tal", message);
    }

    /**
     * String som är null
     */
    @Test
    public void testEmptyString() {
        String inputSek = "";
        String message = "Felaktig inmatning. Inmatning måste vara ett tal";
        try {
            UnitConversion.convertFromSek(inputSek);
        } catch (Exception e) {
            message = e.getMessage();
        }
        assertEquals("Felaktig inmatning. Inmatning måste vara ett tal", message);
    }
}
