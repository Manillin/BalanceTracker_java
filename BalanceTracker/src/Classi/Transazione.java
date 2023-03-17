package Classi;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Classe che rappresenta una singola transazione -> compone il bilancio
 * @author Christian von Waldorff
 */

public class Transazione implements Comparable, Serializable {
    private LocalDate data;
    private String descrizione;
    private float ammontare;

    /**
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


    //Have a look here - forse @deprecated
    @Override
    public int compareTo(Object obj) {
        Transazione t2 = (Transazione) obj;
        return this.data.compareTo(t2.getData());
    }


    public String toString(String valuta){
        return data.toString() + " " + ammontare + valuta+ " " + descrizione;
    }

}
