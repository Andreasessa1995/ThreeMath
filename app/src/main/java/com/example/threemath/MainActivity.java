package com.example.threemath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView testoDomanda ;
    TextView testoCategoriaDomanda;
    Button A ,B,C,D;
    ArrayList<Domanda> quesito;
    int i = 0;
    int numeroDomande = 3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // categoria domanda ( addizione sottrazione ...)
        testoCategoriaDomanda =  ( TextView) findViewById(R.id.categoriaDomanda);

        // testo domanda ( 50+4 ....)
        testoDomanda =  (TextView) findViewById(R.id.corpoDomandaTesto);

        //bottoni 50-45-54-60 ....)
        A = (Button) findViewById(R.id.A);
        B= (Button) findViewById(R.id.B);
        C = (Button) findViewById(R.id.C);
        D = (Button) findViewById(R.id.D);


         quesito = new ArrayList<>();


        Domanda domanda1 = new Domanda("Come ti chiami ","andrea","giacomo","mario","amedeo");
        Domanda domanda2 = new Domanda("Il tuo cognome  ","sessa","landi","de paoli","brighi");
        Domanda domanda3 = new Domanda("Il cognome di mamma  ","sessa","landi","rossi","brighi");





        String x = domanda1.getDomanda();

        quesito.add(domanda1);
        quesito.add(domanda2);
        quesito.add(domanda3);
        
        int sizeq = quesito.size();

       // String y = quesito.get(0).getDomanda();
       // String j = quesito.get(0).getRispostaEsatta();

        Log log = null;
     //   log.d("DEBUG","domanda in arrivo = "+ y);
      //  log.d("DEBUG","domanda in arrivo-risposta esatta= "+ j);

     //   String risposta = "sessa";

      //  boolean esito = quesito.get(1).checkRisposta(risposta);

     //   log.d("DEBUG","domanda  esito  Wwwwwwwwwwwwww= = "+ esito);
        log.d("DEBUG","size quesito rrrrrrrrrrrrrrrrrr= = "+sizeq);

        log.d("DEBUG","domanda  risposta corr  Wwwwwwwwwwwwww= = "+ quesito.get(i).getRispostaEsatta()+" indice "+i);
        log.d("DEBUG","domanda  risposta err Wwwwwwwwwwwwww= = "+ quesito.get(i).getRispostaErrata1()+" indice "+i);
        log.d("DEBUG","domanda  risposta err  Wwwwwwwwwwwwww= = "+ quesito.get(i).getRispostaErrata2()+" indice "+i);
        log.d("DEBUG","domanda  rispostae rr  Wwwwwwwwwwwwww= = "+ quesito.get(i).getRispostaErrata3()+" indice "+i);



        testoCategoriaDomanda.setText("Addizione");

        testoDomanda.setText(quesito.get(i).getDomanda());

        A.setText(quesito.get(i).getRispostaEsatta());
        B.setText(quesito.get(i).getRispostaErrata1());
        C.setText(quesito.get(i).getRispostaErrata2());
        D.setText(quesito.get(i).getRispostaErrata3());

        i = 1;








    }
    public void onClickRisposta (View v){
      //  boolean domandeTerminate=false;
        Log log = null;
        log.d("DEBUG","wwww333333333  Wwwwwwwwwwwwww= = "+i);

        //while(i<numDomande){
            testoDomanda.setText(quesito.get(i).getDomanda());
            if((A.isPressed()==true)||(B.isPressed()==true)||(C.isPressed()==true)||(D.isPressed()==true)){
                A.setText(quesito.get(i).getRispostaEsatta());
                B.setText(quesito.get(i).getRispostaErrata1());
                C.setText(quesito.get(i).getRispostaErrata2());
                D.setText(quesito.get(i).getRispostaErrata3());
                A.setText("ooo");
                log.d("DEBUG","domanda  risposta corr  Wwwwwwwwwwwwww= = "+ quesito.get(0).getRispostaEsatta()+" indice "+i);
                log.d("DEBUG","domanda  risposta err Wwwwwwwwwwwwww= = "+ quesito.get(0).getRispostaErrata1()+" indice "+i);
                log.d("DEBUG","domanda  risposta err  Wwwwwwwwwwwwww= = "+ quesito.get(0).getRispostaErrata2()+" indice "+i);
                log.d("DEBUG","domanda  rispostae rr  Wwwwwwwwwwwwww= = "+ quesito.get(0).getRispostaErrata3()+" indice "+i);
               if(i<numeroDomande-1){
                   i++;
               }
            }

        // }



    }

}

