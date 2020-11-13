package org.example;

import java.time.LocalDate;
import java.util.Objects;

public enum ValidationService {
    INSTANCE;

    /**
     * Kontrollerar om ett lösenord är tillräckligt starkt. Har satt den väldigt
     * @param plainPassword lösenord i plaintext
     * @return true om plainPassword är längre än 3 tecken. False om plainPassword är för kort eller plainPassword är null
     */
    public boolean isValidPassword(String plainPassword) {
        boolean isValid = false;
        if(Objects.nonNull(plainPassword)) {
            if(plainPassword.length() > 3) {
                isValid = true;
            } else {
                isValid = false;
            }
        }

        return isValid;
    }

    /**
     * Kontrollerar godtyckligt om en textsträng är ett namn
     *
     * @param name för- eller efternamn som ska valideras
     * @return true om namnet är minst ett tecken långt och börjar med en stor bokstav som (eventuellt) följs av små bokstäver. Vissa korrekta namn returnerar false (till exempel "Therése", eftersom 'é' inte ingår i det vanliga alfabetet).
     * @author Alex
     */
    public boolean isValidName(String name) {
        boolean isCorrectName = false;
        if (name != null) {
            if (name.length() > 0) {
                //Om första tecknet är en stor bokstav (A-Ö) kontrollerar for-satsen ifall varje nästkommande tecken är en liten bokstav (a-ö).
                if (isUpperCaseLetter(name.charAt(0))) {
                    for (int i = 1; i < name.length(); i++) {
                        if (!isLowerCaseLetter(name.charAt(i))) {
                            return false;       //Om ett nästkommande tecken inte är en liten bokstav (a-ö) returnerar metoden false.
                        }
                    }
                    isCorrectName = true;       //Om namnet klarade alla kontroller
                }
            }
        }
        return isCorrectName;           //Returnerar "isCorrectName" istället för "true" i det fall den första if-satsen var false.
    }

    /**
     * Kontrollerar om en ownerID är ett giltigt personnummer. Den enda ålderskontrollen som görs är att personen måste vara född.
     * Till exempel är "00000101-0008" ett giltigt personnummer (1: januari år 0). Mest relevant är att personen kan vara under 18 eller till och med nyfödd, så länge personnumret i sig är giltigt.
     *
     * @param ownerID ett personnummer i formatet "yyyyMMdd-xxxx"
     * @return true om ownerID är ett giltigt personnummer
     * @author Alex
     */
    public boolean isValidOwnerID(String ownerID) {
        boolean isCorrectOwnerID = false; // Kommer returneras i slutet. För att den ska ändras till "true" måste String ownerID klara alla kontroller.
        //0. För att programmet inte ska krasha ifall ownerID inte har något värde.
        if (ownerID != null) {
            //1. Kontrollerar att ownerID är 13 tecken lång
            if (ownerID.length() == 13) {
                //2. Kontrollerar att tecken på index 8 är '-'
                if (ownerID.charAt(8) == '-') {
                    //3a. Konverterar till standard datumformat "yyyy-MM-dd" för nästa kontroll (3b)
                    StringBuilder stringBuilderOwnerID = new StringBuilder();
                    stringBuilderOwnerID.append(ownerID, 0, 4);
                    stringBuilderOwnerID.append('-');
                    stringBuilderOwnerID.append(ownerID, 4, 6);
                    stringBuilderOwnerID.append('-');
                    stringBuilderOwnerID.append(ownerID, 6, 8);
                    try {
                        //3b. Kontrollerar att "yyyy-MM-dd" är ett giltigt datum genom att försöka parsa till LocalDate
                        //3c. Kontrollerar samtidigt att datumet har inträffat
                        //3d. Kontrollerar samtidigt att "yyyy", "MM" och "dd" enbart består av siffror
                        if (LocalDate.parse(stringBuilderOwnerID.toString()).isAfter(LocalDate.now())) {
                            return false;
                        }
                    } catch (Exception e) {
                        return false;
                    }
                    //4a. Förbereder stringbuilder för Luhns algoritm. Ändrar StringBuilder från "yyyy-MM-dd" till "yyMMdd"
                    stringBuilderOwnerID.delete(0, 2); //från "yyyy-MM-dd" till "yy-MM-dd"
                    stringBuilderOwnerID.delete(2, 3); //från yy-MM-DD" till "yyMM-DD"
                    stringBuilderOwnerID.delete(4, 5); //från "yyMM-DD" till "yyMMdd"

                    //4b. Förbereder stringbuilder för Luhns algoritm. Kontrollerar att "XXXX" är ett heltal
                    try {
                        Integer.parseInt(ownerID.substring(9));
                    } catch (Exception e) {
                        return false;
                    }
                    //4c. Lägger till "XXXX" i StringBuilderOwnerID, vars innehåll nu är "yyMMddXXXX"
                    stringBuilderOwnerID.append(ownerID.substring(9));

                    //5. Luhns algoritm
                    int LuhnAlgorithmSum = 0;

                    char[] arrayOwnerID = stringBuilderOwnerID.toString().toCharArray(); //StringBuilder konverteras till char[]
                    for (int i = 0; i < arrayOwnerID.length; i++) {
                        int value = Character.getNumericValue(arrayOwnerID[i]);

                        if (i % 2 == 0) { //multiplicera med 2
                            value = value * 2;
                        }
                        LuhnAlgorithmSum = LuhnAlgorithmSum + value / 10 + value % 10;
                    }
                    if (LuhnAlgorithmSum % 10 == 0) {
                        isCorrectOwnerID = true;
                    }
                }
            }
        }

        return isCorrectOwnerID;
    }

    /**
     * Kontrollerar om ett tecken är en stor bokstav
     *
     * @param ch tecknet som kontrolleras
     * @return true om tecknet är en stor bokstav (A-Ö). Variationer av bokstäver (till exempel È) returnerar false.
     */
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

    /**
     * Kontrollerar om ett tecken är en liten bokstav (a-ö).
     *
     * @param ch tecknet som kontrolleras
     * @return true om tecknet är en stor bokstav (A-Ö). Variationer av bokstäver (till exempel é) returnerar false.
     */
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
