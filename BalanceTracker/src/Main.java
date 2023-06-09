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
        Transazione t4 = new Transazione("Giacca",153.56f,now2);
        Transazione t5 = new Transazione("Videogioco",54,now2);
        Transazione t6 = new Transazione("Cena",23,now2);
        Transazione t7 = new Transazione("Spotify",12.33f,now2);
        Transazione t8 = new Transazione("Spotify",12.33f,now2);
        Transazione t9 = new Transazione("Test",12,now2);
        Transazione t10 = new Transazione("test2",234.5f,now2);
        Transazione t11 = new Transazione("test3",98.4f,now2);
        Transazione t12= new Transazione("etet",12.5f,now2);
        Transazione t13= new Transazione("b",23.321f,now2);
        Transazione t14= new Transazione("ergerg",12.4f,now2);
        Transazione t15= new Transazione("dfbe",53.53f,now2);
        Transazione t16= new Transazione("eerh",12.234f,now2);
        Transazione t17 = new Transazione("erge",12.45f,now2);
        listaB.addTransazione(t2);
        listaB.addTransazione(t4);
        listaB.addTransazione(t5);
        listaB.addTransazione(t6);
        listaB.addTransazione(t7);
        listaB.addTransazione(t8);
        listaB.addTransazione(t17);
        listaB.addTransazione(t9);
        listaB.addTransazione(t10);
        listaB.addTransazione(t11);
        listaB.addTransazione(t12);
        listaB.addTransazione(t13);
        listaB.addTransazione(t14);
        listaB.addTransazione(t15);
        listaB.addTransazione(t16);


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