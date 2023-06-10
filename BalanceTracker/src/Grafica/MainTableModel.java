package Grafica;

import Classi.Bilancio;
import Classi.FiltroRicerca;
import Classi.Transazione;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class MainTableModel extends AbstractTableModel {
    private Bilancio listaB;
    private MainPanel p; //riferimento al pannello
    private String[] colName = {"Data", "Descrizione", "Ammontare"};
    private ArrayList<Transazione> list;
    private static String dateFormat = "dd/MM/yyyy";
    private String valuta = "€";
    private FiltroRicerca filtro;


    /**COSTRUTTORE:
     *
     * @param b Riferimento al bilancio
     * @param p Riferimento al Pannello principale
     * @param filtro Filtro di ricerca applicato alla tabella
     */
    public MainTableModel(Bilancio b, MainPanel p,FiltroRicerca filtro){
        this.listaB = b;
        this.filtro = filtro;
        this.list = b.getFilteredList(this.filtro);
        this.p = p;
    }

    /**
     * Restituisce il numero di righe
     * @return Numero di righe
     */
    @Override
    public int getRowCount() {
        return list.size();
    }

    /**
     * Restituisce il numero di colonne
     * @return Numero di colonne
     */
    @Override
    public int getColumnCount() {
        return colName.length;
    }

    /**
     * Restituisce il nome della colonna indicizzata
     * @param col  La colonna indicizzata
     * @return Stringa che contiene il nome della colonna
     */
    @Override
    public String getColumnName(int col){return colName[col];}

    /**
     * Metodo che dice se una certa riga è editabile o meno
     * @param row  Indice della riga
     * @param col Indice della colonna
     * @return Un booleano che indica lo status di modifica della cella
     */
    @Override
    public boolean isCellEditable(int row, int col){
        return col <= 2;
    }


    /**
     * Restituisce il valore dell'elemento nella cella specificata dalle coordinate rowIndex e columnIndex.
     * @param rowIndex l'indice della riga della cella
     * @param columnIndex l'indice della colonna della cella
     * @return  l'oggetto che rappresenta il valore della cella specificata
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //seleziono la transazione da mostrare da dentro la lista
        Transazione t = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
                 return t.getData().format(formatter);
            case 1:
                return t.getDescrizione();
            case 2:
                return t.getAmmontare() + valuta;
            default:
                return "ERRORE";
        }
    }

    /**
     * Gestisce le modifiche dell'user sulla tabella
     * @param val   Valore che l'utente vuole sostituire dentro la cella
     * @param row   Riga della cella
     * @param col   Colonna della cella
     */
    @Override
    public void setValueAt(Object val, int row, int col){
        String input = val.toString(); //valore da sostituire trasformato in Stringa
        //istruzioni
        Transazione t = list.get(row);
        switch(col){
            case 0:
                try{
                    DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern(dateFormat);
                    LocalDate date = LocalDate.parse(input, dFormatter);
                    t.setData(date);
                } catch (Exception e) {
                    System.out.println("Input sul date format sbagliato [EXP : " + e.toString() + " ]");
                    JOptionPane.showMessageDialog(null,"Formato non valido [ Formato richiesto: " + dateFormat + " ]","Attenzione",JOptionPane.ERROR_MESSAGE);
                }
                break;

            case 1:
                t.setDescrizione(input);
                break;

            case 2:
                input = input.replace(",", ".");
                input = input.replace("€", "");
                try{
                    Scanner scanner = new Scanner(input);
                    float newAmmontare = scanner.nextFloat();
                    System.out.println(newAmmontare);
                    t.setAmmontare(newAmmontare);
                }catch(Exception e){
                    System.out.println("Input scelto come nuovo ammontare sbagliato [EXP: "+e.toString()+" ]");
                    JOptionPane.showMessageDialog(null,"Solo numeri sono validi", "Attezione",JOptionPane.ERROR_MESSAGE);
                }
                break;


            default:
                System.out.println("Colonna non modificabile");
        }
        p.update();
    }

    public static String getDateFormat() {
        return dateFormat;
    }
}
