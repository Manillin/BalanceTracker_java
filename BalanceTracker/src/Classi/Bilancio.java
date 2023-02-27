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
 * Classe che rappresenta l'insieme delle transazioni (struttura dati)
 */

public class Bilancio implements Salvabile, Serializable, Printable {
    private ArrayList<Transazione> listaB;
    private float sommaTot;
    private String valuta;


    //Inizializzazione del bilancio
    public Bilancio(){
        listaB = new ArrayList<Transazione>();
        sommaTot = 0F;
        valuta = "€";
    }

    //Caricamento di un bilancio
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

    public void setLista(ArrayList<Transazione> l){
        this.listaB = l;
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


    public void setSommaTot(float sommaTot) {
        this.sommaTot = sommaTot;
    }

    //Ritorna elemento all'indice 'i' dentro la lista di trasazioni
    public Transazione getTransazioneAt(int index){
        return listaB.get(index);
    }

    /**
     *
     * @param t -> Nuova transizione da registrare
     * @return true se la transizione ha esito positivo
     */
    public void addTransazione(Transazione t) {
        listaB.add(t);
        sommaTot += t.getAmmontare();
    }

    /**
     *
     * @param t -> Transizione da cancellare
     *
     */

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

    public void delTransazione2(Transazione t){
        listaB.removeIf(app -> app.equals(t));
    }


    // metodo da sviluppare -> public boolean fileLoad(){}
    // metodo da sviluppare -> public booelan fileSave(){}

    //Per test e debug
    public void printBalance(){
        for(int i= 0 ; i<listaB.size();i++){
            System.out.println(i+": "+listaB.get(i).toString());
        }
    }


    public void setListaB(ArrayList<Transazione> listaB) {
        this.listaB = listaB;
    }

    public String getValuta(){
        return valuta;
    }




    public boolean salvaSuFile(String fileName, String ext){
        FileOutputStream f = null;

        //Controllo ext:
        int inizio,fine;
        inizio = (fileName.length() - ext.length());
        fine = fileName.length();

        if(!fileName.subSequence(inizio,fine).equals(ext)){
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
