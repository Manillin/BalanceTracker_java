package Grafica;

import Classi.Bilancio;
import Classi.FiltroRicerca;
import Classi.Transazione;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;


public class AscoltatoreMainPanel implements ActionListener, FocusListener {
    private JButton search;
    private JButton add;
    private JButton delete;
    private JButton modify;
    private Bilancio listaB;
    private MainPanel mainPanel;
    private MainTableModel model;
    private JLabel totComplessivo;
    private JTable table;
    private JTextField stringaRicerca;
    private int ultimaRicerca;
    private String bufferRicerca;


    //mancano variabili


    public AscoltatoreMainPanel(JButton search, JButton add, JButton delete, JButton modify,
                                Bilancio listaB, MainPanel mainPanel, MainTableModel model, JLabel totComplessivo,
                                JTable table, JTextField stringaRicerca) {
        this.search = search;
        this.add = add;
        this.modify = modify;
        this.delete = delete;
        this.listaB = listaB;
        this.mainPanel = mainPanel;
        this.model = model;
        this.totComplessivo = totComplessivo;
        this.table = table;
        this.stringaRicerca = stringaRicerca;
        this.ultimaRicerca = -1;
        this.bufferRicerca = "";

        //manca qualcosa
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object tmp = e.getSource();
        //se ho cliccato un bottone
        if (tmp instanceof JButton) {
            if (tmp == add) {
                String data = null, ammontare = null, descrizione = null;
                Boolean datiInseriti = false;
                int error = 1;
                LocalDate cData = LocalDate.now(); //serve come data default

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MainTableModel.getDateFormat());
                data = JOptionPane.showInputDialog("Data [ Formato: " + MainTableModel.getDateFormat() + " ]", cData.format(formatter));

                //Controlli servono per non fare aprire pannelli nel caso l'utente ne chiuda uno e non venga salvato nulla
                //nelle corrispettive variabili
                if (data != null) {
                    ammontare = JOptionPane.showInputDialog("Importo: ");
                }
                if (ammontare != null) {
                    descrizione = JOptionPane.showInputDialog("Descrizione: ");
                    datiInseriti = true;
                }

                if (datiInseriti) {
                    try {
                        cData = LocalDate.parse(data, formatter);
                        error++;
                        ammontare = ammontare.replace('.', ',');
                        float ammontareFloat = Float.parseFloat(ammontare);
                        Transazione t = new Transazione(descrizione, ammontareFloat, cData);
                        listaB.addTransazione(t); //OCCHIO
                        System.out.println("Elemento aggiunto alla lista con successo -> "+ t.toString());

                    } catch (Exception err) {
                        switch (error) {
                            case 1 ->
                                    JOptionPane.showMessageDialog(null, "Formato data errato [Formato " + MainTableModel.getDateFormat() + " ]", "Attenzione", JOptionPane.ERROR_MESSAGE);
                            case 2 ->
                                    JOptionPane.showMessageDialog(null, "Importo in formato invalido - puoi usare solo numeri e virgole", "Attenzione", JOptionPane.ERROR_MESSAGE);
                            default ->
                                    System.out.println("Something went wrong - reached illegal default branch in switch case [AscoltatoreMainPanel.java line 80]");
                        }
                    }
                    System.out.println("<DEBUG> - Totale Complessivo -> " + listaB.getSommaTot() + " euro");
                    mainPanel.update();

                }
            } else if (tmp == search) {
                System.out.println("Sono entrato nel tmp == search button");
                System.out.println("Stringa Ricerca -> " + stringaRicerca.getText());
                if (!cercaTransizione(table, stringaRicerca.getText())) {
                    stringaRicerca.setForeground(Color.red);
                    stringaRicerca.setText("Nessun Elemento trovato");
                }

            } else if (tmp == modify) {


            } else if (tmp == delete) {
                int result = JOptionPane.showConfirmDialog(null, "Sicuro di voler cancellare gli elementi selezionati?", "Conferma", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    int[] selectedRows = table.getSelectedRows();
                    deleteSelectedRows2(selectedRows);
                } else {
                    System.out.println("Utente non ha confermato la cancellazione");
                }
                mainPanel.updateSomma(totComplessivo, listaB);
            } else
                System.out.println("Something went wrong" +
                        " Illegal access to else branch -> [AscoltatoreMainPanel.java line 54]");
        }
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }


    /**
     * @param indici -> Array che contiene gli indici selezionati per la cancellazione
     *               iterator -> Iteratore per eliminare elementi ed evitare errori di OutOfBound Index
     */

    private void deleteSelectedRows(int[] indici) {
        ArrayList<Transazione> l = listaB.getListaB();
        Iterator<Transazione> iterator = l.iterator();

        //debug
        System.out.println("Elementi selezionati: " + indici.length);

        //debug

        for (int i = 0; i < indici.length; i++) {
            while (iterator.hasNext()) {
                Transazione app = iterator.next();
                if (app.equals(l.get(indici[i]))) {
                    iterator.remove();
                    break;
                }
            }
        }
        model.fireTableDataChanged();
    }

    private void deleteSelectedRows2(int[] rows){
        Transazione app;
        for(int i= 0; i< rows.length;i++){
            app = listaB.getTransazioneAt(rows[i]);
            listaB.delTransazione(app);
        }
        model.fireTableDataChanged();
    }






    /**
     *
     * @param table
     * @param input
     *  ultimaRicerca -> serve come indice per sapere se un elemento è gia stato trovato o meno
     *  inizializzato a -1 [nessun elemento della tabella è stato trovato]
     *  bufferRicerca -> Serve per non escludere a priori elementi nella tabella solo perchè
     *  precedenti a quello che si sta cercando attualmente
     * @return true se trova elemento false altrimenti
     */
    public boolean cercaTransizione(JTable table, String input){
        ArrayList<Transazione> l = listaB.getListaB(); //per ottenere lista filtrata
        if(bufferRicerca.equals("")){
            bufferRicerca = input;
        }
        if(!bufferRicerca.equals(input)){
            ultimaRicerca = -1;
        }
        for(int i=0; i<l.size();i++){
            if(l.get(i).getDescrizione().contains(input)  && (i > ultimaRicerca) ){
                if(ultimaRicerca == -1){
                    JOptionPane.showMessageDialog(null,"Trovato un elemento che rispetti il pattern","Avviso",JOptionPane.INFORMATION_MESSAGE);
                }
                table.setRowSelectionInterval(i,i);
                ultimaRicerca = i;
                bufferRicerca = input;
                return true;
            }
        }
        table.clearSelection();
        ultimaRicerca = -1;
        return false;
    }
}
