package Grafica;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainMenu {
    private JMenuBar menuBar;
    private JMenuItem salva = new JMenuItem("Salva su file");
    private JMenuItem carica = new JMenuItem("Carica da file");
    private JMenuItem filtri = new JMenuItem("Filtri");
    private JMenuItem stampa = new JMenuItem("Stampa");
    private JMenu menu = new JMenu("Impostazioni");
    private MainPanel mainPanel;

    public MainMenu(MainPanel p){
        this.mainPanel = p;
        menuBar = new JMenuBar();
        menu.add(salva);
        menu.add(carica);
        menu.add(filtri);
        menu.add(stampa);

        AscoltatoreMenu listener = new AscoltatoreMenu(p);
        salva.addActionListener(listener);
        carica.addActionListener(listener);
        filtri.addActionListener(listener);
        stampa.addActionListener(listener);

        menuBar.add(menu);
    }

    public JMenuBar getMenuBar(){
        return menuBar;
    }




}
