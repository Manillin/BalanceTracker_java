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



    //COSTRUTTORE:
    public MainTableModel(Bilancio b, MainPanel p,FiltroRicerca filtro){
        this.listaB = b;
        //this.list = b.getListaB();
        this.filtro = filtro;
        this.list = b.getFilteredList(this.filtro);
        this.p = p;

    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return colName.length;
    }

    @Override
    public String getColumnName(int col){return colName[col];}

    @Override
    public boolean isCellEditable(int row, int col){
        return col <= 2;
    }

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
     * Gestisce le modifiche user sulla tabella
     * @param val   value to assign to cell
     * @param row   row of cell
     * @param col   column of cell
     */
    @Override
    public void setValueAt(Object val, int row, int col){
        String input = val.toString();
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
                    JOptionPane.showMessageDialog(null,"Formato non valido [ Formato richiesto: " + dateFormat + "","Attenzione",JOptionPane.ERROR_MESSAGE);
                }
                break;

            case 1:
                t.setDescrizione(input);
                break;

            case 2:
                input = input.replace('.', ',');
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
        fireTableDataChanged();
        //MainPanel.updatePan();
    }

    public static String getDateFormat() {
        return dateFormat;
    }
}
