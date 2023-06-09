package Classi;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe per esportare in diversi formati
 * @author Christian von Waldorff
 */

public abstract class Esportatore {

    protected Bilancio b;

    public Esportatore(Bilancio b){this.b = b;}

    /**
     * Metodo abstract per esportare in diversi formati
     * @param fileName -> File target
     * @return -> True on success | False on failure
     */
    public abstract boolean esporta(String fileName);

    /**
     * Metodo che aggiunge l'estensione di file desiderata se non già presente
     * nel file scelto. Se pre-esistente non aggiunge l'estensione.
     * @param fileName -> Nome del file
     * @param ext -> Estensione per salvare file
     * @return -> Nuovo file writer o null in caso di fallimento
     *
     */
    public FileWriter checkFileName(String fileName, String ext){

        try {
            FileWriter fout;
            int inizio = fileName.length() - ext.length();
            int fine = fileName.length();
            if (!fileName.subSequence(inizio, fine).equals(ext)) {
                fout = new FileWriter(fileName + ext);
            } else {
                fout = new FileWriter(fileName);
            }
            return fout;
        }
        catch(IOException e){
            System.out.println("Apertura file fallita [EXP: "+ e.toString()+ " ]");
        }
        return null;
    }


    /**
     *  Esportatore writer -> Esporta nel formato scelto
     * @param fout -> FileWriter che scrive
     * @param separatore -> Separatore che denota il tipo di esportazione (CSV, TXT)
     * @throws IOException -> gestita dall'esportatore
     */
    public void expWriter(FileWriter fout,String separatore) throws IOException{
        String buff;
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
    }
}
