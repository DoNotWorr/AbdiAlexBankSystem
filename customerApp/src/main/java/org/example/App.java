package org.example;

import org.example.Account;
import org.example.Customer;
import com.google.inject.Singleton;
import java.util.Scanner;


/**
 * @author Abdi
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("--------Välkommen till Newton bank--------");

        boolean avslut = true;
        while(avslut){

            System.out.println("" +
                    "[1] Skapa konto "
                    + "\n[2] Lista konto "
                    + "\n[3] Sätta in pengar på konto "
                    + "\n[4] Ta ut pengar från konto "
                    + "\n[5] Lägg upp betalningsuppdrag i en textfil "
                    + "\n[6] Ta bort betalningsuppdrag "
                    + "\n[7] Visa kassavalv (totalt insatt belopp på banken )"
                    + "\n[8] Gör överföring mellan två konton. "
                    + "\n[0] Avslut ");

            int svar = Integer.parseInt(SingletonInput.getInstance().scanner.nextLine());
            //Skapade ett Singleton klass för att försöka fatta mig på hur det funkar och jag lyckades. Plon Jon har kollat genom koden och det är rätt.

            switch (svar){

                case 1:
                    System.out.println("hej"); // Skrev det här bara för att se om inmatningen funkade
                    //Här skapar man konto
                    break;

                case 2:
                    System.out.println("heeej"); // Skrev det här bara för att se om inmatningen funkade
                    // Här ska vi kunna lista dom olika konton som finns
                    // Det ska vara sorterad och klart
                    break;

                case 3:
                    // Här ska vi kunna referara till metoden som vi har skrivit i Account
                    break;

                case 4:
                    // Här ska vi kunna referara till metoden som vi har skrivit i Account
                    break;

                case 5:
                    // Här ska vi kunna referara till metoden som vi har skrivit
                    break;

                case 6:
                    // Här ska vi kunna referara till metoden som vi har.
                    break;

                case 7:
                    // Här ska metoden som räknar allt som finns i allas konton vara.
                    break;

                case 8:
                    // Här ska vi kunna överföra mellan konton och kunna överföra mellan dom olika anvndaren.
                    // Alltså Abdi ska kunna skicka pengar till Alex.
                    break;

                case 0:
                    avslut = false;
                    break;

                default:
                    System.out.println("Felaktig inmatning! "
                            + "\n Vänligen välj mellan det som finns i menyn! ");
            }
        }
    }
}
