package Classi;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Christian von Waldorff
 * Classe che rappresenta l'insieme delle transazioni (struttura dati)
 */

public class Bilancio implements Printable{
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
        for(int i= 0; i< listaB.size();i++){
            app = listaB.get(i);
            if(f.isElementValid(listaB.get(i))){
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
        Iterator<Transazione> iterator = listaB.iterator();
        while(iterator.hasNext()){
            Transazione app = iterator.next();
            if(app.equals(t)){
                iterator.remove();
            }
        }
    }


    // metodo da sviluppare -> public boolean fileLoad(){}
    // metodo da sviluppare -> public booelan fileSave(){}

    //Per test e debug
    public void printBalance(){
        for(int i= 0 ; i<listaB.size();i++){
            System.out.println(i+": "+listaB.get(i).toString());
        }
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        int maxBilanciPerPagina = 25;
        String titolo = "LISTA BILANCI : \n";

        System.out.println("join with page = " + pageIndex);

        if ( (pageIndex*maxBilanciPerPagina) > listaB.size()-1) {
            return NO_SUCH_PAGE;
        }
        int y = (int) pageFormat.getImageableY();
        System.out.println("x("+pageFormat.getImageableX()+") , y(" + y +") ");
        y+=graphics.getFontMetrics().getHeight();
        System.out.println("x("+pageFormat.getImageableX()+") , y(" + y +") ");
        graphics.drawString(titolo , (int) pageFormat.getImageableX(), y);
        y+=graphics.getFontMetrics().getHeight();
        for (int i = pageIndex * maxBilanciPerPagina; i < Math.min((pageIndex + 1) * maxBilanciPerPagina, listaB.size() ); i++) {      // la condizione del ciclo for permette di non mettere più di 15 bilanci per pagina
            System.out.println("i = " + i);
            graphics.drawString(listaB.get(i).toString(valuta) , (int) pageFormat.getImageableX(), y);
            System.out.println("x("+pageFormat.getImageableX()+") , y(" + y +") ");
            y += graphics.getFontMetrics().getHeight();
        }
        return PAGE_EXISTS;
    }


    public void setListaB(ArrayList<Transazione> listaB) {
        this.listaB = listaB;
    }
    public String getValuta(){
        return valuta;
    }


}
