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
}
