package com.example.threemath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView testoDomanda;
    TextView testoCategoriaDomanda;
    Button A, B, C, D;
    ArrayList<Domanda> quesito = new ArrayList<>();
    int i = 0;
    int sizeq = 0;
    /**
     * String domanda = " ";
     * String rispostaCorretta = " ";
     * String rispostaErrata1 = " ";
     * String rispostaErrata2 = " ";
     * String rispostaErrata3 = " ";
     **/

    ArrayList<Boolean> risposteQuesito = new ArrayList<>();
    Random generatore = new Random();
    int numRand = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** categoria domanda ( addizione sottrazione ...)**/
        testoCategoriaDomanda = (TextView) findViewById(R.id.categoriaDomanda);

        /** testo domanda ( 50+4 ....)*/
        testoDomanda = (TextView) findViewById(R.id.corpoDomandaTesto);

        /**bottoni 50-45-54-60 ....)*/
        A = (Button) findViewById(R.id.A);
        B = (Button) findViewById(R.id.B);
        C = (Button) findViewById(R.id.C);
        D = (Button) findViewById(R.id.D);


        Domanda uno = new Domanda("Come ti chiami ", "andrea", "giacomo", "mario", "amedeo");
        quesito.add(0, uno);
        Domanda due = new Domanda("Il tuo cognome  ", "sessa", "landi", "de paoli", "brighi");
        quesito.add(1, due);
        Domanda tre = new Domanda("Il cognome di mamma  ", "sessa", "landi", "rossi", "brighi");
        quesito.add(2, tre);


        //String x = domanda1.getDomanda();


        sizeq = quesito.size();

        // String y = quesito.get(0).getDomanda();
        // String j = quesito.get(0).getRispostaEsatta();

        Log log = null;
        //   log.d("DEBUG","domanda in arrivo = "+ y);
        //  log.d("DEBUG","domanda in arrivo-risposta esatta= "+ j);

        //   String risposta = "sessa";

        //  boolean esito = quesito.get(1).checkRisposta(risposta);

        //   log.d("DEBUG","domanda  esito  Wwwwwwwwwwwwww= = "+ esito);
        //  log.d("DEBUG", "size quesito rrrrrrrrrrrrrrrrrr= = " + sizeq);
       /* domanda = quesito.get(i).getDomanda();
        rispostaCorretta = quesito.get(i).getRispostaEsatta();
        rispostaErrata1 = quesito.get(i).getRispostaErrata1();
        rispostaErrata2 = quesito.get(i).getRispostaErrata2();
        rispostaErrata3 = quesito.get(i).getRispostaErrata3();
        log.d("DEBUG", "domanda    Wwwwwwwwwwwwww= = " + domanda + " indice " + i);

        log.d("DEBUG", "domanda  risposta corr  Wwwwwwwwwwwwww= = " + rispostaCorretta + " indice " + i);
        log.d("DEBUG", "domanda  risposta err Wwwwwwwwwwwwww= = " + rispostaErrata1 + " indice " + i);
        log.d("DEBUG", "domanda  risposta err  Wwwwwwwwwwwwww= = " + rispostaErrata2 + " indice " + i);
        log.d("DEBUG", "domanda  rispostae rr  Wwwwwwwwwwwwww= = " + rispostaErrata3 + " indice " + i);

        */


        testoCategoriaDomanda.setText("Addizione");

        testoDomanda.setText(quesito.get(i).getDomanda());

        A.setText(quesito.get(i).getRispostaEsatta());
        B.setText(quesito.get(i).getRispostaErrata1());
        C.setText(quesito.get(i).getRispostaErrata2());
        D.setText(quesito.get(i).getRispostaErrata3());

        //  i = 1;

        // if (i < sizeq) {
        //    i++;
        //  }


    }

    public void onClickRisposta(View v) {
        //  boolean domandeTerminate=false;
        Log log = null;
        //  log.d("DEBUG", "wwww333333333  Wwwwwwwwwwwwww= = " + i);
        //log.d("DEBUG", "wwww333333333qw  Wwwwwwwwwwwwww= = " + sizeq);

        //while(i<numDomande){

        /** CHECK RISPOSTA E SALVATAGGIO CHECK **/
        if (i < sizeq) {

            if (A.isPressed()) {
                risposteQuesito.add(quesito.get(i).checkRisposta(A.getText().toString()));
            } else if (B.isPressed()) {
                risposteQuesito.add(quesito.get(i).checkRisposta(B.getText().toString()));
            } else if (C.isPressed()) {
                risposteQuesito.add(quesito.get(i).checkRisposta(C.getText().toString()));
            } else if (D.isPressed()) {
                risposteQuesito.add(quesito.get(i).checkRisposta(D.getText().toString()));
            }
            log.d("DEBUG", "wwww333333333  Wwwwwwwwwwwwww= presssedddd = " + risposteQuesito.get(i));
        }

        numRand = 1 + generatore.nextInt(4);
        //numRand = (int) (Math.random()*4);

        if (i < sizeq - 1) {
            i++;
            testoDomanda.setText(quesito.get(i).getDomanda());
            if ((A.isPressed()) || (B.isPressed()) || (C.isPressed()) || (D.isPressed())) {

                switch (numRand) {
                    case (1):
                        A.setText(quesito.get(i).getRispostaEsatta());
                        B.setText(quesito.get(i).getRispostaErrata1());
                        C.setText(quesito.get(i).getRispostaErrata2());
                        D.setText(quesito.get(i).getRispostaErrata3());
                    case (2):
                        B.setText(quesito.get(i).getRispostaEsatta());
                        C.setText(quesito.get(i).getRispostaErrata1());
                        D.setText(quesito.get(i).getRispostaErrata2());
                        A.setText(quesito.get(i).getRispostaErrata3());

                    case (3):
                        C.setText(quesito.get(i).getRispostaEsatta());
                        D.setText(quesito.get(i).getRispostaErrata1());
                        A.setText(quesito.get(i).getRispostaErrata2());
                        B.setText(quesito.get(i).getRispostaErrata3());
                    case (4):
                        D.setText(quesito.get(i).getRispostaEsatta());
                        A.setText(quesito.get(i).getRispostaErrata1());
                        B.setText(quesito.get(i).getRispostaErrata2());
                        C.setText(quesito.get(i).getRispostaErrata3());
                }


            }
        }
    }


}

