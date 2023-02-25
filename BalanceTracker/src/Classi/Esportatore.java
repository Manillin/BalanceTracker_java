package Classi;

/**
 * Classe per esportare
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

}
