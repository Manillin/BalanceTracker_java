package Grafica;

import Classi.Bilancio;
import Classi.Transazione;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Classe che implementa l'ascoltatore in grado di reagire a interazioni con il pannello principale
 * @author Christian von Waldorff
 *
 */
public class AscoltatoreMainPanel implements ActionListener {
    private final JButton search;
    private final JButton add;
    private final JButton delete;
    private final Bilancio listaB;
    private final MainPanel mainPanel;
    private final JTable table;
    private final JTextField stringaRicerca;
    private int ultimaRicerca;
    private String bufferRicerca; //contiene l'ultimo pattern passato come target ricerca

    // Costruttore - PULIRE PARAMETRI INUTILI
    public AscoltatoreMainPanel(JButton search, JButton add, JButton delete, JButton modify,
            Bilancio listaB, MainPanel mainPanel, MainTableModel model, JLabel totComplessivo,
            JTable table, JTextField stringaRicerca) {
        this.search = search;
        this.add = add;
        this.delete = delete;
        this.listaB = listaB;
        this.mainPanel = mainPanel;
        this.table = table;
        this.stringaRicerca = stringaRicerca;
        this.ultimaRicerca = -1;
        this.bufferRicerca = "";
    }

    /**
     * Metodo che permette di reagire ad eventi sul pannello principale
     * 
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object tmp = e.getSource();
        // se ho cliccato un bottone
        if (tmp instanceof JButton) {
            if (tmp == add) {
                String data = null, ammontare = null, descrizione = null;
                boolean datiInseriti = false; //variabile di checkpoint
                int error = 1; //variabile per gestione del problema
                LocalDate cData = LocalDate.now(); // serve come data default

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MainTableModel.getDateFormat());
                data = JOptionPane.showInputDialog("Data [ Formato: " + MainTableModel.getDateFormat() + " ]",
                        cData.format(formatter));

                // Controlli: servono per non fare aprire i pannelli successivi nel caso d'interruzione prematura
                if (data != null) {
                    ammontare = JOptionPane.showInputDialog("Importo: ");
                }
                if (ammontare != null) {
                    descrizione = JOptionPane.showInputDialog("Descrizione: ");
                    datiInseriti = true; //checkpoint raggiunto, i dati inseriti sono coerenti e corretti
                }

                if (datiInseriti) {
                    try {
                        cData = LocalDate.parse(data, formatter);
                        error++;
                        ammontare = ammontare.replace(",", ".");
                        ammontare = ammontare.replace("€", "");
                        float ammontareFloat = Float.parseFloat(ammontare);
                        Transazione t = new Transazione(descrizione, ammontareFloat, cData);
                        listaB.addTransazione(t);
                        System.out.println("Elemento aggiunto alla lista con successo -> " + t);
                    } catch (Exception err) {
                        switch (error) {
                            case 1 ->
                                JOptionPane.showMessageDialog(null,
                                        "Formato data errato [Formato " + MainTableModel.getDateFormat() + " ]",
                                        "Attenzione", JOptionPane.ERROR_MESSAGE);
                            case 2 ->
                                JOptionPane.showMessageDialog(null,
                                        "Importo in formato invalido - puoi usare solo numeri e virgole", "Attenzione",
                                        JOptionPane.ERROR_MESSAGE);
                            default ->
                                System.out.println(
                                        "Something went wrong - reached illegal default branch in switch case [AscoltatoreMainPanel.java]");
                        }
                    }
                    System.out.println("Totale Complessivo -> " + listaB.getSommaTot() + " euro"); //debug
                    mainPanel.update();
                }
            } else if (tmp == search) {
                System.out.println("Sono entrato nel tmp == search button");
                System.out.println("Stringa Ricerca -> " + stringaRicerca.getText());
                if (!cercaTransizione(table, stringaRicerca.getText())) {
                    stringaRicerca.setForeground(Color.red);
                    stringaRicerca.setText("Nessun Elemento trovato");
                }

            } else if (tmp == delete) {
                int result = JOptionPane.showConfirmDialog(null, "Sicuro di voler cancellare gli elementi selezionati?",
                        "Conferma", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    int[] selectedRows = table.getSelectedRows();
                    deleteSelectedRows(selectedRows);
                } else {
                    System.out.println("Cancellazione annullata");
                }
            } else
                System.out.println("Something went wrong" +
                        " Illegal access to else branch -> [AscoltatoreMainPanel.java]");
        }

    }

    /**
     * Metodo per cancellare un singolo elemento o piu elementi dal bilancio
     * @param indici -> Array che contiene gli indici selezionati per la
     *                  cancellazione
     */

    private void deleteSelectedRows(int[] indici) {
        ArrayList<Transazione> l = listaB.getListaB();

        // debug
        System.out.println("Elementi selezionati: " + indici.length);

        int cont = 0;
        for (int j : indici) {
            l.remove(j - cont);
            cont++;
        }
        mainPanel.update();

        /*
         * Vecchio ciclo for
         * for(int i=0 ; i<indici.length ; i++) {
         * l.remove(indici[i]-cont);
         * cont++;
         * }
         */
    }

    /**
     *
     * @param table -> tabella di visualizzazione
     * @param input -> Stringa di ricerca
     *              ultimaRicerca -> serve come indice per sapere se un elemento è
     *              gia stato trovato o meno
     *              inizializzato a -1 [nessun elemento della tabella è stato
     *              trovato]
     *              bufferRicerca -> Serve per non escludere a priori elementi nella
     *              tabella solo perché precedenti a quello che si sta cercando attualmente
     * @return true se trova elemento false altrimenti
     */
    public boolean cercaTransizione(JTable table, String input) {
        //controllo nel caso in cui l'utente provi a fare una ricerca senza passare nessun pattern
        if(input.equals("")){
            return false;
        };
        ArrayList<Transazione> l = listaB.getListaB(); // per ottenere lista filtrata
        if (bufferRicerca.equals("")) {
            bufferRicerca = input;
        }
        if (!bufferRicerca.equals(input)) {
            ultimaRicerca = -1;
        }
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).getDescrizione().contains(input) && (i > ultimaRicerca)) {
                if (ultimaRicerca == -1) {
                    JOptionPane.showMessageDialog(null, "Trovato un elemento che rispetti il pattern", "Avviso",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                table.setRowSelectionInterval(i, i);
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
