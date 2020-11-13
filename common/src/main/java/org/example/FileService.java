package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.DataClasses.Account;
import org.example.DataClasses.Customer;
import org.example.DataClasses.Transfer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Alex
 */
public enum FileService {
    INSTANCE;

    private final String filenameCustomers = "customers.txt";
    private final String filenameAccounts = "accounts.txt";
    private final String filenameTransfers = "transfers.txt";
    private final String folderName = "Banksystem";
    private final Gson gson = new Gson();

    /**
     * Sparar alla kunder som Json i textfil. Använder save() för att skriva till fil.
     *
     * @param allCustomers HashMap med alla kunder som ska sparas
     */
    public void saveCustomers(HashMap<String, Customer> allCustomers) {
        if (allCustomers.size() == 0) {
            System.out.println("Det finns inga kunder att spara.");
        } else {
            save(filenameCustomers, gson.toJson(allCustomers));
        }
    }

    /**
     * Sparar alla konton som Json i textfil. Använder save() för att skriva till fil.
     *
     * @param allAccounts HashMap med alla konton som ska sparas
     */
    public void saveAccounts(HashMap<String, Account> allAccounts) {
        if (allAccounts.size() == 0) {
            System.out.println("Det finns inga konton att spara.");
        } else {
            save(filenameAccounts, gson.toJson(allAccounts));
        }
    }

    /**
     * Sparar alla bankuppdrag som Json i textfil. Använder save() för att skriva till fil.
     *
     * @param allTransfers ArrayList med alla bankuppdrag som ska sparas
     */
    public void saveTransfers(ArrayList<Transfer> allTransfers) {
        if (allTransfers.size() == 0) {
            System.out.println("Det finns inga betalningsuppdrag att spara.");
        } else {
            save(filenameTransfers, gson.toJson(allTransfers));
        }
    }

    /**
     * Läser in alla kunder från datafil. Använder load() för att läsa från fil.
     *
     * @return HashMap med customers, inlästa från datafil
     */
    public HashMap<String, Customer> loadCustomers() {
        String jsonFromFile = load(filenameCustomers);
        if (jsonFromFile == null) {
            System.out.println("Hittade inga sparade kunder.");
            return new HashMap<>();
        }
        Type allCustomersType = new TypeToken<HashMap<String, Customer>>() {
        }.getType(); //Från "3. Using TypeToken", https://www.baeldung.com/gson-json-to-map
        return gson.fromJson(jsonFromFile, allCustomersType);
    }

    /**
     * Läser in alla konton från datafil. Använder load() för att läsa från fil.
     *
     * @return HashMap med accounts, inlästa från datafil
     */
    public HashMap<String, Account> loadAccounts() {
        String jsonFromFile = load(filenameAccounts);
        if (jsonFromFile == null) {
            System.out.println("Hittade inga sparade konton.");
            return new HashMap<>();
        }
        Type allAccountsType = new TypeToken<HashMap<String, Account>>() {
        }.getType(); //Från "3. Using TypeToken", https://www.baeldung.com/gson-json-to-map
        return gson.fromJson(jsonFromFile, allAccountsType);
    }

    /**
     * Läser in alla bankuppdrag från datafil. Använder load() för att läsa från fil.
     *
     * @return ArrayList med transfers, inlästa från datafil
     */
    public ArrayList<Transfer> loadTransfers() {
        String jsonFromFile = load(filenameTransfers);
        if (jsonFromFile == null) {
            System.out.println("Hittade inga sparade betalningsuppdrag.");
            return new ArrayList<>();
        }
        Type allTransfersType = new TypeToken<ArrayList<Transfer>>() {
        }.getType(); //Från "3. Using TypeToken", https://www.baeldung.com/gson-json-to-map
        return gson.fromJson(jsonFromFile, allTransfersType);
    }

