package Grafica;

import Classi.Bilancio;
import Classi.FiltroRicerca;
import Classi.TipoFiltro;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Classe che implementa l'ascoltatore dei filtri e permette di applicarli
 * @author Christian von Waldorff
 */


public class AscoltatoreFiltri implements ActionListener {
    private final JFrame frame;
    private final MainPanel mainPanel;
    private final JComboBox<String> comboBox;
    private final JComboBox<String> mese;
    private final JComboBox<String> settimana;
    private final JPanel sezione;
    private final JTextField giorno;
    private final JTextField anno;
    private String sceltaAttuale;
    private final JTextField inizioP;
    private final JTextField fineP;
    private final JLabel periodo;
    private final JPanel periodoPanel;


    public AscoltatoreFiltri(JFrame f,MainPanel p, JComboBox<String> cb,JComboBox<String> settimana,JComboBox<String> mese, JPanel sez,
                             JTextField giorno,JTextField anno, JTextField p1, JTextField p2) {
        this.frame = f;
        this.mainPanel = p;
        this.comboBox = cb;
        this.mese = mese;
        this.settimana = settimana;
        this.sezione = sez;
        this.giorno = giorno;
        this.anno = anno;
        this.inizioP = p1;
        this.fineP = p2;
        this.sceltaAttuale = "Giorno"; //scelta attuale per default è giorno
        periodo = new JLabel("Data inizio -- Data fine: ");
        periodoPanel = new JPanel();
        Bilancio b = p.getListaB();
    }


    /**
     * Metodo che mi permette di eseguire codice in base all'evento che si verifica
     * @param e evento che deve essere processato
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object temp = e.getSource();
        //controllo se listener è stato chiamato da un click sul bottone
        if (temp instanceof JButton) {
            FiltroRicerca f = null;
            switch (sceltaAttuale) {
                case "Giorno" ->{
                    f = new FiltroRicerca(TipoFiltro.Giorno, giorno.getText());
                    System.out.println("Filtro applicato -> " + f);}
                case "Settimana" -> {
                    f = new FiltroRicerca(TipoFiltro.Settimana, (String) settimana.getSelectedItem());
                    System.out.println("Filtro applicato -> " + f);
                }
                case "Mese" -> {
                    f = new FiltroRicerca(TipoFiltro.Mese, monthToInt((String) Objects.requireNonNull(mese.getSelectedItem()))); //aggiunto per warning
                    System.out.println("Filtro applicato -> " + f);
                }
                case "Anno" -> {
                    f = new FiltroRicerca(TipoFiltro.Anno, anno.getText());
                    System.out.println("Filtro applicato -> " + f);
                }
                case "Periodo" -> {
                    f = new FiltroRicerca((TipoFiltro.Periodo), inizioP.getText() + " <-> " + fineP.getText());
                    System.out.println("Filtro applicato -> " + f);
                }
                default -> System.out.println("Something went wrong [switch case] - AscoltatoreFiltri.java");
            }
            //Setto, filtro e aggiorno tabella
            mainPanel.setCurrentFilter(f);
            mainPanel.update();
        } else {
            String scelta = (String) comboBox.getSelectedItem(); //mi restituisce il filtro che l'user ha scelto (in Str)
            //Si esegue una clear del Panel Filtri per applicare i cambiamenti
            //se è == a giorno allora non si deve fare clear
            if (!Objects.equals(scelta, sceltaAttuale)) {
                sceltaAttuale = scelta;
                clearPanel(sezione);
                switch (scelta) {
                    case "Giorno" -> sezione.add(giorno);
                    case "Settimana" -> sezione.add(settimana);
                    case "Mese" -> sezione.add(mese);
                    case "Anno" -> sezione.add(anno);
                    case "Periodo" -> {
                        sezione.add(periodo);
                        periodoPanel.add(inizioP);
                        periodoPanel.add(fineP);
                        sezione.add(periodoPanel);
                    }
                    default ->
                            System.out.println("Something went wrong [switch case] - AscoltatoreFiltri.java ");
                }
                //dimensionamento del frame
                frame.pack();
            }
        }
    }

    /**
     * Resetta un pannello
     * @param sezione Panel da pulire
     */
    public void clearPanel(JPanel sezione) {
        sezione.removeAll();
    }

    /**
     * Metodo per trasformare un mese in una stringa corrispondente al numero di tale mese
     * @param mese -> Mese considerato
     * @return Ritorna la stringa corrispondente al numero del mese passato come parametro
     */
    private String monthToInt(String mese) {
        String t = mese.toLowerCase();
        return switch (t) {
            case "gennaio" -> "1";
            case "febbraio" -> "2";
            case "marzo" -> "3";
            case "aprile" -> "4";
            case "maggio" -> "5";
            case "giugno" -> "6";
            case "luglio" -> "7";
            case "agosto" -> "8";
            case "settembre" -> "9";
            case "ottobre" -> "10";
            case "novembre" -> "11";
            case "dicembre" -> "12";
            default -> "Error [MonthToInt switch case]";
        };

    }
}
