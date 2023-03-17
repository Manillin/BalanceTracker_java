package Classi;

import Grafica.MainTableModel;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

//import org.jopendocument.dom.spreadsheet.SpreadSheet;
/*
-> Mancano le librerie esterne

 _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _NON DA IMPLEMENTARE_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _








public class EsportatoreODS extends Esportatore{
    public EsportatoreODS(Bilancio b){super(b);}

    @Override
    public boolean esporta(String fileName) {

        String[] columns = new String[]{"TABELLA BILANCIO: " , "" , "" };
        Transazione t;
        ArrayList<Transazione> list = b.getListaB();

        //creazione di matrice di oggetti
        final Object[][] data = new Object[list.size()+1][columns.length];
        data[0] = new Object[]{"Data", "Descrizione", "Ammontare"};

        for(int i= 1;i< list.size();i++){
            t = list.get(i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MainTableModel.getDateFormat());
            data[i] = new Object[]{t.getData().format(formatter), t.getDescrizione(), t.getAmmontare()};
        }
        TableModel model = new DefaultTableModel(data,columns);

        try{
            final File file = new File(fileName);
            //SpreadSheet
        }catch (Exception e){
            System.out.println("Errore Esportazione ODS [EXP: "+ e.toString()+ " ]");
            return false;
        }
        return true;
    }
}


 */

