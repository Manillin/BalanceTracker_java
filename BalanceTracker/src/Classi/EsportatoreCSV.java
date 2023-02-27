package Classi;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
        FileWriter fout = null;

        try{
            fout = new FileWriter(fileName + estensione);
        }catch(IOException e){
            System.out.println("Apertura file fallita [EXP: "+ e.toString() + " ]");
            return false;
        }
        try{
            String buff = null;
            ArrayList<Transazione> l = b.getListaB();
            for (Transazione t : l) {
                buff = t.getData().toString();
                fout.write(buff + separatore);
                buff = Float.toString(t.getAmmontare());
                fout.write(buff + separatore);
                buff = t.getDescrizione();
                fout.write(buff + "\n");
            }
            fout.close();
        }catch (IOException e){
            System.out.println("Scrittura su file in CSV fallita [EXP: "+ e.toString()+ " ]");
            return false;
        }
        return true;
    }

}
