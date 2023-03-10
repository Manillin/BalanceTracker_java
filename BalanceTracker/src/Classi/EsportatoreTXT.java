package Classi;

import java.io.FileWriter;
import java.io.IOException;

public class EsportatoreTXT extends Esportatore{

    public EsportatoreTXT(Bilancio b){super(b);}

    /**
     * Esporta il bilancio nel formato TXT
     * @param fileName -> File target
     * @return -> True on success | False on failure
     */
    @Override
    public boolean esporta(String fileName) {
        String separatore = " ";
        String estensione = ".txt";
        FileWriter fout;
        fout = checkFileName(fileName,estensione);
        try{expWriter(fout,separatore);}
        catch (IOException e){
            System.out.println("Scrittura su file in CSV fallita [EXP: "+ e.toString()+ " ]");
            return false;
        }
        return true;
    }
}
