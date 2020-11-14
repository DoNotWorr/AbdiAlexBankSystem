package org.example;

import org.example.Exceptions.NonNumericalException;
import org.example.Exceptions.NumberNotInBoundsException;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Author Alex, Abdi
 * Alex har skrivit klassen. Metoder som är skrivna enbart av Alex saknar author-notation. I metoder där Abdi har hjälpt till finns en author-notation samt förklaring
 */
public class UnitConversion {
    /**
     * Metod för att konvertera ett belopp (öre) till ett belopp i kronor
     *
     * @param amountCent belopp i hela ören, exempelvis: 1234
     * @return belopp i kronor som decimaltal, exempelvis 12,34
     * @author Alex, Abdi
     * Alex skrev metod. Abdi fixade bug för små tal. Alex fixade sedan bug för små negativa tal.
     */
    public static String convertToSek(long amountCent) {
        StringBuilder amountSek = new StringBuilder();
        int indexOfNewZero;
        int minLength;
        amountSek.append(amountCent);

        //Kollar om beloppet är positivt eller negativt och justerar därefter (eftersom "-x" innehåller ett tecken mer än "x")
        if (amountCent >= 0) {      //Fixade en bug för små negativa tal (till exempel -10 eller -1) /Alex
            indexOfNewZero = 0;
            minLength = 3;
        } else {
            indexOfNewZero = 1;
            minLength = 4;
        }

        //Justerar små tal så man kan stoppa in en punkt två steg till vänster om sista tecknet, till exempel "1" -> "001"
        while (amountSek.toString().length() < minLength) {     //Fixade bugg när amountSek var mindre än 3 siffror /Abdi
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
     * Alex skrev metod. Abdi fixade bug för små tal. Alex fixade sedan bug för små negativa tal.
     */
    public static String convertToSek(String amountCent) {
        StringBuilder amountSek = new StringBuilder();
        int indexOfNewZero;
        int minLength;
        amountSek.append(amountCent);

        //Kollar om beloppet är positivt eller negativt och justerar därefter (eftersom "-x" innehåller ett tecken mer än "x")
        if (amountCent.charAt(0) != '-') {      //Fixade en bug för små negativa tal (till exempel -10 eller -1) /Alex
            indexOfNewZero = 0;
            minLength = 3;
        } else {
            indexOfNewZero = 1;
            minLength = 4;
        }

        //Justerar små tal så man kan stoppa in en punkt två steg till vänster om sista tecknet, till exempel "1" -> "001"
        while (amountSek.toString().length() < minLength) {     //Fixade bugg när amountSek var mindre än 3 siffror /Abdi
            amountSek.insert(indexOfNewZero, '0');
        }
        //Stoppar in en punkt två tecken till vänster om det sista tecknet, till exempel "001" -> "0.01"
        amountSek.insert(amountSek.length() - 2, '.');
        return amountSek.toString();
    }

    /**
     * Räknar om ett belopp i kronor till motsvarande belopp i ören. Validerar inmatning.
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
