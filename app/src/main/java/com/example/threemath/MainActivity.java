package com.example.threemath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    TextView testoDomanda;
    TextView testoCategoriaDomanda;
    Button A, B, C, D,bottoneRisultati;
    LinearLayout llcategoriaDomanda;
    LinearLayout llDomanda;
    LinearLayout llCorpoDomanda;
    LinearLayout llRisposte;
    LinearLayout llRisultati ;


    int deviceWidth =0;
    int deviceHeight = 0;


    ArrayList<Domanda> quesito = new ArrayList<>();
    int indiceDomanda = 0;
    int sizeq = 0;
    int numRispEsatte=0;
    int numRispErrate=0;

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

        /** dimensioni schermo telefono **/
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        deviceWidth = metrics.heightPixels;
        deviceHeight = metrics.widthPixels;

        Log log = null;
        log.d("DEBUG","DIMENSIONI SCHERMO = "+ deviceHeight);
        log.d("DEBUG","DIMENSIONI SCHERMO = "+ deviceWidth);

        setContentView(R.layout.activity_main);





        llcategoriaDomanda = ( LinearLayout)  findViewById(R.id.llcategoriaDomanda);
        //llcategoriaDomanda.setX((deviceHeight*15)/100);
        //llcategoriaDomanda.x


        llCorpoDomanda = ( LinearLayout)  findViewById(R.id.corpoDomanda);



        llRisposte = ( LinearLayout) findViewById(R.id.risposte);














        /** categoria domanda ( addizione sottrazione ...)**/
        testoCategoriaDomanda = (TextView) findViewById(R.id.categoriaDomanda);

        /** testo domanda ( 50+4 ....)*/
        testoDomanda = (TextView) findViewById(R.id.corpoDomandaTesto);

        /**bottoni 50-45-54-60 ....)*/
        A = (Button) findViewById(R.id.A);
        B = (Button) findViewById(R.id.B);
        C = (Button) findViewById(R.id.C);
        D = (Button) findViewById(R.id.D);

        /**bottone risultati non visibile)*/
        bottoneRisultati = (Button) findViewById(R.id.bottoneRisultati);

        llRisultati = (LinearLayout) findViewById(R.id.llbottoneRisultati);




        Domanda uno = new Domanda("Come ti chiami ", "andrea", "giacomo", "mario", "amedeo");
        quesito.add(0, uno);
        Domanda due = new Domanda("Il tuo cognome  ", "sessa", "landi", "de paoli", "brighi");
        quesito.add(1, due);
        Domanda tre = new Domanda("Il cognome di mamma  ", "sessa", "landi", "rossi", "brighi");
        quesito.add(2, tre);

        insertDomande();









        //String x = domanda1.getDomanda();


        sizeq = quesito.size();

        // String y = quesito.get(0).getDomanda();
        // String j = quesito.get(0).getRispostaEsatta();




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
        log.d("DEBUG", "dimensioni altezza =  " + deviceHeight + " dimensioni base" + deviceWidth);



        testoCategoriaDomanda.setText("Addizione");

        testoDomanda.setText(quesito.get(indiceDomanda).getDomanda());

        A.setText(quesito.get(indiceDomanda).getRispostaEsatta());
        B.setText(quesito.get(indiceDomanda).getRispostaErrata1());
        C.setText(quesito.get(indiceDomanda).getRispostaErrata2());
        D.setText(quesito.get(indiceDomanda).getRispostaErrata3());

        //  i = 1;

        // if (i < sizeq) {
        //    i++;
        //  }

        /** Aggiunta bottone vai a risultati
        LinearLayout llBottoneRisultati = (LinearLayout) findViewById(R.id.llVaiARisultati);

        ImageView viewBottone = new ImageView(getApplicationContext());
        Button vaiARisultati = new Button(viewBottone.getContext());
        vaiARisultati.setText("risultati");
        viewBottone.set**/







    }

    public void onClickRisposta(View v) {
        //  boolean domandeTerminate=false;
        Log log = null;
        //  log.d("DEBUG", "wwww333333333  Wwwwwwwwwwwwww= = " + i);
        //log.d("DEBUG", "wwww333333333qw  Wwwwwwwwwwwwww= = " + sizeq);

        //while(i<numDomande){

        /** CHECK RISPOSTA E SALVATAGGIO CHECK **/
        if (indiceDomanda < sizeq) {

            if (A.isPressed()) {
                risposteQuesito.add(quesito.get(indiceDomanda).checkRisposta(A.getText().toString()));
            } else if (B.isPressed()) {
                risposteQuesito.add(quesito.get(indiceDomanda).checkRisposta(B.getText().toString()));
            } else if (C.isPressed()) {
                risposteQuesito.add(quesito.get(indiceDomanda).checkRisposta(C.getText().toString()));
            } else if (D.isPressed()) {
                risposteQuesito.add(quesito.get(indiceDomanda).checkRisposta(D.getText().toString()));
            }

            log.d("DEBUG", "wwww333333333  Wwwwwwwwwwwwww= presssedddd = " + risposteQuesito.get(indiceDomanda));

            if(indiceDomanda==sizeq-1){ /** ultima domanda **/
            indiceDomanda++;
                for(int i = 0 ; i < risposteQuesito.size() ; i++){
                   // log.d("DEBUG", "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqq = " );

                    if(risposteQuesito.get(i)){
                        numRispEsatte++;
                    }else{
                        numRispErrate++;
                    }
                }

                log.d("DEBUG", "corrette = " +numRispEsatte);
                log.d("DEBUG", "errate= " + numRispErrate);


                /**visibile il bottone risultati e disbilitazione dei pulsanti delle risposte**/
                bottoneRisultati.setVisibility(View.VISIBLE);
                llRisultati.setVisibility(View.VISIBLE);
                A.setClickable(false);
                B.setClickable(false);
                C.setClickable(false);
                D.setClickable(false);
            }


        }


        numRand = 1 + generatore.nextInt(4);
        //numRand = (int) (Math.random()*4);

        if (indiceDomanda < sizeq - 1) {
            indiceDomanda++;
            testoDomanda.setText(quesito.get(indiceDomanda).getDomanda());
            if ((A.isPressed()) || (B.isPressed()) || (C.isPressed()) || (D.isPressed())) {

                switch (numRand) {
                    case (1):
                        A.setText(quesito.get(indiceDomanda).getRispostaEsatta());
                        B.setText(quesito.get(indiceDomanda).getRispostaErrata1());
                        C.setText(quesito.get(indiceDomanda).getRispostaErrata2());
                        D.setText(quesito.get(indiceDomanda).getRispostaErrata3());
                    case (2):
                        B.setText(quesito.get(indiceDomanda).getRispostaEsatta());
                        C.setText(quesito.get(indiceDomanda).getRispostaErrata1());
                        D.setText(quesito.get(indiceDomanda).getRispostaErrata2());
                        A.setText(quesito.get(indiceDomanda).getRispostaErrata3());

                    case (3):
                        C.setText(quesito.get(indiceDomanda).getRispostaEsatta());
                        D.setText(quesito.get(indiceDomanda).getRispostaErrata1());
                        A.setText(quesito.get(indiceDomanda).getRispostaErrata2());
                        B.setText(quesito.get(indiceDomanda).getRispostaErrata3());
                    case (4):
                        D.setText(quesito.get(indiceDomanda).getRispostaEsatta());
                        A.setText(quesito.get(indiceDomanda).getRispostaErrata1());
                        B.setText(quesito.get(indiceDomanda).getRispostaErrata2());
                        C.setText(quesito.get(indiceDomanda).getRispostaErrata3());
                }


            }
        }
    }
    /** Metodo void che al cliccare del bottone risultati chiama un activity che gestisce i risultati***/
     public void onClickRisultati(View v){

         /*Media player*/
        // MediaPlayer mp = new MediaPlayer();
         // mp = MediaPlayer.create(this, getResources().getIdentifier("cassa", "raw", getPackageName()));
         //mp = MediaPlayer.create(this, R.raw.cassa);
        // mp.start();


         /*new intent*/
         Intent intent = new Intent();
         intent.setClass(getApplicationContext(), RisultatiActivity.class);
         intent.putExtra("CORRETTE",numRispEsatte );
         intent.putExtra("ERRATE",numRispErrate );




         startActivityForResult(intent, 0);
     }

     public void onClickHome(View v){
         /*new intent*/
         Intent intent = new Intent();
         intent.setClass(getApplicationContext(), HomeActivity.class);
         startActivityForResult(intent, 0);

     }

    /***
     * Inserisce le domande in un array list
     */
     public void insertDomande(){

         Domanda quattro = new Domanda("Come si chiama mago ", "pasquale", "giacomo", "mario", "amedeo");
         quesito.add( quattro);
         Domanda cinque = new Domanda("Il suo cognome  ", "prisco", "landi", "de paoli", "brighi");
         quesito.add( cinque);
         Domanda sei = new Domanda("Il cognome di nonna  ", "non definito", "landi", "rossi", "brighi");
         quesito.add( sei);

     }



}

