package Classi;
//Filtro data

import Grafica.MainTableModel;

import javax.swing.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;


/**
 *Classe che implementa la logistica a periodi neccessaria per applicare i filtri di ricerca per le transazioni
 * @author Christian von Waldorff
 */

public class FiltroRicerca {
    private TipoFiltro fType;
    private String info;
    private int infoInt;
    private LocalDate inizio;
    private LocalDate fine;

    //Costruttore con parametri
    public FiltroRicerca(TipoFiltro filtro, String info){
        this.fType = filtro;
        this.info = info;
        genRange();
    }

    //Costruttore senza parametri
    public FiltroRicerca(){
        this.fType = TipoFiltro.Anno;
        this.info = Integer.toString(LocalDate.now().getYear());
        genRange();
    }

    //logica a periodi -> PeriodoIniziale to PeriodoFinale
    public void genRange(){
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MainTableModel.getDateFormat());
        LocalDate date;
        Boolean error = false;

        if(fType == TipoFiltro.Giorno){
            try{
                //controlla che date sia nel formato giusto, altrimenti solleva eccezione
                date = LocalDate.parse(info,formatter);
                inizio = fine = date; //giorno singolo su cui effettuare la ricerca
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Formato data sbagliato [Formato: " + MainTableModel.getDateFormat()+" ]", "Attenzione", JOptionPane.ERROR_MESSAGE);
                error = true;
            }
        }

        else if(fType == TipoFiltro.Periodo){
            String[] periodo = info.split(" <-> ");
            try{
                inizio = LocalDate.parse(periodo[0],formatter);
                fine = LocalDate.parse(periodo[1],formatter);

            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Formato date sbagliato [Formato: " + MainTableModel.getDateFormat()+" ]", "Attenzione", JOptionPane.ERROR_MESSAGE);
                error = true;
            }

        }

        //Entro se mi serve l'info come intero, cioè quando non filtro per periodo o giorno.
        else{
            try{
                infoInt = Integer.parseInt(info);
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,"Formato anno sbagliato [ Formato: YYYY ]", "Attenzione", JOptionPane.ERROR_MESSAGE);
                error = true;
            }
        }

        if(!error &&fType == TipoFiltro.Anno){ //infoInt contiene anno
            inizio = LocalDate.of(infoInt,1,1); //primo gennaio current year
            fine = LocalDate.of(infoInt,12,31); //31 dicembre current year
        }

        else if(fType == TipoFiltro.Mese){ //infoInt contiene mese
            inizio = LocalDate.of(now.getYear(), infoInt,1); //primo giorno di un dato mese
            YearMonth mese = YearMonth.of(now.getYear(),infoInt);
            int giorniMese = mese.lengthOfMonth();
            fine = LocalDate.of(now.getYear(), infoInt,giorniMese);
        }

        else if(fType == TipoFiltro.Settimana){ //info int contiene il numero dell settimana (del mese)
            switch (infoInt) {
                case 1 -> {
                    inizio = LocalDate.of(now.getYear(), now.getMonth(), 1);
                    fine = LocalDate.of(now.getYear(), now.getMonth(), 8);
                }
                case 2 -> {
                    inizio = LocalDate.of(now.getYear(), now.getMonth(), 9);
                    fine = LocalDate.of(now.getYear(), now.getMonth(), 16);
                }
                case 3 -> {
                    inizio = LocalDate.of(now.getYear(), now.getMonth(), 17);
                    fine = LocalDate.of(now.getYear(), now.getMonth(), 24);
                }
                case 4 -> {
                    inizio = LocalDate.of(now.getYear(), now.getMonth(), 25);
                    YearMonth mese = YearMonth.of(now.getYear(), now.getMonth());
                    int giorniMese = mese.lengthOfMonth();
                    fine = LocalDate.of(now.getYear(), now.getMonth(), giorniMese);
                }
            }
        }
        //In caso di errori si mette come filtro di default il filtro per anno
        if(error){
            this.fType = TipoFiltro.Anno;
            info = Integer.toString(LocalDate.now().getYear());
            genRange();
        }
    }

    /**
     * Metodo che trasforma in stringa il mio filtro
     * @return Filtro trasformato in stringa
     */
    @Override
    public String toString(){
        if(fType == TipoFiltro.Anno){
            return "Anno "+ info;
        }else if(fType == TipoFiltro.Giorno){
            String targetDay = inizio.getDayOfMonth() + "/" + inizio.getMonthValue() + "/" + inizio.getYear();
            return "Giorno " + targetDay;
        }else if(fType == TipoFiltro.Mese){
            switch(info){
                case "1":
                    return "Mese " + " Gennaio-"+LocalDate.now().getYear();
                case "2":
                    return "Mese " + " Febbraio-"+LocalDate.now().getYear();
                case "3":
                    return "Mese " + " Marzo-"+LocalDate.now().getYear();
                case "4":
                    return "Mese " + " Aprile-"+LocalDate.now().getYear();
                case "5":
                    return "Mese " + " Maggio-"+LocalDate.now().getYear();
                case "6":
                    return "Mese " + " Giugno-"+LocalDate.now().getYear();
                case "7":
                    return "Mese " + " Luglio-"+LocalDate.now().getYear();
                case "8":
                    return "Mese " + " Agosto-"+LocalDate.now().getYear();
                case "9":
                    return "Mese " + " Settembre-"+LocalDate.now().getYear();
                case "10":
                    return "Mese " + " Ottobre-"+LocalDate.now().getYear();
                case "11":
                    return "Mese " + " Novembre-"+LocalDate.now().getYear();
                case "12":
                    return "Mese " + " Dicembre-"+LocalDate.now().getYear();
            }
        }else if(fType == TipoFiltro.Settimana){
            return "Settimana #"+info+" ";
        }else if(fType == TipoFiltro.Periodo){
            return "[ "+info+" ]";
        }
        return "Something went wrong [FiltroRicerca.java line 177]";
    }


    /**
     *
     * @param t -> Transazione su cui fare il controllo con il filtro attuale
     * La funzione compareTo(LocalDate data) di LocalDate mi confronta due date, e restituisce:
     *          <0 se la data è precedente | >0 se la data è successiva | == 0 se la data è uguale
     *          in base a questo decreto se una data rispetta l'intervallo in accordo con il filtro scelto
     * @return true se la data rispetta filtro | false se la dat anon rispetta il filtro
     */

    public boolean isElementValid(Transazione t){
        return (t.getData().compareTo(inizio)) >= 0 && ((t.getData().compareTo(fine)) <= 0);
    }
}