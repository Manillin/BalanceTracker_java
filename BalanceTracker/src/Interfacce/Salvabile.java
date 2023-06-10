package Interfacce;

/**
 * Interfaccia che permette di salvare o caricare su file
 * @author Christian von Waldorff
 */

public interface Salvabile {

    /**
     * Metodo per caricare un bilancio da un file
     * @param fileName -> Nome del file dal quale si vuole caricare il bilancio
     * @return -> True on success | False on failure
     */
    public boolean caricaDaFile(String fileName);

    /**
     * Metodo per salvare un bilancio su file
     * @param fileName -> Nome del file dove si vuole salvare il bilancio
     * @param ext -> Estensione con cui il file verrà salvato
     * @return -> True on success | False on failure
     */
    public boolean salvaSuFile(String fileName, String ext);


}
