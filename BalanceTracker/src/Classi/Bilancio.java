package Classi;
import Interfacce.Salvabile;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Christian von Waldorff
 * Classe che rappresenta l'insieme delle transazioni (struttura dati) con le sue primitive
 */

public class Bilancio implements Salvabile, Serializable, Printable {
    private ArrayList<Transazione> listaB;
    private float sommaTot;
    private String valuta;


    /**
     * Costruttori con e senza parametri per la creazione di un bilancio
     */
    public Bilancio(){
        listaB = new ArrayList<Transazione>();
        sommaTot = 0F;
        valuta = "€";
    }


    public Bilancio(ArrayList<Transazione> l, float somma ){
        this.listaB = l;
        this.sommaTot = somma;
        valuta = "€";

    }

    /**
     * Setter e getter della classe
     */
    public ArrayList<Transazione> getListaB() {
        return listaB;
    }

    public ArrayList<Transazione> getFilteredList(FiltroRicerca f){
        ArrayList<Transazione> l = new ArrayList<Transazione>();
        Transazione app;
        for (Transazione transazione : listaB) {
            app = transazione;
            if (f.isElementValid(transazione)) {
                l.add(app);
            }
        }
        return l;
    }

    public float getSommaTot() {
        sommaTot = 0;
        for (Transazione transazione : listaB) {
            sommaTot += transazione.getAmmontare();
        }
        return sommaTot;
    }

    public float getFilteredTot(FiltroRicerca f){
        float somma = 0;
        ArrayList<Transazione> l = getFilteredList(f);
        for (Transazione transazione : l) {
            somma += transazione.getAmmontare();
        }
        return somma;
    }


    //Ritorna elemento all'indice 'i' dentro la lista di transazioni
    public Transazione getTransazioneAt(int index){
        return listaB.get(index);
    }

    /**
     * Aggiunge una transazione all'arraylist
     * @param t -> Nuova transizione da registrare
     *
     */
    public void addTransazione(Transazione t) {
        listaB.add(t);
        sommaTot += t.getAmmontare();
    }


    public void delTransazione(Transazione t){
        Iterator<Transazione> iterator = listaB.iterator();
        while(iterator.hasNext()){
            Transazione tz = iterator.next();
            if(tz.equals(t)){
                iterator.remove();
                sommaTot -= tz.getAmmontare();
            }
        }
    }


    /*
    Classe per debugging
     */
    public void printBalance(){
        for(int i= 0 ; i<listaB.size();i++){
            System.out.println(i+": "+listaB.get(i).toString());
        }
    }

    public String getValuta(){
        return valuta;
    }


    /**
     * Metodo che permette di scrivere e salvare su un file il bilancio attualmente in uso
     *
     * @param fileName -> Nome del file dove si vuole salvare il bilancio
     * @param ext -> Estensione con cui il file verrà salvato
     * @return status
     */
    public boolean salvaSuFile(String fileName, String ext){
        FileOutputStream f = null;

        //Controllo ext:
        int inizio,fine;
        inizio = (fileName.length() - ext.length());
        fine = fileName.length();

        if(!fileName.subSequence(inizio,fine).equals(ext)){
            System.out.println("Aggiunta estensione al file -> " + ext);
            fileName = fileName+ext;
        }

        try{
            f = new FileOutputStream(fileName);
        }catch(IOException e){
            System.out.println("Errore nella scrittura su file [EXP: " + e.toString() + " ]");
            return false;
        }
        ObjectOutputStream os = null;
        System.out.println("Scrittura su file...");

        try{
            os = new ObjectOutputStream(f);
            os.writeObject(listaB);
            os.flush();
            os.close();
        }catch(IOException e){
            System.out.println("Errore nella scrittura su file [EXP: " + e.toString() + " ]");
            return false;
        }
        System.out.println("Scrittura terminata con successo!");
        return true;
    }


    /**
     * Metodo che legge e carica da un file un bilancio
     * @param fileName -> Nome del file dal quale si vuole caricare il bilancio
     * @return status
     */
    public boolean caricaDaFile(String fileName){
        System.out.println("Inizio caricamento da file...");
        System.out.println("Lettura dati: ");
        FileInputStream fin = null;
        ObjectInputStream is = null;
        try{
            fin = new FileInputStream(fileName);
            is = new ObjectInputStream(fin);
        }catch(IOException e){
            System.out.println("Errore caricamento da file [EXP: " + e.toString()+ " ]");
            return false;
        }
        listaB = null;
        try{
            listaB = (ArrayList<Transazione>) (is.readObject());
            is.close();
        }catch (IOException | ClassNotFoundException e){
            System.out.println("Errore caricamento da file [EXP: " + e.toString()+ " ]");
            return false;
        }
        return true;
    }


    /**
     * Classe che permette la stampa fisica di un bilancio -> estende printable
     *
     * @param g il contesto in cui viene disegnata la pagina
     * @param pf  le dimensioni e l'orientamento della pagina che viene disegnata
     * @param page  l'indice in base zero della pagina da disegnare
     * @return ritorna un intero
     * @throws PrinterException Nel caso la stampa fallisca
     */

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        int maxBilanciPerPagina = 25;
        String titolo = "LISTA BILANCI : \n";

        System.out.println("join with page = " + page);

        if ( (page*maxBilanciPerPagina) > listaB.size()-1) {
            return NO_SUCH_PAGE;
        }

        int y = (int) pf.getImageableY();
        System.out.println("x("+pf.getImageableX()+") , y(" + y +") ");
        y+=g.getFontMetrics().getHeight();
        System.out.println("x("+pf.getImageableX()+") , y(" + y +") ");
        g.drawString(titolo , (int) pf.getImageableX(), y);
        y+=g.getFontMetrics().getHeight();
        for (int i = page * maxBilanciPerPagina; i < Math.min((page + 1) * maxBilanciPerPagina, listaB.size() ); i++) {      // la condizione del ciclo for permette di non mettere più di 15 bilanci per pagina
            System.out.println("i = " + i);
            g.drawString(listaB.get(i).toString(valuta) , (int) pf.getImageableX(), y);
            System.out.println("x("+pf.getImageableX()+") , y(" + y +") ");
            y += g.getFontMetrics().getHeight();
        }
        return PAGE_EXISTS;
    }
}
