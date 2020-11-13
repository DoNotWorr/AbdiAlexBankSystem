package org.example;

import org.example.Exceptions.NonNumericalException;
import org.example.Exceptions.NumberNotInBoundsException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UnitConversion {
    /**
     * Metod för att konvertera ett belopp (öre) till ett belopp i kronor
     *
     * @param amountCent belopp i hela ören, exempelvis: 1234
     * @return belopp i kronor som decimaltal, exempelvis 12,34
     * @author Alex, Abdi
     * Konverterar ett belopp från ören (1234 öre) till kronor (12,34 kr)
     */
    public static String convertToSek(long amountCent) {
        StringBuilder amountSek = new StringBuilder();
        int indexOfNewZero;
        int minLength;
        amountSek.append(amountCent);

        //Fixade en bug för små negativa tal (till exempel -10 eller -1) /Alex
        //Kollar om beloppet är positivt eller negativt och justerar därefter (eftersom "-x" innehåller ett tecken mer än "x")
        if (amountCent >= 0) {
            indexOfNewZero = 0;
            minLength = 3;
        } else {
            indexOfNewZero = 1;
            minLength = 4;
        }

        //Fixade bugg när amountSek var mindre än 3 siffror /Abdi
        //Justerar små tal så man kan stoppa in en punkt två steg till vänster om sista tecknet, till exempel "1" -> "001"
        while (amountSek.toString().length() < minLength) {
            amountSek.insert(indexOfNewZero, '0');
        }
        //Stoppar in en punkt två tecken till vänster om det sista tecknet, till exempel "001" -> "0.01"
        amountSek.insert(amountSek.length() - 2, '.');
        return amountSek.toString();
    }

    /**
     * Metod för att konvertera belopp väldigt stora belopp (öre) till kronor. Validerar inte inmatning
     *
     * @param amountCent String-representation av ett heltal, exempelvis: "123456789123456789123456789123456789"
     * @return belopp i kronor som decimaltal, exempelvis 12,34
     * @author Alex, Abdi
     * Konverterar ett belopp från ören (1234 öre) till kronor (12,34 kr)
     */
    public static String convertToSek(String amountCent) {
        StringBuilder amountSek = new StringBuilder();
        amountSek.append(amountCent);
        //2020 öre
        while (amountSek.toString().length() < 3) {  // Fixade bugg när amountSek var mindre än 3 siffror //Abdi
            amountSek.insert(0, '0');
        }
        amountSek.insert(amountSek.length() - 2, '.');
        return amountSek.toString();
    }

    /**
     * Räknar om ett belopp i kronor till motsvarande belopp i ören.
     *
     * @param amountSek String med ett belopp i kronor med max två decimaler (exempelvis "123" eller "123.45"). Max två decimaler och inom intervallet -92233720368547758.08 >= amountSek <= 92233720368547758.07
     * @return beloppet konverterat till ören (exempelvis 12300 eller 12345)
     * @throws NonNumericalException      om argument inte går att göra om till ett tal
     * @throws NumberNotInBoundsException om argument är ett tal som inte kan hanteras. Max två decimaler och inom intervallet -92233720368547758.08 >= amountSek <= 92233720368547758.07
     * @author Alex
     */
    public static long convertFromSek(String amountSek) throws NonNumericalException, NumberNotInBoundsException {
        try {
            //Om amountSek innehöll mer än 2 decimalers precision så blir det ArithmeticException.
            return new BigDecimal(amountSek).movePointRight(2).setScale(0, RoundingMode.UNNECESSARY).longValueExact();
        } catch (NumberFormatException e) {
            throw new NonNumericalException("Felaktig inmatning. Inmatning måste vara ett tal");
        } catch (ArithmeticException e) {
            throw new NumberNotInBoundsException("Felaktig inmatning. Max två decimaler och inom intervallet -92233720368547758.08 >= amountSek <= 92233720368547758.07");
        }
    }
}
