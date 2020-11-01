package org.example;

import java.math.BigDecimal;
import java.math.BigInteger;

public class UnitConversion {

    /**
     * @param amount belopp i hela ören, exempelvis: 1234
     * @return belopp i kronor som decimaltal, exempelvis 12,34
     * @author Alex
     * Konverterar ett belopp från ören (1234 öre) till kronor (12,34 kr)
     */
    public static BigDecimal convertToSek(BigInteger amount) {
        //2020 öre
        return new BigDecimal(amount.divide(BigInteger.valueOf(100)) + "." +  amount.mod(BigInteger.valueOf(100)));         //+ ((double) (amount % 100) / 100.0); //balance i kronor och ören
    }

    /**
     * En metod som konverterar ett belopp i ören (int) till motsvarande belopp i kronor (double).
     * Använd ValidationService.INSTANCE.isValidArgumentConvertFromSek(amountInSek) används för att kontrollera att argument är giltigt.
     *
     * @param amountInSek ett belopp i kronor med max två decimaler (exempelvis 123 eller 123.45)
     * @return beloppet konverterat till ören (exempelvis 12300 eller 12345)
     * @throws TooManyDecimalsException om amountInSek innehåller mer än två decimaler (exempelvis 123.456), för att undvika avrundningsfel
     * @author Alex
     */

    public static BigInteger convertFromSek(double amountInSek) throws TooManyDecimalsException, TooBigNumberException {
        //All kontroll sker i ValidationService.INSTANCE.isValidArgumentConvertFromSek()
        if (ValidationService.INSTANCE.isValidArgumentConvertFromSek(amountInSek)) {
            return BigDecimal.valueOf(amountInSek * 100.0).toBigIntegerExact();
            //return (BigInteger) (amountInSek * 100.0); //gamla
        }
        return null;
    }


}
