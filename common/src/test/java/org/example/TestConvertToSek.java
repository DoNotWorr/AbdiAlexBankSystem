package org.example;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tester för metoden UnitConversion.convertToSek()
 */
public class TestConvertToSek {

    @Test
    public void testSmallNumber() {
        String expected = "1.00";
        String actual = UnitConversion.convertToSek(100);
        assertEquals(expected, actual);
    }

    /**
     * testar att konvertera väldigt litet negativt tal.
     */
    @Test
    public void testConvertVerySmallNegativeNumber() {
        String expected = "-0.10";
        long amountCent = -10;
        String actual = UnitConversion.convertToSek(amountCent);
        assertEquals(expected, actual);
    }

    /**
     * testar att konvertera väldigt väldigt litet negativt tal.
     */
    @Test
    public void testConvertVeryVerySmallNegativeNumber() {
        String expected = "-0.01";
        long amountCent = -1;
        String actual = UnitConversion.convertToSek(amountCent);
        assertEquals(expected, actual);
    }

    /**
     * testar att konvertera väldigt litet negativt tal.
     */
    @Test
    public void testConvertZero() {
        String expected = "0.00";
        long amountCent = 0;
        String actual = UnitConversion.convertToSek(amountCent);
        assertEquals(expected, actual);
    }


    /**
     * testar att konvertera väldigt litet positivt tal.
     */
    @Test
    public void testConvertVerySmallPositiveNumber() {
        String expected = "0.10";
        long amountCent = 10;
        String actual = UnitConversion.convertToSek(amountCent);
        assertEquals(expected, actual);
    }

    @Test
    public void testNegativeSmallNumber() {
        String expected = "-1.00";
        String actual = UnitConversion.convertToSek(-100);
        assertEquals(expected, actual);
    }

    @Test
    public void testMaxNumber() {
        String expected = "92233720368547758.07";
        String actual = UnitConversion.convertToSek(Long.MAX_VALUE);
        assertEquals(expected, actual);
    }

    @Test
    public void testNegativeMaxNumber() {
        String expected = "-92233720368547758.08";
        String actual = UnitConversion.convertToSek(Long.MIN_VALUE);
        assertEquals(expected, actual);
    }

    /**
     * testar att konvertera väldigt stora tal.
     */
    @Test
    public void testConvertBigStringNumber() {
        String input = "123456789123456789123456789123456789123456789123456789123456789123456789";
        String expected = "1234567891234567891234567891234567891234567891234567891234567891234567.89";
        assertEquals(expected, UnitConversion.convertToSek(input));
    }

    /**
     * testar att konvertera väldigt stora tal.
     */
    @Test
    public void testConvertSmallNumber() {
        String input = "10";
        String expected = "0.10";
        assertEquals(expected, UnitConversion.convertToSek(input));
    }

    /**
     * testar att konvertera väldigt stora tal.
     */
    @Test
    public void testConvertSmallNegativeNumber() {
        String input = "-10";
        String expected = "-0.10";
        assertEquals(expected, UnitConversion.convertToSek(input));
    }
}
