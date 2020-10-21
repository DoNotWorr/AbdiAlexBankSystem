package org.example;

import java.time.LocalDate;

public enum ValidationService {
    INSTANCE;

    /**
     * Kontrollerar om en String innehåller enbart bokstäver.
     *
     * @param name
     * @return
     * @author Alex
     */
    public boolean isValidName(String name) {
        boolean isCorrectName = false;
        //Om första tecknet är en stor bokstav (A-Ö) kontrollerar for-satsen ifall varje nästkommande tecken är en liten bokstav (a-ö).
        if (isUpperCaseLetter(name.charAt(0))) {
            for (int i = 1; i < name.length(); i++) {
                if (isLowerCaseLetter(name.charAt(i)) == false) {
                    return false;       //Om ett nästkommande tecken inte är en liten bokstav (a-ö) returnerar metoden false.
                }
            }
            isCorrectName = true;       //Om namnet klarade alla kontroller
        }
        return isCorrectName;           //Returnerar "isCorrectName" istället för "true" i det fall den första if-satsen var false.
    }

    /**
     * Kontrollerar om en String är ett korrekt personnummer i formatet yyyyMMdd-xxxx
     *
     * @param ownerID
     * @return
     * @author Alex
     */
    public boolean isValidOwnerID(String ownerID) {
        boolean isCorrectOwnerID = false; // Kommer returneras i slutet. För att den ska ändras till "true" måste String ownerID klara alla kontroller.
        //1. Kontrollerar att ownerID är 13 tecken lång
        if(ownerID.length() == 13) {
            //2. Kontrollerar att tecken på index 8 är '-'
            if(ownerID.charAt(8) == '-') {
                //3. Kontrollerar att yyyyMMdd är ett giltigt datum genom att försöka parsa till LocalDate
                StringBuilder stringBuilderOwnerID = new StringBuilder();
                stringBuilderOwnerID.append(ownerID.substring(0,4));
                stringBuilderOwnerID.append('-');
                stringBuilderOwnerID.append(ownerID.substring(4,6));
                stringBuilderOwnerID.append('-');
                stringBuilderOwnerID.append(ownerID.substring(6,8));
                try {
                    LocalDate.parse(stringBuilderOwnerID.toString());
                } catch (Exception e) {
                    return false;
                }
                //4. Ändrar StringBuilder från "yyyy-MM-dd" till "yyyyMMdd" för senare sifferkontroll
                stringBuilderOwnerID.delete(4,5);
                stringBuilderOwnerID.delete(6,7);

                //5. Kontrollera att XXXX är ett heltal. Denna kontroll behöver inte göras på
                try {
                    Integer.parseInt(ownerID.substring(9));
                } catch (Exception e) {
                    return false;
                }
                //6. Lägger till XXXX i StringBuilderOwnerID, vars innehåll nu är yyyyMMddXXXX
                stringBuilderOwnerID.append(ownerID.substring(9));

                //7. Kontrollerar kontrollsiffran todo fortsätt
                int sum = 0;
                int LuhnAlgorithmSum;
                char[] ownerIDarray = stringBuilderOwnerID.toString().toCharArray();
                for (int i = 0; i < ownerIDarray.length -1; i++) {
                    int value = Character.getNumericValue(ownerIDarray[i]);

                    if (i % 2 == 0) { //multiplicera med 2
                        value = value * 2;
                    }
                    sum = sum + value / 10 + value % 10;
                }
                LuhnAlgorithmSum = (10 - sum % 10) % 10;
            }
        }

        /*



        4. Kontrollera att yyyyMMddXXX stämmer överens med sista X
         */

        return isCorrectOwnerID;
    }

    //små bokstäver a-z = 97-122, å = 229, ä = 228, ö = 246
    private static boolean isLowerCaseLetter(char ch) {
        boolean isLowerCaseLetter = false;
        if ((int) ch >= 97 && (int) ch <= 122) { //UTF-8: a till z motsvarar decimaltal 97 till 122
            isLowerCaseLetter = true;
        }
        if ((int) ch == 229 || (int) ch == 228 || (int) ch == 246) { //UTF-8: å, ä, ö motsvarar 229, 228, 246
            isLowerCaseLetter = true;
        }
        return isLowerCaseLetter;
    }

    //Stora bokstäver A-Z = 65 - 90, Å = 197, Ä = 196, Ö = 214
    private static boolean isUpperCaseLetter(char ch) {
        boolean isUpperCaseLetter = false;
        if ((int) ch >= 65 && (int) ch <= 90) { //UTF-8: A till Z motsvarar decimaltal 65 till 90
            isUpperCaseLetter = true;
        }
        if ((int) ch == 197 || (int) ch == 196 || (int) ch == 214) { //UTF-8: Å, Ä, Ö motsvarar 197, 196, 214
            isUpperCaseLetter = true;
        }
        return isUpperCaseLetter;
    }
}
