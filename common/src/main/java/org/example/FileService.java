package org.example;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Alex
 */
public enum FileService {
    INSTANCE;

    private final String filepathCustomers = "customers.txt";
    private final String filepathAccounts = "accounts.txt";
    private final String filepathTransfers = "transfers.txt";
    private final Gson gson = new Gson();

    /**
     * Sparar alla kunder som Json i textfil. Använder save() för att skriva till fil.
     *
     * @param allCustomers HashMap med alla kunder som ska sparas
     */
    public void saveCustomers(HashMap<String, Customer> allCustomers) {
        save(filepathCustomers, gson.toJson(allCustomers));
    }

    /**
     * Sparar alla konton som Json i textfil. Använder save() för att skriva till fil.
     *
     * @param allAccounts HashMap med alla konton som ska sparas
     */
    public void saveAccounts(HashMap<String, Account> allAccounts) {
        save(filepathAccounts, gson.toJson(allAccounts));
    }

    /**
     * Sparar alla bankuppdrag som Json i textfil. Använder save() för att skriva till fil.
     *
     * @param allTransfers ArrayList med alla bankuppdrag som ska sparas
     */
    public void saveTransfers(ArrayList<Transfer> allTransfers) {
        save(filepathTransfers, gson.toJson(allTransfers));
    }

    /**
     * Läser in alla kunder från datafil. Använder load() för att läsa från fil.
     *
     * @return HashMap med customers, inlästa från datafil
     */
    public HashMap<String, Customer> loadCustomers() {
        return gson.fromJson(load(filepathCustomers), HashMap.class);
    }

    /**
     * Läser in alla konton från datafil. Använder load() för att läsa från fil.
     *
     * @return HashMap med accounts, inlästa från datafil
     */
    public HashMap<String, Account> loadAccounts() {
        return gson.fromJson(load(filepathAccounts), HashMap.class);
    }

    /**
     * Läser in alla bankuppdrag från datafil. Använder load() för att läsa från fil.
     *
     * @return ArrayList med transfers, inlästa från datafil
     */
    public ArrayList<Transfer> loadTransfers() {
        return gson.fromJson(load(filepathTransfers), ArrayList.class);
    }

    /**
     * Generell metod som returnerar den första raden i en textfil.
     *
     * @param filepath sökväg till textfil
     * @return String med första raden från textfilen
     */
    private String load(String filepath) {
        File file = new File(filepath);
        String StringToReturn = null;
        if (file.exists() == false) {                                                            //Om datafil inte existerar skapas ny fil
            System.out.println("Försöker skapa datafil " + filepath + ".");
            try {
                file.createNewFile();
                System.out.println("Skapade datafil " + filepath + ".");
            } catch (IOException error) {
                System.out.println("Kunde inte skapa datafil " + filepath + ".");
                error.printStackTrace();
            }
        } else {                                                                        //Om datafil existerar så läses filen in
            try {
                Scanner reader = new Scanner(file);
                StringToReturn = reader.nextLine();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return StringToReturn;
    }

    /**
     * Generell metod som skriver en textrad i en datafil.
     * @param filepath    sökväg till textfil
     * @param dataToWrite den textrad som skrivs till textfil
     */
    private void save(String filepath, String dataToWrite) {
        File file = new File(filepath);
        if (file.exists() == false) {                                                            //Om datafil inte existerar skapas ny fil
            System.out.println("Försöker skapa datafil " + filepath + ".");
            try {
                file.createNewFile();
                System.out.println("Skapade datafil " + filepath + ".");
            } catch (IOException error) {
                System.out.println("Kunde inte skapa datafil " + filepath + ".");
                error.printStackTrace();
                System.out.println("Det går inte att spara utan datafil.");
                return;
            }
        }                                                                        //Om datafil existerar så läses filen in
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(dataToWrite);
        } catch (IOException e) {
            System.out.println("Kunde inte spara data i " + filepath);
            System.out.println("Datan har inte sparats.");
            e.printStackTrace();
        }
    }
}
