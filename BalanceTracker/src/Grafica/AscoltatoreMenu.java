package Grafica;

import Classi.Bilancio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterJob;
import java.io.File;

public class AscoltatoreMenu implements ActionListener {
    private MainPanel mainPanel;


    public AscoltatoreMenu(MainPanel p) {
        this.mainPanel = p;
    }

    //to do in case of input:

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = e.getActionCommand();
        System.out.println("Comando scelto: " + input);

        switch (input) {
            case "Salva su file" -> salvaSuFile();
            case "Carica da file" -> caricaDaFile();
            case "Filtri" -> {
                System.out.println("Apertura nuovo frame (per filtri) ");
                MainFrameFiltri filtri = new MainFrameFiltri("Filtri per ricerca: ", mainPanel);
            }
            //Altri casi da aggiungere
        }
    }

    public void salvaSuFile() {
        Bilancio b;
        JFileChooser chooser = new JFileChooser();
        int resp = chooser.showSaveDialog(null);

        //se l'utente conferma il savlvataggio
        if (resp == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (file.exists()) {
                int res = JOptionPane.showConfirmDialog(null, "Vuoi sovrascrivere il file ?", "Conferma", JOptionPane.YES_NO_CANCEL_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    b = mainPanel.getListaB();
                    System.out.println("File scelto -> " + file.toString());
                    if (b.salvaSuFile(file.toString() + ".bin", ".bin")) { //forzo estensione ".bin"
                        JOptionPane.showMessageDialog(null, "Salvataggio eseguito correttamente", "Avviso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Errore nel salvataggio su file", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (res == JOptionPane.NO_OPTION) {
                    System.out.println("Salvataggio dumped");
                } else {
                    System.out.println("Chiuso pop-up salvataggio");
                }
            }
            //Se il file non esiste
            else {
                System.out.println("File scelto -> " + file.toString());
                b = mainPanel.getListaB();
                if (b.salvaSuFile(file.toString(), ".bin")) {
                    JOptionPane.showMessageDialog(null, "Salvataggio eseguito correttamente", "Avviso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Errore nel salvataggio su file", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void caricaDaFile(){
        Bilancio b;
        JFileChooser chooser = new JFileChooser();
        int resp = chooser.showOpenDialog(null);
        //se clicca pulsante per salvare
        if(resp == JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            b = mainPanel.getListaB();
            if(b.caricaDaFile(file.toString())){
                JOptionPane.showMessageDialog(null,"Caricamento eseguito con successo", "Conferma",JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null,"Caricamento fallito", "Errore",JOptionPane.ERROR_MESSAGE);
            }
        }
        mainPanel.update();
    }
















}
