package org.example;

import java.math.BigDecimal;
import java.math.BigInteger;

public class UnitConversion {
    //Belopp i sek som ska konverteras till cent (ören) måste vara mindre än absoluteBoundSek och större än -absoluteBoundSek
    private static final double absoluteBoundSekToCent = 70368744177663.99;

    /**
     * @param amountCent belopp i hela ören, exempelvis: 1234
     * @return belopp i kronor som decimaltal, exempelvis 12,34
     * @author Alex
     * Konverterar ett belopp från ören (1234 öre) till kronor (12,34 kr)
     */
    public static String convertToSek(long amountCent) {
        StringBuilder amountSek = new StringBuilder();
        amountSek.append(amountCent);
        //2020 öre
        amountSek.insert(amountSek.length() - 2, '.');
        return amountSek.toString();
    }

    /**
     * Räknar om ett belopp i kronor till motsvarande belopp i ören.
     * @param amountSek ett belopp i kronor med max två decimaler (exempelvis 123 eller 123.45)
     * @return beloppet konverterat till ören (exempelvis 12300 eller 12345)
     * @throws TooManyDecimalsException om amountInSek innehåller mer än två decimaler (exempelvis 123.456), för att undvika avrundningsfel
     * @author Alex
     */

    public static long convertFromSek(double amountSek) throws TooManyDecimalsException, TooBigNumberException {
        //All kontroll sker i ValidationService.INSTANCE.isValidArgumentConvertFromSek()
        if (amountSek < absoluteBoundSekToCent && amountSek > -absoluteBoundSekToCent) {
            try {
                //Om amountSek innehöll mer än 2 decimalers precision så blir det ArithmeticException.
                return BigDecimal.valueOf(amountSek).movePointRight(2).setScale(0).longValueExact();
            } catch (ArithmeticException e) {
                //Skapar eget exception för att påtvinga hantering av fel där metoden används
                throw new TooManyDecimalsException("Input får ha max 2 decimalers precision");
            }
        } else {
            throw new TooBigNumberException("Argument måste vara mindre än 70368744177663.99");
        }
    }


}
