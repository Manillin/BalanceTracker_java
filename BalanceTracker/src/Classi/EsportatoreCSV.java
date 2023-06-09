package Classi;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Esportatore del bilancio nel formato CSV
 * @author Christian von Waldorff
 */

public class EsportatoreCSV extends Esportatore{

    public EsportatoreCSV(Bilancio b){super(b);}

    /**
     * Esporta il bilancio nel formato CSV
     * @param fileName -> File target
     * @return -> True on success | False on failure
     */
    @Override
    public boolean esporta(String fileName) {
        String separatore = ",";
        String estensione = ".csv";
        FileWriter fout = checkFileName(fileName,estensione);
        try{expWriter(fout,separatore);}
        catch (IOException e){
            System.out.println("Scrittura su file in CSV fallita [EXP: "+ e.toString()+ " ]");
            return false;
        }
        return true;
    }
}