    /**
     * Generell metod som returnerar den första raden i en textfil.
     *
     * @param fileName sökväg till textfil
     * @return String med första raden från textfilen
     */
    private String load(String fileName) {
        String StringToReturn = null;

        File dataFile = getFile(fileName);

        if (Objects.nonNull(dataFile)) {
            //Om datafil existerar så läses filen in
            try {
                Scanner reader = new Scanner(dataFile);
                StringToReturn = reader.nextLine();
                reader.close();
            } catch (FileNotFoundException e) {
                System.out.println("Antingen hittades ingen datafil eller så går den inte att läsa ifrån.");
            } catch (NoSuchElementException e) {
                System.out.println("Datafil är tom.");
            }
        }
        return StringToReturn;
    }

    /**
     * Skriver en textrad i en datafil.
     *
     * @param fileName    namnet på filen som ska skrivas till (till exempel "accounts.txt");
     * @param dataToWrite den textrad som skrivs till textfil
     */
    private void save(String fileName, String dataToWrite) {
        //todo implementera save() som använder en absolut filsökväg

        //getFile skapar File-objekt som pekar på filen som
        File dataFile = getFile(fileName);
        if (dataFile == null) return; //todo ändra till eget exception??

        //todo implementera exakt samma i load()

        //Om datafil existerar så läses filen in
        try {
            FileWriter fileWriter = new FileWriter(dataFile);
            fileWriter.write(dataToWrite);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Kunde inte spara data i " + dataFile.getAbsolutePath());
            System.out.println("Datan har inte sparats.");
            e.printStackTrace();
        }
    }

    /**
     * Kontrollerar om en fil existerar i filsökvägen som skapas av getDirectory() och försöker skapa filen om den inte existerar.
     *
     * @param fileName namnet på filen
     * @return Returnerar File-instans som pekar på filen om den existerar. Returnerar null om filen inte existerar och inte heller gick att skapa.
     */
    private File getFile(String fileName) {
        //todo tänk på att om filen redan existerar så ska programmet ändå fortsätta
        //Kontrollerar om mapp existerar, skapar annars
        StringBuilder filePathBuilder = getDirectory();

        //Kontrollerar det gick att skapa mapp
        if (Objects.isNull(filePathBuilder)) { //todo ändra till eget exeception?
            return null;
        }

        //filePathBuilder är nu absolut sökväg till namnet där
        filePathBuilder.append("\\").append(fileName);

        File dataFile = new File(filePathBuilder.toString());
        if (!dataFile.exists()) {                                                            //Om datafil inte existerar skapas ny fil
            System.out.println("Försöker skapa datafil " + filePathBuilder.toString());
            try {
                dataFile.createNewFile();
                System.out.println("Skapade datafil " + filePathBuilder.toString());
            } catch (IOException error) {
                System.out.println("Kunde inte skapa datafil " + filePathBuilder.toString());
                error.printStackTrace();
                System.out.println("Det går inte att spara utan datafil.");
                return null;
            }
        }
        //Om datafil inte gick att spara, fortsätt inte
        return dataFile;
    }

    /**
     * Försöker skapa en mapp i "C:\\users\\%username%\\AppData\\Local\\" om mappen inte redan existerar. Mappens namn bestäms i global-variabel folderName.
     *
     * @return StringBuilder-instans med sökväg till mappen. Returnerar null om mapp inte existerade och inte gick att skapa.
     */
    //todo eget exception?
    private StringBuilder getDirectory() {
        StringBuilder filePathBuilder = new StringBuilder();

        //Skapar sökväg till mapp
        filePathBuilder.append(System.getProperty("user.home"))
                .append("\\AppData\\Local\\")
                .append(folderName);

        //Skapar File-objekt med sökväg till mapp
        File folder = new File(filePathBuilder.toString());

        //Kontrollerar om sökväg till mapp existerar, skapar annars
        if (!folder.exists()) {
            if (folder.mkdir()) {
                System.out.println("Skapade mapp " + folder.getAbsolutePath());

            } else {
                //Om sökväg inte går att skapa
                System.out.println("Kunde inte skapa " + folder.getAbsolutePath());
                return null;
            }
        }
        return filePathBuilder;
    }
}
