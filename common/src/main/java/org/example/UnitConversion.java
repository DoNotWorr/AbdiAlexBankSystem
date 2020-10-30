package org.example;

public class UnitConversion {

    /**
     * @param amount belopp i hela ören, exempelvis: 1234
     * @return belopp i kronor som decimaltal, exempelvis 12,34
     * @author Alex
     * Konverterar ett belopp från ören (1234 öre) till kronor (12,34 kr)
     */
    public static double convertToSek(int amount) {
        //2020 ören -> 20 kr + 0,20 kr
        return (amount / 100) + ((double) (amount % 100) / 100.0); //balance i kronor och ören
    }

    /**
     * @param amountInSek ett belopp i kronor med max två decimaler (exempelvis 123 eller 123.45)
     * @return beloppet konverterat till ören (exempelvis 12300 eller 12345)
     * @throws TooManyDecimalsException om amountInSek innehåller mer än två decimaler (exempelvis 123.456), för att undvika avrundningsfel
     * @author Alex
     */
    public static int convertFromSek(double amountInSek) throws TooManyDecimalsException, TooBigNumberException {
        //Konverterar till String för att räkna ut hur många decimaler efter punkten

        if (amountInSek > 21474836.47) {
            throw new TooBigNumberException("Kan inte hantera värden större än 21474836.47 kr");
        } else {
            String amountAsText = Double.toString(amountInSek);
            //Jämför längden på strängen och vilket index i strängen som decimaltecknet har. Om skillnaden är 3 så har talet 2 decimaler.
            //"10.01"     .length = 5; .indexOf('.') = 2
            //"10.001"    .length = 6; .indexOf('.') = 2
            if (amountAsText.length() - amountAsText.indexOf('.') <= 3) {
                return (int) (amountInSek * 100.0);
            } else if (amountAsText.contains("E")) {
                String[] amountAsScientificNotation = amountAsText.split("E");
                if(amountAsScientificNotation[0].length() - 2 - Integer.parseInt(amountAsScientificNotation[1]) <= 2) {
                    return (int) (amountInSek * 100.0);
                } else {
                    throw new TooManyDecimalsException("Max 2 decimaler");
                }
            } else {
                throw new TooManyDecimalsException("Max 2 decimaler");
            }
        }
    }

}
