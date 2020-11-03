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
        try {
            assertEquals(10000, UnitConversion.convertFromSek(100));
        } catch (TooManyDecimalsException e) {
            System.out.println("Oväntat fel testNoDecimal()");
        } catch (TooBigNumberException e) {
            System.out.println("Oväntat fel testNoDecimal()");
        }
    }

    /**
     * Test inmatning, en decimal
     */
    @Test
    public void testOneDecimal() {
        try {
            assertEquals(10090, UnitConversion.convertFromSek(100.9));
        } catch (TooManyDecimalsException e) {
            System.out.println("Oväntat fel testOneDecimal()");
        } catch (TooBigNumberException e) {
            System.out.println("Oväntat fel testOneDecimal()");
        }
    }

    /**
     * Test inmatning, två decimaler
     */
    @Test
    public void testTwoDecimal() {
        try {
            assertEquals(10099, UnitConversion.convertFromSek(100.99));
        } catch (TooManyDecimalsException e) {
            System.out.println("Oväntat fel testTwoDecimal()");
        } catch (TooBigNumberException e) {
            System.out.println("Oväntat fel testTwoDecimal()");
        }
    }

    /**
     * Test inmatning, tre decimaler
     */
    @Test
    public void testThreeDecimal() {
        String message = "Oväntat fel i testThreeDecimal";
        try {
            UnitConversion.convertFromSek(100.999);
        } catch (TooManyDecimalsException e) {
            message = e.getMessage();
        } catch (TooBigNumberException e) {
            System.out.println("Oväntat fel testThreeDecimal()");
        }
        assertEquals("Input får ha max 2 decimalers precision", message);
    }

    /**
     * Test inmatning, max heltal utan decimaler
     */
    @Test
    public void testDoubleMaxNoDecimal() {
        long expected = 7036874417766300L;
        try {
            assertEquals(expected, UnitConversion.convertFromSek(70368744177663.0));
        } catch (TooManyDecimalsException e) {
            System.out.println("Oväntat fel i testDoubleMaxNoDecimal()");
        } catch (TooBigNumberException e) {
            System.out.println("Oväntat fel i testDoubleMaxNoDecimal()");
        }
    }

    /**
     * Test inmatning, max heltal med en decimal
     */
    @Test
    public void testDoubleMaxWithOneDecimal() {
        long expected = 7036874417766390L;
        try {
            assertEquals(expected, UnitConversion.convertFromSek(70368744177663.9));
        } catch (TooManyDecimalsException e) {
            System.out.println("Oväntat fel i testDoubleMax()");
        } catch (TooBigNumberException e) {
            System.out.println("Oväntat fel i testDoubleMax()");
        }
    }

    /**
     * Test inmatning, max heltal med två decimaler
     */
    @Test
    public void testDoubleMaxWithTwoDecimal() {
        long expected = 7036874417766398L;
        try {
            assertEquals(expected, UnitConversion.convertFromSek(70368744177663.98));
        } catch (TooManyDecimalsException e) {
            System.out.println("Oväntat fel i testDoubleMax()");
        } catch (TooBigNumberException e) {
            System.out.println("Oväntat fel i testDoubleMax()");
        }
    }

    /**
     * Test inmatning, max heltal med två decimaler och en extra nolla efter
     */
    @Test
    public void testOverflowMaxWithTwoDecimalAndExtraZero() {
        long expected = 7036874417766398L;
        try {
            assertEquals(expected, UnitConversion.convertFromSek(70368744177663.980));
        } catch (TooManyDecimalsException e) {
            System.out.println("Oväntat fel i testOverflowMaxWithThreeDecimal() 1");
        } catch (TooBigNumberException e) {
            System.out.println("Oväntat fel i testOverflowMaxWithThreeDecimal() 2");
        }
    }

    /**
     * Test inmatning, max heltal med tre decimaler
     */
    @Test
    public void testOverflowMaxWithThreeDecimals() {
        String message = "Oväntat fel i testOverflowMaxWithThreeDecimals()";
        //"Input får ha max 2 decimalers precision"
        try {
            UnitConversion.convertFromSek(70368744177663.981);
        } catch (TooManyDecimalsException e) {
            message = e.getMessage();
        } catch (TooBigNumberException e) {
            System.out.println("Oväntat fel i testOverflowMaxWithThreeDecimal() 2");
        }
        assertEquals("Input får ha max 2 decimalers precision", message);
    }

    /**
     * Test inmatning, för stort tal
     */
    @Test
    public void testOverflow() {
        String message = "Oväntat fel i testDoubleOverflow()";
        try {
            UnitConversion.convertFromSek(70368744177663.99);
        } catch (TooManyDecimalsException e) {
            System.out.println("Oväntat fel");
        } catch (TooBigNumberException e) {
            message = e.getMessage();
        }
        assertEquals("Argument måste vara mindre än 70368744177663.99", message);
    }

    //negativa

    /**
     * Test inmatning utan decimal
     */
    @Test
    public void testNegativeNoDecimal() {
        try {
            assertEquals(UnitConversion.convertFromSek(-100), -10000);
        } catch (TooManyDecimalsException e) {
            System.out.println("Oväntat fel testNoDecimal()");
        } catch (TooBigNumberException e) {
            System.out.println("Oväntat fel testNoDecimal()");
        }
    }

    /**
     * Test inmatning, en decimal
     */
    @Test
    public void testNegativeOneDecimal() {
        try {
            assertEquals(UnitConversion.convertFromSek(-100.9), -10090);
        } catch (TooManyDecimalsException e) {
            System.out.println("Oväntat fel testOneDecimal()");
        } catch (TooBigNumberException e) {
            System.out.println("Oväntat fel testOneDecimal()");
        }
    }

    /**
     * Test inmatning, två decimaler
     */
    @Test
    public void testNegativeTwoDecimal() {
        try {
            assertEquals(UnitConversion.convertFromSek(-100.99), -10099);
        } catch (TooManyDecimalsException e) {
            System.out.println("Oväntat fel testTwoDecimal()");
        } catch (TooBigNumberException e) {
            System.out.println("Oväntat fel testTwoDecimal()");
        }
    }

    /**
     * Test inmatning, tre decimaler
     */
    @Test
    public void testNegativeThreeDecimal() {
        String message = "Oväntat fel i testThreeDecimal";
        try {
            UnitConversion.convertFromSek(-100.999);
        } catch (TooManyDecimalsException e) {
            message = e.getMessage();
        } catch (TooBigNumberException e) {
            System.out.println("Oväntat fel testThreeDecimal()");
        }
        assertEquals("Input får ha max 2 decimalers precision", message);
    }

    /**
     * Test inmatning, max heltal utan decimaler
     */
    @Test
    public void testNegativeDoubleMaxNoDecimal() {
        long expected = -7036874417766300L;
        try {
            assertEquals(expected, UnitConversion.convertFromSek(-70368744177663.0));
        } catch (TooManyDecimalsException e) {
            System.out.println("Oväntat fel i testDoubleMaxNoDecimal()");
        } catch (TooBigNumberException e) {
            System.out.println("Oväntat fel i testDoubleMaxNoDecimal()");
        }
    }

    /**
     * Test inmatning, max heltal med en decimal
     */
    @Test
    public void testNegativeDoubleMaxWithOneDecimal() {
        long expected = -7036874417766390L;
        try {
            assertEquals(expected, UnitConversion.convertFromSek(-70368744177663.9));
        } catch (TooManyDecimalsException e) {
            System.out.println("Oväntat fel i testDoubleMax()");
        } catch (TooBigNumberException e) {
            System.out.println("Oväntat fel i testDoubleMax()");
        }
    }

    /**
     * Test inmatning, max heltal med två decimaler
     */
    @Test
    public void testNegativeDoubleMaxWithTwoDecimal() {
        long expected = -7036874417766398L;
        try {
            assertEquals(expected, UnitConversion.convertFromSek(-70368744177663.98));
        } catch (TooManyDecimalsException e) {
            System.out.println("Oväntat fel i testDoubleMax()");
        } catch (TooBigNumberException e) {
            System.out.println("Oväntat fel i testDoubleMax()");
        }
    }

    /**
     * Test inmatning, max heltal med två decimaler och en extra nolla efter
     */
    @Test
    public void testNegativeOverflowMaxWithTwoDecimalAndExtraZero() {
        long expected = -7036874417766398L;
        try {
            assertEquals(expected, UnitConversion.convertFromSek(-70368744177663.980));
        } catch (TooManyDecimalsException e) {
            System.out.println("Oväntat fel i testOverflowMaxWithThreeDecimal() 1");
        } catch (TooBigNumberException e) {
            System.out.println("Oväntat fel i testOverflowMaxWithThreeDecimal() 2");
        }
    }

    /**
     * Test inmatning, max heltal med tre decimaler
     */
    @Test
    public void testNegativeOverflowMaxWithThreeDecimals() {
        String message = "Oväntat fel i testOverflowMaxWithThreeDecimals()";
        //"Input får ha max 2 decimalers precision"
        try {
            UnitConversion.convertFromSek(-70368744177663.981);
        } catch (TooManyDecimalsException e) {
            message = e.getMessage();
        } catch (TooBigNumberException e) {
            System.out.println("Oväntat fel i testOverflowMaxWithThreeDecimal() 2");
        }
        assertEquals("Input får ha max 2 decimalers precision", message);
    }

    /**
     * Test inmatning, för stort tal
     */
    @Test
    public void testNegativeOverflow() {
        String message = "Oväntat fel i testDoubleOverflow()";
        try {
            UnitConversion.convertFromSek(-70368744177663.99);
        } catch (TooManyDecimalsException e) {
            System.out.println("Oväntat fel");
        } catch (TooBigNumberException e) {
            message = e.getMessage();
        }
        assertEquals("Argument måste vara mindre än 70368744177663.99", message);
    }
}
