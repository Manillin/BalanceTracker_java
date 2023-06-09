import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

public class Main2{
    public static void main(String[] args)  {


        String info = Integer.toString(LocalDate.now().getYear());
        System.out.println( "Anno: " + info );
/*
        int infoInt = 2022;
        int mese = 8;
        LocalDate now = LocalDate.now();
        LocalDate inizio, fine;
        YearMonth giorniMese = YearMonth.of(now.getYear(),mese);
        int giorniMeseInt = giorniMese.lengthOfMonth();
        inizio = LocalDate.of(now.getYear(),mese,1);
        fine = LocalDate.of(now.getYear(),mese,giorniMeseInt);


        System.out.println(inizio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println(fine.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));



        JFrame f = new JFrame("Prova");

        JPanel P = new JPanel();
        JTextField text = new JTextField("Inserisci qui il testo",25);

        text.addFocusListener(new FocusListener(){

            @Override
            public void focusGained(FocusEvent e) {
                if(text.getText().equals("Inserisci qui il testo")){
                    text.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (text.getText().equals("")){
                    text.setText("Inserisci qui il testo");
                    text.setForeground(new Color(0,0,0,128));
                }
            }
        });

        JButton bottone = new JButton(" bottone prova");

        P.add(text);
        P.add(bottone);
        f.add(P);
        f.pack();
        f.setVisible(true);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



*/

        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);



        for(int i = 0; i< list.size();i++){
            System.out.println("Elemento # " + i + " == "+ list.get(i));
        }

        int[] selected = new int[]{4, 5};

        deleteElem(selected,list);

        System.out.println("\n\n--------------CANCELLAZIONE --------------\n\n");
        for(int i = 0; i< list.size();i++){
            System.out.println("Elemento # " + i + " == "+ list.get(i));
        }
    }

    public static void deleteElem(int[] rows, ArrayList<Integer> list){
        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()){
            Integer elem = iterator.next();
            for(int i = 0; i< rows.length;i++){
                if(list.indexOf(elem) == rows[i]){
                    iterator.remove();
                }
            }
        }


        /*for(int i = 0; i<rows.length;i++) {
            list.remove(list.get(rows[i]));
        }*/

    }
}
