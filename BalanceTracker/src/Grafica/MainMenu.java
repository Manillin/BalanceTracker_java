package Grafica;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


/**
 * Classe che rappresenta il Menu Principale dell'applicazione
 * @author Christian von Waldorff
 *
 */


public class MainMenu {
    private JMenuBar menuBar;
    private JMenuItem salva = new JMenuItem("Salva su file");
    private JMenuItem carica = new JMenuItem("Carica da file");
    private JMenuItem esportaTXT = new JMenuItem("Esporta in txt");
    private JMenuItem esportaCSV = new JMenuItem("Esporta in csv");
    private JMenuItem filtri = new JMenuItem("Filtri");
    private JMenuItem stampa = new JMenuItem("Stampa");
    private JMenu menu = new JMenu("Impostazioni");

    /**
     * Costruttore del menu
     * @param p Riferimento al Pannello Principale
     */
    public MainMenu(MainPanel p){
        menuBar = new JMenuBar();
        menu.add(salva);
        menu.add(carica);
        menu.add(esportaTXT);
        menu.add(esportaCSV);
        menu.add(filtri);
        menu.add(stampa);

        AscoltatoreMenu listener = new AscoltatoreMenu(p);
        salva.addActionListener(listener);
        carica.addActionListener(listener);
        esportaTXT.addActionListener(listener);
        esportaCSV.addActionListener(listener);
        filtri.addActionListener(listener);
        stampa.addActionListener(listener);

        menuBar.add(menu);
    }
    /**
     * Getter del menuBar
     * @return Riferimento al MenuBar
     */
    public JMenuBar getMenuBar(){
        return menuBar;
    }




}
