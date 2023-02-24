package Grafica;

import javax.swing.*;
import java.awt.*;

public class MainFrameFiltri extends JFrame {
    private String titolo;
    private final int numSezioni = 4;
    private MainPanel mainPanel;



    public MainFrameFiltri(String titolo, MainPanel mainPanel) throws HeadlessException {
        super(titolo);
        this.titolo = titolo;
        this.mainPanel = mainPanel;
        String formatData = "dd/MM/yyyy";

        //Creazione pannello e aggiunta elementi:
        JPanel panel = new JPanel(); //pannello display per filtri e opzioni
        panel.setLayout(new GridLayout(numSezioni,1));
        JPanel[] sezione = new JPanel[numSezioni];
        for(int i =0; i<numSezioni;i++){ sezione[i] = new JPanel();};
        //titolo
        JLabel titoloFinestra = new JLabel("Filtri: ");
        JButton confermaScelta = new JButton("Apply");


        //Filtro: Periodo
        JTextField periodoInizio = new JTextField(formatData , 8);
		JTextField periodoFine = new JTextField(formatData , 8);


        //Filtro: Giorno
        JTextField giorno = new JTextField(formatData,10);
        //ascoltatore per giorno per far apparire e sparire testo quando c'è il hover sulla casella.


        //Filtro: Mese !!OCCHIO A GENERICS !!
        JComboBox<String> mese = new JComboBox<String>();
        mese.addItem("Gennaio");
        mese.addItem("Febbraio");
        mese.addItem("Marzo");
        mese.addItem("Aprile");
        mese.addItem("Maggio");
        mese.addItem("Giugno");
        mese.addItem("Luglio");
        mese.addItem("Agosto");
        mese.addItem("Settembre");
        mese.addItem("Ottobre");
        mese.addItem("Novembre");
        mese.addItem("Dicembre");

        //Filtro: Anno
        JTextField anno = new JTextField("", 8);

        //Filtro: Settimana
        JComboBox<String> settimana = new JComboBox<String>();
        settimana.addItem("1");
        settimana.addItem("2");
        settimana.addItem("3");
        settimana.addItem("4");

        //Filtro: MainFiltro [padre]
        JComboBox<String> combo = new JComboBox<String>();
        combo.addItem("Giorno");
        combo.addItem("Settimana");
        combo.addItem("Mese");
        combo.addItem("Anno");
        combo.addItem("Periodo");

        //aggiunta elementi al JFrame

        sezione[0].add(titoloFinestra);
        sezione[1].add(combo);
        sezione[2].add(giorno);
        sezione[3].add(confermaScelta);

        AscoltatoreFiltri listener = new AscoltatoreFiltri(this,mainPanel,combo,settimana,mese,
                sezione[2],giorno,anno,periodoInizio,periodoFine);
        //aggiunta ascoltatore ai vari elementi
        combo.addActionListener(listener);
        confermaScelta.addActionListener(listener);


        //inserimento sezioni nel panel
        for(int i = 0; i<numSezioni;i++){panel.add(sezione[i]);}
        //inserimento panel nel frame
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }

}
