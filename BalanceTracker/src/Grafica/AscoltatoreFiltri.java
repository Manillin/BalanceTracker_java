package Grafica;

import Classi.Bilancio;
import Classi.FiltroRicerca;
import Classi.TipoFiltro;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * @author Christian von Waldorff
 * Classe che implementa l'ascoltatore e permette di applicare i filtri di ricerca
 */


public class AscoltatoreFiltri implements ActionListener {
    private JFrame frame;
    private MainPanel mainPanel;
    private JComboBox<String> comboBox;
    private JComboBox<String> mese;
    private JComboBox<String> settimana;
    private JPanel sezione;
    private JTextField giorno;
    private JTextField anno;
    private String sceltaAttuale;
    private JTextField inizioP;
    private JTextField fineP;
    private JLabel periodo;
    private JPanel periodoPanel;
    private Bilancio b;


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
        b = p.getListaB();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object temp = e.getSource();
        //controllo se listener è stato chiamato da un click sul bottone
        if (temp instanceof JButton) {
            FiltroRicerca f = null;
            switch (sceltaAttuale) {
                case "Giorno" ->{
                    f = new FiltroRicerca(TipoFiltro.Giorno, giorno.getText());
                    System.out.println("Filtro applicato -> " + f.toString());}
                case "Settimana" -> {
                    f = new FiltroRicerca(TipoFiltro.Settimana, (String) settimana.getSelectedItem());
                    System.out.println("Filtro applicato -> " + f.toString());
                }
                case "Mese" -> {
                    f = new FiltroRicerca(TipoFiltro.Mese, monthToInt((String) mese.getSelectedItem()));
                    System.out.println("Filtro applicato -> " + f.toString());
                }
                case "Anno" -> {
                    f = new FiltroRicerca(TipoFiltro.Anno, anno.getText());
                    System.out.println("Filtro applicato -> " + f.toString());
                }
                case "Periodo" -> {
                    f = new FiltroRicerca((TipoFiltro.Periodo), inizioP.getText() + "<->" + fineP.getText());
                    System.out.println("Filtro applicato -> " + f.toString());
                }
                default -> System.out.println("Something went wrong [switch case] - AscoltatoreFiltri.java line 58");
            }
            //Setto filtro e aggiorno tabella
            mainPanel.setCurrentFilter(f);
            mainPanel.update();
        } else {
            String scelta = (String) comboBox.getSelectedItem(); //mi restituisce il header del filtro che l'user ha scelto
            //se la scelta è diversa da default allora si fa clear è si aggiunge il componente
            //se è == a giorno allora non si deve fare clear
            //C'è comunque l'opzione per giorno in caso un user cambi filtro da giorno a filtro x e poi cambi idea e torni a filtrare per giorno.
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
                            System.out.println("Something went wrong [switch case] - AscoltatoreFiltri.java line 89");
                }
                //sezione.setVisible(false);
                //sezione.setVisible(true);
                frame.pack();
            }
        }
    }


    public void clearPanel(JPanel sezione) {
        sezione.removeAll();
    }

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
            default -> "Error";
        };

    }
}
