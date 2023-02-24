package Grafica;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterJob;

public class AscoltatoreMenu implements ActionListener {
    private MainPanel mainPanel;



    public AscoltatoreMenu(MainPanel p){
        this.mainPanel = p;
    }

    //to do in case of input:

    @Override
    public void actionPerformed(ActionEvent e) {
        String input =e.getActionCommand();
        System.out.println("Comando scelto: " + input);

        switch (input) {
            case "Salva su file" -> salvaSuFile();
            case "Carica da file" -> caricaDaFile();
            case "Filtri" -> {
                System.out.println("Apertura nuovo frame (per filtri) ");
                MainFrameFiltri filtri = new MainFrameFiltri("Filtri per ricerca: ", mainPanel);
            }
            case "Stampa" -> stampaContenuto();
            //Altri casi da aggiungere
        }
    }
    public void salvaSuFile(){
        //salva i dati su un file
    }

    public void caricaDaFile(){
        //Carica i dati da un file
    }

    public void stampaContenuto(){
        for(int i =0; i<100;i++){
            System.out.println("CIAOOOOOOOOOOOO");
        }
        System.out.println("Avvio funzione di stampa su carta");
        JOptionPane.showMessageDialog(null,"Pannello stampa","Messaggio", JOptionPane.INFORMATION_MESSAGE);
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(mainPanel.getListaB());
    };




}
