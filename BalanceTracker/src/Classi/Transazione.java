package Classi;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Christian von Waldorff
 * Classe che rappresenta una singola transazione -> compone il bilancio
 *
 */

public class Transazione implements Comparable, Serializable {
    private LocalDate data;
    private String descrizione;
    private float ammontare;

    /**
     * Costruttore della classe
     * @param descrizione -> Descrizione della transazione
     * @param ammontare -> Ammontare totale della transazione
     * @param data -> Data della transizione
     */

    public Transazione(String descrizione, float ammontare, LocalDate data ){
        this.data = data;
        this.descrizione = descrizione;
        this.ammontare = ammontare;
    }

    /**
     * Setter e Getter per le classi
     */

    public void setData(LocalDate data) {
        this.data = data;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public void setAmmontare(float ammontare) {
        this.ammontare = ammontare;
    }
    public float getAmmontare() {
        return ammontare;
    }
    public LocalDate getData() {
        return data;
    }
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public String toString(){
        return data.toString() +"  "+ descrizione +"  "+ ammontare;
    }


    /**
     * Potrebbe essere inutile questo metodo.
     * Metodo per comparare Transazioni in base alla loro data
     * @param obj Corrisponde alla Transazione target del confronto
     * @return Restituisce un intero < 0 se t2.data è anteriore a this.data | >0 se è posteriore | == 0 se coincide
     */
    @Override
    public int compareTo(Object obj) {
        //Transazione t2 = (Transazione) obj;
       // return this.data.compareTo(t2.getData()); //questo compareTO è della classe LocalDate
        return 0;
    }

    /**
     * Metodo per stampare tutti gli attributi di una transazione con la valuta compresa [tenere per debug]
     * @param valuta valuta della transazione
     * @return Transazione sotto forma di Stringa stampabile
     */
    public String toString(String valuta){
        return data.toString() + " " + ammontare + valuta+ " " + descrizione;
    }

}
