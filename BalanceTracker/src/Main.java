import Classi.Bilancio;
import Classi.FiltroRicerca;
import Classi.TipoFiltro;
import Classi.Transazione;
import Grafica.MainMenu;
import Grafica.MainPanel;

import javax.swing.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        JFrame mainframe = new JFrame();
        Bilancio listaB = new Bilancio();


        LocalDate now = LocalDate.now();
        LocalDate now2 = LocalDate.now();
        LocalDate data = LocalDate.of(2022, 12, 20);
        Transazione t1 = new Transazione("Caramelle",12,now);
        Transazione t2 = new Transazione("Pantaloni",134,now2);
        Transazione t4 = new Transazione("Giacca",153,now2);
        Transazione t5 = new Transazione("Videogioco",54,now2);
        Transazione t6 = new Transazione("Cena",23,now2);
        Transazione t7 = new Transazione("Spotify",12,now2);
        Transazione t8 = new Transazione("Test",12,data);
        listaB.addTransazione(t1); listaB.addTransazione(t2);
        listaB.addTransazione(t4); listaB.addTransazione(t5);
        listaB.addTransazione(t6); listaB.addTransazione(t7);
        listaB.addTransazione(t8);


        FiltroRicerca filtro = new FiltroRicerca();


        MainPanel mainP = new MainPanel(listaB, filtro);
        MainMenu menu = new MainMenu(mainP);
        mainframe.setJMenuBar(menu.getMenuBar());
        mainframe.add(mainP);
        mainframe.pack();
        mainframe.setVisible(true);
        mainframe.setLocationRelativeTo(null); //centra frame
        mainframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}