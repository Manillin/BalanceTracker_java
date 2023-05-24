package Grafica;

import Classi.Bilancio;
import Classi.FiltroRicerca;
import Classi.Transazione;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainPanel extends JPanel {
    private final int numSezioni = 5;
    private Bilancio listaB;
    private FiltroRicerca currentFilter;
    private JButton bAggiunta;
    private JButton bRimozione;
    private JButton bModifica;
    private JButton bRicerca;

    private JLabel tTotaleComplessivo;
    private JLabel totaleComplessivo;
    private JLabel stringaRicerca;
    private JLabel titoloFiltro;
    private MainTableModel dModel;
    private JTable table;
    private JScrollPane scrollTable;
    private JTextField fieldRicerca;
    private DecimalFormat floatFormat;

    public MainPanel(Bilancio b, FiltroRicerca filtroRicerca) {
        this.currentFilter = filtroRicerca;
        this.listaB = b;
        ArrayList<Transazione> l = b.getFilteredList(filtroRicerca);
        for (Transazione transazione : l) {
            System.out.println("Elemento -> " + transazione.toString());
        }
        floatFormat = new DecimalFormat("#.##");
        update();

    }

    public void update() {
        this.removeAll();
        this.setVisible(false);
        this.setVisible(true);

        System.out.println("Filtro attuale -> " + currentFilter.toString());
        // debug
        ArrayList<Transazione> l = listaB.getFilteredList(currentFilter);
        for (Transazione transazione : l) {
            System.out.println("Elemento -> " + transazione.toString());
        }

        // Formatto TOT in modo che escano solo due decimali dopo la virgola
        String tot = floatFormat.format(listaB.getFilteredTot(currentFilter)) + listaB.getValuta();
        System.out.println("Totale delle transazioni filtrate -> " + tot);

        // debug

        this.setLayout(new GridLayout(5, 1));
        JPanel[] sezione = new JPanel[numSezioni];
        for (int i = 0; i < numSezioni; i++) {
            sezione[i] = new JPanel();
        }

        // Z1
        tTotaleComplessivo = new JLabel("Totale Complessivo: ");
        totaleComplessivo = new JLabel("[ " + tot + " ]");
        // updateSomma(totaleComplessivo,tot);
        stringaRicerca = new JLabel("Cerca una transazione: ");
        titoloFiltro = new JLabel("Bilancio:  " + currentFilter.toString()); // <- aggiungere filtro data
        dModel = new MainTableModel(listaB, this, currentFilter);
        table = new JTable(dModel);
        centerTableElements(table);
        scrollTable = new JScrollPane(table);
        fieldRicerca = new JTextField("", 23);

        fieldRicerca.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (fieldRicerca.getText().equals("Nessun Elemento trovato")) {
                    fieldRicerca.setText("");
                    fieldRicerca.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // non serve
            }
        });

        bAggiunta = new JButton("Add");
        bRimozione = new JButton("Delete");
        bModifica = new JButton("Modify");
        bRicerca = new JButton("Ricerca");

        AscoltatoreMainPanel listener = new AscoltatoreMainPanel(bRicerca, bAggiunta, bRimozione, bModifica,
                listaB, this, dModel, totaleComplessivo, table, fieldRicerca);
        bAggiunta.addActionListener(listener);
        bRicerca.addActionListener(listener);
        bRimozione.addActionListener(listener);

        sezione[0].add(titoloFiltro);
        sezione[1].add(tTotaleComplessivo);
        sezione[1].add(totaleComplessivo);
        sezione[2].add(scrollTable);
        sezione[4].add(fieldRicerca);
        sezione[4].add(bRicerca);
        sezione[3].add(bAggiunta);
        sezione[3].add(bRimozione);

        for (int i = 0; i < numSezioni; i++) {
            if (i == 2) {
                add(scrollTable);
            } else {
                add(sezione[i]);
            }
        }
    }

    public void updateSomma(JLabel label, String tot) {
        label.setText("[ " + tot + " ]");
    }

    /**
     * Centra gli elementi delle celle della tabella passata in ingresso
     * 
     * @param table tabella in ingresso
     */
    private void centerTableElements(JTable table) {
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setCellRenderer(cellRenderer);
        }
    }

    public void setCurrentFilter(FiltroRicerca currentFilter) {
        this.currentFilter = currentFilter;
    }

    public Bilancio getListaB() {
        return listaB;
    }
}