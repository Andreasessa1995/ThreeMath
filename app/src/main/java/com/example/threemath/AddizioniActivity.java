package com.example.threemath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class AddizioniActivity extends AppCompatActivity {

    /**
     * elementi della view
     **/
    TextView testoDomanda;
    TextView testoCategoriaDomanda;
    TextView categoriaQuiz;
    TextView numeroDomandaCorrente;
    Button A, B, C, D, bottoneRisultati;
    LinearLayout llRisultati;
    LinearLayout lltimezone;


    int deviceWidth = 0;
    int deviceHeight = 0;

    /**
     * elementi addizione
     */
    ArrayList<Domanda> quesito = new ArrayList<>();
    int indiceDomanda = 0;
    int sizeq = 0;
    int numRispEsatte = 0;
    int numRispErrate = 0;
    boolean ultimaDomanda = false;
    int livello = 1;
    int numRandScoreAddizioni = 0;
    String categoria = "Addizioni";

    /**
     * elementi suoni
     */
    MediaPlayer mpCampanella;
    MediaPlayer mpBat;
    MediaPlayer mpTic;


    /**
     * gestione vibrazione
     */
    Vibrator vibrator;
    /*vib 100 millise riposo 1000 millsec, vir*/
    long[] pattern = {0, 100, 1000};


    /**
     * random
     */
    int numRand = 0;


    /**
     * TIME
     **/

    long START_TIME_IN_MILLIS = 25000;
    long tempoQuesito = START_TIME_IN_MILLIS;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;/*indica se sta scendendo il tempo*/
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    TextView timeText;
    int minutes = 0;
    int seconds = 0;

    boolean countDownIniziato = false;
    boolean resumeStartCountDown = false;
    boolean stopCountDownPressedHome = false;



    /**
     * IMPOSTAZIONI
     */
    GestoreFile gf = new GestoreFile();
    Boolean suoni = true;
    Boolean vibrazione = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz);


        /** dimensioni schermo telefono **/
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        deviceWidth = metrics.heightPixels;
        deviceHeight = metrics.widthPixels;

        Log log = null;
        //log.d("DEBUG", "DIMENSIONI SCHERMO = " + deviceHeight);
        //log.d("DEBUG", "DIMENSIONI SCHERMO = " + deviceWidth);

        /**time zone */
        timeText = (TextView) findViewById(R.id.timeText);
        lltimezone = (LinearLayout) findViewById(R.id.timezone);


        //llcategoriaDomanda = ( LinearLayout)  findViewById(R.id.llcategoriaDomanda);
        //llcategoriaDomanda.setX((deviceHeight*15)/100);
        //llcategoriaDomanda.x
        // llCorpoDomanda = ( LinearLayout)  findViewById(R.id.corpoDomanda);
        // llRisposte = ( LinearLayout) findViewById(R.id.risposte);


        /** categoria domanda ( addizione sottrazione ...)**/
        testoCategoriaDomanda = (TextView) findViewById(R.id.categoriaDomanda);

        /** categoria scelta quiz tier + - ...*/
        categoriaQuiz = (TextView) findViewById(R.id.testoCategoria);

        /** testo domanda ( 50+4 ....)*/
        testoDomanda = (TextView) findViewById(R.id.corpoDomandaTesto);

        /**numero progressivo della domanda*/
        numeroDomandaCorrente = (TextView) findViewById(R.id.categoriaNumeroDomanda);

        /**bottoni 50-45-54-60 ....)*/
        A = (Button) findViewById(R.id.A);
        B = (Button) findViewById(R.id.B);
        C = (Button) findViewById(R.id.C);
        D = (Button) findViewById(R.id.D);

        /**bottone risultati non visibile)*/
        bottoneRisultati = (Button) findViewById(R.id.bottoneRisultati);
        llRisultati = (LinearLayout) findViewById(R.id.llbottoneRisultati);



        /*------------------GESTIONE DOMANDE- valori activuty precedente-----------------*/

        Intent intent = getIntent();
        intent = getIntent();
        livello = intent.getIntExtra("LIVELLO", livello);
        numRandScoreAddizioni = intent.getIntExtra("SCORE", numRandScoreAddizioni);


        //log.d("DEBUG", "LIVELLO LIVELLO LIVELLO LIVELLO = " + livello);

        /*setta il valore del countdown in base al livello scelto*/
        setCountDownLevel();
        /*sceglie le domande da inserire **/
        choseDomande();

        checkImpostazioni();


        sizeq = quesito.size();

        numeroDomandaCorrente.setText("" + (indiceDomanda + 1) + "/" + sizeq);


        //log.d("DEBUG", "dimensioni altezza =  " + deviceHeight + " dimensioni base" + deviceWidth);

        /*SETTAGGI TEXT VIEW BOTTONI*/
        testoCategoriaDomanda.setText("ADDIZIONI");
        categoriaQuiz.setText("+");
        /*bottoni*/
        testoDomanda.setText(quesito.get(indiceDomanda).getDomanda());
        A.setText(quesito.get(indiceDomanda).getRispostaEsatta());
        B.setText(quesito.get(indiceDomanda).getRispostaErrata1());
        C.setText(quesito.get(indiceDomanda).getRispostaErrata2());
        D.setText(quesito.get(indiceDomanda).getRispostaErrata3());

        /*TIME TIME TIME */
        startTimer();


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

    /**
     * gestisce le risposte, cambiando ad ogni risposta la domanda e le relative risposte. Nell'ultima
     * risposta fa comparire un bottone con la scritta risultati.
     *
     * @param v
     */
    public void onClickRisposta(View v) {
        //  boolean domandeTerminate=false;
        Log log = null;
        //  log.d("DEBUG", "wwww333333333  Wwwwwwwwwwwwww= = " + i);
        //log.d("DEBUG", "wwww333333333qw  Wwwwwwwwwwwwww= = " + sizeq);

        //while(i<numDomande){
        boolean risposta = false;
        /** CHECK RISPOSTA E SALVATAGGIO CHECK **/
        if (indiceDomanda < sizeq) {

            if (A.isPressed()) {
                risposta = quesito.get(indiceDomanda).checkRisposta(A.getText().toString());
            } else if (B.isPressed()) {
                risposta = quesito.get(indiceDomanda).checkRisposta(B.getText().toString());

            } else if (C.isPressed()) {
                risposta = quesito.get(indiceDomanda).checkRisposta(C.getText().toString());

            } else if (D.isPressed()) {
                risposta = quesito.get(indiceDomanda).checkRisposta(D.getText().toString());

            }
            /*check risposta ogni volta che rispondo */
            checkRisposteV2(risposta);


            // log.d("DEBUG", "wwww333333333  Wwwwwwwwwwwwww= presssedddd = " + risposteQuesito.get(indiceDomanda));
            /** ultima domanda **/
            if (indiceDomanda == sizeq - 1) {

                /*check suoni e vibrazione*/
                if (vibrazione) {
                    if (isHaveVibrate()) {
                        vibrator.vibrate(pattern, -1); // does not repeat
                        //vibrator.vibrate(pattern,  0); // repeats forever
                    }
                }

                startCampanella();



                if (vibrazione) {
                    /*vibrazione*/
                    if (isHaveVibrate()) {
                        vibrator.vibrate(pattern, -1); // does not repeat
                        //vibrator.vibrate(pattern,  0); // repeats forever
                    }
                }


                ultimaDomanda = true;
                indiceDomanda++;

                //log.d("DEBUG", "corrette = " +numRispEsatte);
                //log.d("DEBUG", "errate= " + numRispErrate);


                /**visibile il bottone risultati e disbilitazione dei pulsanti delle risposte**/
                disabilitaRisposte();
            }


        }


        //numRand = (int) (Math.random()*4);

        if (indiceDomanda < sizeq - 1) {

            indiceDomanda++;

            /*setta il testo ovvero il numero della domanda corrente*/
            numeroDomandaCorrente.setText("" + (indiceDomanda + 1) + "/" + sizeq);

            testoDomanda.setText(quesito.get(indiceDomanda).getDomanda());
            if ((A.isPressed()) || (B.isPressed()) || (C.isPressed()) || (D.isPressed())) {


                /*vibrazione*/

                startBattuta();

                if (vibrazione) {
                    if (isHaveVibrate()) {
                        vibrator.vibrate(pattern, -1); // does not repeat
                        //vibrator.vibrate(pattern,  0); // repeats forever
                    }
                }


                /**disposizione randomica risposte sui vari bottoni varie scelte per aumentare la casualità**/
                // numRand = 1 + (int) (Math.random() * 3);

                //numRand = numRandScoreAddizioni;

                log.d("DEBUG", "Numero random dello score modulo 3 = " + numRandScoreAddizioni);


                /*-----num random dello score cioè se lo score mod 3da come resto 0-1-2   */

                if (numRandScoreAddizioni == 0) {

                    numRand = 1 + (int) (Math.random() * 4);
                    /*-------1-----------*/

                    switch (numRand) {
                        case (1):
                            A.setText(quesito.get(indiceDomanda).getRispostaEsatta());
                            B.setText(quesito.get(indiceDomanda).getRispostaErrata1());
                            C.setText(quesito.get(indiceDomanda).getRispostaErrata2());
                            D.setText(quesito.get(indiceDomanda).getRispostaErrata3());
                            break;
                        case (2):
                            B.setText(quesito.get(indiceDomanda).getRispostaEsatta());
                            C.setText(quesito.get(indiceDomanda).getRispostaErrata1());
                            D.setText(quesito.get(indiceDomanda).getRispostaErrata2());
                            A.setText(quesito.get(indiceDomanda).getRispostaErrata3());
                            break;
                        case (3):
                            C.setText(quesito.get(indiceDomanda).getRispostaEsatta());
                            D.setText(quesito.get(indiceDomanda).getRispostaErrata1());
                            A.setText(quesito.get(indiceDomanda).getRispostaErrata2());
                            B.setText(quesito.get(indiceDomanda).getRispostaErrata3());
                            break;
                        case (4):
                            D.setText(quesito.get(indiceDomanda).getRispostaEsatta());
                            A.setText(quesito.get(indiceDomanda).getRispostaErrata1());
                            B.setText(quesito.get(indiceDomanda).getRispostaErrata2());
                            C.setText(quesito.get(indiceDomanda).getRispostaErrata3());
                            break;
                    }

                } else if (numRandScoreAddizioni == 1) {
                    /*-------2-----------*/

                    numRand = 1 + (int) (Math.random() * 4);
                    switch (numRand) {
                        case (1):
                            A.setText(quesito.get(indiceDomanda).getRispostaEsatta());
                            B.setText(quesito.get(indiceDomanda).getRispostaErrata1());
                            C.setText(quesito.get(indiceDomanda).getRispostaErrata2());
                            D.setText(quesito.get(indiceDomanda).getRispostaErrata3());
                            break;
                        case (2):
                            B.setText(quesito.get(indiceDomanda).getRispostaEsatta());
                            C.setText(quesito.get(indiceDomanda).getRispostaErrata1());
                            D.setText(quesito.get(indiceDomanda).getRispostaErrata2());
                            A.setText(quesito.get(indiceDomanda).getRispostaErrata3());
                            break;
                        case (3):
                            C.setText(quesito.get(indiceDomanda).getRispostaEsatta());
                            D.setText(quesito.get(indiceDomanda).getRispostaErrata1());
                            A.setText(quesito.get(indiceDomanda).getRispostaErrata2());
                            B.setText(quesito.get(indiceDomanda).getRispostaErrata3());
                            break;
                        case (4):
                            D.setText(quesito.get(indiceDomanda).getRispostaEsatta());
                            A.setText(quesito.get(indiceDomanda).getRispostaErrata1());
                            B.setText(quesito.get(indiceDomanda).getRispostaErrata2());
                            C.setText(quesito.get(indiceDomanda).getRispostaErrata3());
                            break;
                    }
                } else if (numRandScoreAddizioni == 2) {          /*-----num random dello score cioè se lo score mod 3da come resto 0-1-2   */
                    /*-------3-----------*/
                    numRand = 1 + (int) (Math.random() * 4);
                    switch (numRand) {
                        case (4):
                            A.setText(quesito.get(indiceDomanda).getRispostaEsatta());
                            B.setText(quesito.get(indiceDomanda).getRispostaErrata1());
                            C.setText(quesito.get(indiceDomanda).getRispostaErrata2());
                            D.setText(quesito.get(indiceDomanda).getRispostaErrata3());
                            break;
                        case (3):
                            B.setText(quesito.get(indiceDomanda).getRispostaEsatta());
                            C.setText(quesito.get(indiceDomanda).getRispostaErrata1());
                            D.setText(quesito.get(indiceDomanda).getRispostaErrata2());
                            A.setText(quesito.get(indiceDomanda).getRispostaErrata3());
                            break;
                        case (2):
                            C.setText(quesito.get(indiceDomanda).getRispostaEsatta());
                            D.setText(quesito.get(indiceDomanda).getRispostaErrata1());
                            A.setText(quesito.get(indiceDomanda).getRispostaErrata2());
                            B.setText(quesito.get(indiceDomanda).getRispostaErrata3());
                            break;
                        case (1):
                            D.setText(quesito.get(indiceDomanda).getRispostaEsatta());
                            A.setText(quesito.get(indiceDomanda).getRispostaErrata1());
                            B.setText(quesito.get(indiceDomanda).getRispostaErrata2());
                            C.setText(quesito.get(indiceDomanda).getRispostaErrata3());
                            break;
                    }
                }
            }
        }
    }
    /*------------------------gestione risultati e domande ------------------*/

    /**
     * apre l'activity dei risultati ottenuti
     *
     * @param v
     */
    public void onClickRisultati(View v) {




        mTimerRunning = false;
        mCountDownTimer.cancel();

        startBattuta();

        if (vibrazione) {
            /*vibrazione*/
            if (isHaveVibrate()) {
                vibrator.vibrate(pattern, -1); // does not repeat
                //vibrator.vibrate(pattern,  0); // repeats forever
            }
        }


        bottoneRisultati.setClickable(false);


        /*new intent*/

        Intent intent = getIntent();
        //Intent intent =getIntent();
        intent.setClass(getApplicationContext(), RisultatiActivity.class);
        intent.putExtra("CORRETTE", numRispEsatte);
        intent.putExtra("ERRATE", numRispErrate);
        intent.putExtra("TIMEQUESITO", tempoQuesito);
        intent.putExtra("TIMERESTANTEQUESITO", mTimeLeftInMillis);
        intent.putExtra("CATEGORIA", categoria);
        intent.putExtra("LIVELLO", livello);
        startActivityForResult(intent, 0);


        releaseResourcesCampanella();
        releaseResourcesTic();
        onBackPressed();


    }


    /**
     * controlla la risposta data
     *
     * @param risposta
     */
    public void checkRisposteV2(boolean risposta) {

        if (risposta) {
            numRispEsatte++;
        } else {
            numRispErrate++;
        }

    }

    /**
     * * Ogni categoria ha un livello LV1_ un numero progressivo _1 e un identificativo che indica
     *      * se è mischiata o meno_2
     * inserisce le domande della categoria scelta ogni verione ha qualcosa di differente
     * quelle con _2_2 significa che sono sempre le stesse domande ma ruotate per aumentare ancora di
     * più la casualità...
     */
    public void insertDomandeLV1_1() {
        quesito.clear();
        Domanda domanda1 = new Domanda("1 + 3", "4", "2", "3", "5");
        quesito.add(domanda1);
        Domanda domanda2 = new Domanda("5 + 4", "9", "8", "10", "6");
        quesito.add(domanda2);
        Domanda domanda3 = new Domanda("10 + 9", "19", "18", "29", "15");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("12 + 13", "25", "35", "26", "32");
        quesito.add(domanda4);
        Domanda domanda5 = new Domanda("18 + 22", "40", "30", "28", "50");
        quesito.add(domanda5);

    }

    /**
     * inserisce le domande della categoria scelta ogni verione ha qualcosa di differente
     * quelle con _2_2 significa che sono sempre le stesse domande ma ruotate per aumentare ancora di
     * più la casualità...
     */
    public void insertDomandeLV1_1_2() {
        quesito.clear();
        Domanda domanda5 = new Domanda("18 + 22", "40", "30", "28", "50");
        quesito.add(domanda5);
        Domanda domanda3 = new Domanda("10 + 9", "19", "18", "29", "15");
        quesito.add(domanda3);
        Domanda domanda1 = new Domanda("1 + 3", "4", "2", "3", "5");
        quesito.add(domanda1);
        Domanda domanda4 = new Domanda("12 + 13", "25", "35", "26", "32");
        quesito.add(domanda4);
        Domanda domanda2 = new Domanda("5 + 4", "9", "8", "10", "6");
        quesito.add(domanda2);


    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV1_2_1() {

        quesito.clear();
        Domanda domanda1 = new Domanda("25 + 12", "37", "27", "47", "35");
        quesito.add(domanda1);
        Domanda domanda2 = new Domanda("37 + 15", "52", "54", "42", "56");
        quesito.add(domanda2);
        Domanda domanda3 = new Domanda("42 + 19", "61", "72", "51", "63");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("38 + 19", "57", "58", "56", "68");
        quesito.add(domanda4);
        Domanda domanda5 = new Domanda("64 + 17", "81", "72", "83", "85");
        quesito.add(domanda5);


    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV1_2_2() {

        quesito.clear();
        Domanda domanda5 = new Domanda("64 + 17", "81", "72", "83", "85");
        quesito.add(domanda5);
        Domanda domanda3 = new Domanda("42 + 19", "61", "72", "51", "63");
        quesito.add(domanda3);
        Domanda domanda2 = new Domanda("37 + 15", "52", "54", "42", "56");
        quesito.add(domanda2);
        Domanda domanda4 = new Domanda("38 + 19", "57", "58", "56", "68");
        quesito.add(domanda4);
        Domanda domanda1 = new Domanda("25 + 12", "37", "27", "47", "35");
        quesito.add(domanda1);


    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV1_3() {

        quesito.clear();
        Domanda domanda1 = new Domanda("13 + 19", "22", "32", "21", "23");
        quesito.add(domanda1);
        Domanda domanda5 = new Domanda("64 + 17", "81", "72", "83", "85");
        quesito.add(domanda5);
        Domanda domanda2 = new Domanda("37 + 15", "52", "54", "42", "56");
        quesito.add(domanda2);
        Domanda domanda3 = new Domanda("38 + 19", "57", "58", "56", "68");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("42 + 19", "61", "72", "51", "63");
        quesito.add(domanda4);


    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV1_4() {

        quesito.clear();
        Domanda domanda1 = new Domanda("72 + 29", "101", "102", "103", "99");
        quesito.add(domanda1);
        Domanda domanda5 = new Domanda("58 + 17", "75", "76", "85", "66");
        quesito.add(domanda5);
        Domanda domanda2 = new Domanda("18 + 46", "64", "54", "65", "75");
        quesito.add(domanda2);
        Domanda domanda3 = new Domanda("25 + 36", "61", "51", "51", "62");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("49 + 14", "63", "62", "61", "53");
        quesito.add(domanda4);

    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV1_4_2() {

        quesito.clear();
        Domanda domanda4 = new Domanda("49 + 14", "63", "62", "61", "53");
        quesito.add(domanda4);
        Domanda domanda3 = new Domanda("25 + 36", "61", "51", "51", "62");
        quesito.add(domanda3);
        Domanda domanda1 = new Domanda("72 + 29", "101", "102", "103", "99");
        quesito.add(domanda1);
        Domanda domanda5 = new Domanda("58 + 17", "75", "76", "85", "66");
        quesito.add(domanda5);
        Domanda domanda2 = new Domanda("18 + 46", "64", "54", "65", "75");
        quesito.add(domanda2);


    }





    /*-------------------------------------------LV2--normale--------------------------------------------------------------------------------------------------*/

    /**
     * inserisce le domande della categoria scelta, versione 1 (v1) LV2
     */
    public void insertDomandeLV2_1() {
        quesito.clear();

        Domanda domanda1 = new Domanda("101 + 103", "204", "104", "203", "103");
        quesito.add(domanda1);
        Domanda domanda2 = new Domanda("105 + 104", "209", "109", "108", "205");
        quesito.add(domanda2);

        Domanda domanda3 = new Domanda("100 + 29", "129", "128", "229", "119");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("112 + 113", "225", "235", "226", "232");
        quesito.add(domanda4);

        Domanda domanda5 = new Domanda("118 + 22", "140", "130", "128", "150");
        quesito.add(domanda5);
        Domanda domanda6 = new Domanda("125 + 122", "147", "137", "146", "150");
        quesito.add(domanda6);

        Domanda domanda7 = new Domanda("128 + 34", "162", "152", "163", "142");
        quesito.add(domanda7);

    }

    /**
     * inserisce le domande della categoria scelta questa è la versione normale, v1 , mischiata sempre della numero 1
     */
    public void insertDomandeLV2_1_2() {
        quesito.clear();
        Domanda domanda5 = new Domanda("118 + 22", "140", "130", "128", "150");
        quesito.add(domanda5);
        Domanda domanda1 = new Domanda("101 + 103", "204", "104", "203", "103");
        quesito.add(domanda1);
        Domanda domanda6 = new Domanda("125 + 122", "147", "137", "146", "150");
        quesito.add(domanda6);
        Domanda domanda4 = new Domanda("112 + 113", "225", "235", "226", "232");
        quesito.add(domanda4);
        Domanda domanda7 = new Domanda("128 + 34", "162", "152", "163", "142");
        quesito.add(domanda7);
        Domanda domanda3 = new Domanda("100 + 29", "129", "128", "229", "119");
        quesito.add(domanda3);
        Domanda domanda2 = new Domanda("105 + 104", "209", "109", "108", "205");
        quesito.add(domanda2);
    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV2_2() {

        quesito.clear();
        Domanda domanda1 = new Domanda("125 + 115", "240", "230", "235", "210");
        quesito.add(domanda1);
        Domanda domanda2 = new Domanda("137 + 107", "244", "234", "205", "200");
        quesito.add(domanda2);

        Domanda domanda3 = new Domanda("162 + 129", "291", "283", "281", "293");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("148 + 149", "297", "287", "286", "296");
        quesito.add(domanda4);

        Domanda domanda5 = new Domanda("102 + 49", "151", "141", "153", "143");
        quesito.add(domanda5);
        Domanda domanda6 = new Domanda("152 + 49", "201", "191", "202", "193");
        quesito.add(domanda6);

        Domanda domanda7 = new Domanda("137 + 109", "246", "236", "247", "245");
        quesito.add(domanda7);


    }

    /**
     * inserisce le domande della categoria scelta  versione mischiata della seconda scelta v2
     */
    public void insertDomandeLV2_2_2() {

        quesito.clear();
        Domanda domanda5 = new Domanda("102 + 49", "151", "141", "153", "143");
        quesito.add(domanda5);
        Domanda domanda1 = new Domanda("125 + 115", "240", "230", "235", "210");
        quesito.add(domanda1);
        Domanda domanda6 = new Domanda("152 + 49", "201", "191", "202", "193");
        quesito.add(domanda6);
        Domanda domanda4 = new Domanda("148 + 149", "297", "287", "286", "296");
        quesito.add(domanda4);
        Domanda domanda7 = new Domanda("137+ 109", "146", "136", "147", "145");
        quesito.add(domanda7);
        Domanda domanda3 = new Domanda("162 + 129", "291", "283", "281", "293");
        quesito.add(domanda3);
        Domanda domanda2 = new Domanda("137 + 107", "244", "234", "205", "200");
        quesito.add(domanda2);
    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV2_3() {

        quesito.clear();
        Domanda domanda1 = new Domanda("275 + 239", "514", "513", "516", "504");
        quesito.add(domanda1);
        Domanda domanda2 = new Domanda("235 + 215", "450", "440", "460", "460");
        quesito.add(domanda2);

        Domanda domanda3 = new Domanda("248 + 149", "397", "387", "386", "396");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("252 + 139", "391", "383", "381", "393");
        quesito.add(domanda4);

        Domanda domanda5 = new Domanda("157 + 227", "384", "374", "375", "375");
        quesito.add(domanda5);
        Domanda domanda6 = new Domanda("177 + 247", "424", "414", "404", "444");
        quesito.add(domanda6);

        Domanda domanda7 = new Domanda("288 + 215", "503", "502", "504", "493");
        quesito.add(domanda7);


    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV2_3_2() {

        quesito.clear();
        Domanda domanda5 = new Domanda("157 + 227", "384", "374", "375", "375");
        quesito.add(domanda5);
        Domanda domanda1 = new Domanda("275 + 239", "514", "513", "516", "504");
        quesito.add(domanda1);
        Domanda domanda6 = new Domanda("177 + 247", "424", "414", "404", "444");
        quesito.add(domanda6);
        Domanda domanda4 = new Domanda("252 + 139", "391", "383", "381", "393");
        quesito.add(domanda4);
        Domanda domanda2 = new Domanda("235 + 215", "450", "440", "460", "460");
        quesito.add(domanda2);
        Domanda domanda7 = new Domanda("288 + 215", "503", "502", "504", "493");
        quesito.add(domanda7);
        Domanda domanda3 = new Domanda("248 + 149", "397", "387", "386", "396");
        quesito.add(domanda3);


    }

    /*-------------------------------------------LV3--esperto--------------------------------------------------------------------------------------------------*/

    /**
     * inserisce le domande della categoria scelta, versione 1 (v1)
     */
    public void insertDomandeLV3_1() {
        quesito.clear();

        Domanda domanda1 = new Domanda("1001 + 1003", "2004", "1004", "2003", "1003");
        quesito.add(domanda1);
        Domanda domanda2 = new Domanda("1005 + 1004", "2009", "1009", "1008", "2005");
        quesito.add(domanda2);

        Domanda domanda3 = new Domanda("1000 + 29", "1029", "1028", "2029", "1019");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("1012 + 1013", "2025", "2035", "2026", "2032");
        quesito.add(domanda4);

        Domanda domanda5 = new Domanda("1018 + 22", "1040", "1030", "1028", "1050");
        quesito.add(domanda5);
        Domanda domanda6 = new Domanda("1205 + 1202", "1407", "1307", "1406", "1500");
        quesito.add(domanda6);

        Domanda domanda7 = new Domanda("1028 + 34", "1062", "1052", "1063", "1042");
        quesito.add(domanda7);

    }

    /**
     * inserisce le domande della categoria scelta, versione 1 (v1_2)
     */
    public void insertDomandeLV3_1_2() {
        quesito.clear();

        Domanda domanda5 = new Domanda("1018 + 22", "1040", "1030", "1028", "1050");
        quesito.add(domanda5);
        Domanda domanda7 = new Domanda("1028 + 34", "1062", "1052", "1063", "1042");
        quesito.add(domanda7);
        Domanda domanda1 = new Domanda("1001 + 1003", "2004", "1004", "2003", "1003");
        quesito.add(domanda1);
        Domanda domanda4 = new Domanda("1012 + 1013", "2025", "2035", "2026", "2032");
        quesito.add(domanda4);
        Domanda domanda2 = new Domanda("1005 + 1004", "2009", "1009", "1008", "2005");
        quesito.add(domanda2);
        Domanda domanda6 = new Domanda("1205 + 1202", "1407", "1307", "1406", "1500");
        quesito.add(domanda6);
        Domanda domanda3 = new Domanda("1000 + 29", "1029", "1028", "2029", "1019");
        quesito.add(domanda3);


    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV3_2() {

        quesito.clear();
        Domanda domanda1 = new Domanda("1025 + 1015", "2040", "2030", "2035", "2010");
        quesito.add(domanda1);
        Domanda domanda2 = new Domanda("1307 + 107", "1414", "2414", "1424", "1415");
        quesito.add(domanda2);

        Domanda domanda3 = new Domanda("1062 + 1029", "2091", "2083", "2081", "2093");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("1148 + 1149", "2297", "2287", "2286", "2296");
        quesito.add(domanda4);

        Domanda domanda5 = new Domanda("1102 + 1149", "2251", "2241", "2253", "2243");
        quesito.add(domanda5);
        Domanda domanda6 = new Domanda("1521 + 491", "2012", "2191", "2202", "2193");
        quesito.add(domanda6);

        Domanda domanda7 = new Domanda("1137+ 1109", "2246", "2236", "2247", "2245");
        quesito.add(domanda7);


    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV3_2_2() {

        quesito.clear();
        Domanda domanda5 = new Domanda("1102 + 1149", "2251", "2241", "2253", "2243");
        quesito.add(domanda5);
        Domanda domanda3 = new Domanda("1062 + 1029", "2091", "2083", "2081", "2093");
        quesito.add(domanda3);
        Domanda domanda1 = new Domanda("1025 + 1015", "2040", "2030", "2035", "2010");
        quesito.add(domanda1);
        Domanda domanda4 = new Domanda("1148 + 1149", "2297", "2287", "2286", "2296");
        quesito.add(domanda4);
        Domanda domanda7 = new Domanda("1137 + 1109", "2246", "2236", "2247", "2245");
        quesito.add(domanda7);
        Domanda domanda2 = new Domanda("1307 + 107", "1414", "2414", "1424", "1415");
        quesito.add(domanda2);
        Domanda domanda6 = new Domanda("1521 + 491", "2012", "2191", "2202", "2193");
        quesito.add(domanda6);


    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV3_3() {

        quesito.clear();
        Domanda domanda1 = new Domanda("2275 + 2239", "4514", "4513", "4516", "4504");
        quesito.add(domanda1);
        Domanda domanda2 = new Domanda("1235 + 3215", "4450", "4440", "4460", "4460");
        quesito.add(domanda2);

        Domanda domanda3 = new Domanda("3248 + 3149", "6397", "6387", "6386", "6396");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("1252 + 1139", "2391", "2383", "2381", "2393");
        quesito.add(domanda4);

        Domanda domanda5 = new Domanda("2157 + 2227", "4384", "4374", "4375", "4375");
        quesito.add(domanda5);
        Domanda domanda6 = new Domanda("1177 + 7247", "8424", "8414", "8404", "8444");
        quesito.add(domanda6);

        Domanda domanda7 = new Domanda("1288 + 4215", "5503", "5502", "5504", "5493");
        quesito.add(domanda7);


    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV3_3_2() {

        quesito.clear();
        Domanda domanda5 = new Domanda("2157 + 2227", "4384", "4374", "4375", "4375");
        quesito.add(domanda5);
        Domanda domanda4 = new Domanda("1252 + 1139", "2391", "2383", "2381", "2393");
        quesito.add(domanda4);
        Domanda domanda1 = new Domanda("2275 + 2239", "4514", "4513", "4516", "4504");
        quesito.add(domanda1);
        Domanda domanda6 = new Domanda("1177 + 7247", "8424", "8414", "8404", "8444");
        quesito.add(domanda6);
        Domanda domanda2 = new Domanda("1235 + 3215", "4450", "4440", "4460", "4460");
        quesito.add(domanda2);
        Domanda domanda7 = new Domanda("1288 + 4215", "5503", "5502", "5504", "5493");
        quesito.add(domanda7);
        Domanda domanda3 = new Domanda("3248 + 3149", "6397", "6387", "6386", "6396");
        quesito.add(domanda3);

    }










    /*-----------------------------SCELTA QUIZ ----------------------------------*/

    /**
     * sceglie randomicamente le domande da caricare
     */
    protected void choseDomande() {
        //GestoreFile gf = new GestoreFile();
        //int numPoint = gf.caricaScoresAddizioni(getApplicationContext());
        //numPoint = numPoint %2;

        //numRand = 1 + (int) (Math.random() * 3);

        Log log = null;
        //log.d("DEBUG", "Numero random con math= " + numRand);

        /*-----num random dello score cioè se lo score mod 3da come resto 0-1-2   */
        if (numRandScoreAddizioni == 0) {

            numRand = 1 + (int) (Math.random() * 7);

            if (livello == 1) { /*PRINCIPIANTE*/
                switch (numRand) {
                    case (1):
                        insertDomandeLV1_1();
                        break;
                    case (2):
                        insertDomandeLV1_2_1();
                        break;
                    case (3):
                        insertDomandeLV1_3();
                        break;
                    case (4):
                        insertDomandeLV1_4();
                        break;
                    case (5):
                        insertDomandeLV1_2_2();
                        break;
                    case (6):
                        insertDomandeLV1_1_2();
                        break;
                    case (7):
                        insertDomandeLV1_4_2();
                        break;

                }
            } else if (livello == 2) { /*NORMLE*/
                switch (numRand) {
                    case (1):
                        insertDomandeLV2_2();
                        break;

                    case (2):
                        insertDomandeLV2_1();
                        break;
                    case (3):
                        insertDomandeLV2_1_2();
                        break;
                    case (4):
                        insertDomandeLV2_2_2();
                        break;
                    case (5):
                        insertDomandeLV2_1();
                        break;
                    case (6):
                        insertDomandeLV2_3();
                        break;
                    case (7):
                        insertDomandeLV2_3_2();
                        break;
                }
            } else if (livello == 3) { /*ESPERTO*/
                switch (numRand) {
                    case (1):
                        insertDomandeLV3_2();
                        break;

                    case (2):
                        insertDomandeLV3_1();
                        break;
                    case (3):
                        insertDomandeLV3_1_2();
                        break;
                    case (4):
                        insertDomandeLV3_2_2();
                        break;
                    case (5):
                        insertDomandeLV3_1();
                        break;
                    case (6):
                        insertDomandeLV3_3();
                        break;
                    case (7):
                        insertDomandeLV3_3_2();
                        break;
                }
            }

        }
        /*-----num random dello score cioè se lo score mod 3da come resto 0-1-2   */
        else if (numRandScoreAddizioni == 1) {

            numRand = 1 + (int) (Math.random() * 7);

            if (livello == 1) { /*PRINCIPIANTE*/
                switch (numRand) {
                    case (1):
                        insertDomandeLV1_4();
                        break;
                    case (2):
                        insertDomandeLV1_2_2();
                        break;
                    case (3):
                        insertDomandeLV1_1_2();
                        break;
                    case (4):
                        insertDomandeLV1_4_2();
                        break;
                    case (5):
                        insertDomandeLV1_1();
                        break;
                    case (6):
                        insertDomandeLV1_2_1();
                        break;
                    case (7):
                        insertDomandeLV1_3();
                        break;

                }
            } else if (livello == 2) { /*NORMLE*/
                switch (numRand) {
                    case (1):
                        insertDomandeLV2_2_2();
                        break;
                    case (2):
                        insertDomandeLV2_1();
                        break;
                    case (3):
                        insertDomandeLV2_2();
                        break;
                    case (4):
                        insertDomandeLV2_3_2();
                        break;
                    case (5):
                        insertDomandeLV2_3();
                        break;
                    case (6):
                        insertDomandeLV2_1_2();
                        break;
                    case (7):
                        insertDomandeLV2_2();
                        break;
                }
            } else if (livello == 3) { /*ESPERTO*/
                switch (numRand) {
                    case (1):
                        insertDomandeLV3_2_2();
                        break;
                    case (2):
                        insertDomandeLV3_1();
                        break;
                    case (3):
                        insertDomandeLV3_2();
                        break;
                    case (4):
                        insertDomandeLV3_3_2();
                        break;
                    case (5):
                        insertDomandeLV3_3();
                        break;
                    case (6):
                        insertDomandeLV3_1_2();
                        break;
                    case (7):
                        insertDomandeLV3_2();
                        break;
                }
            }

        } else      /*-----num random dello score cioè se lo score mod 3da come resto 0-1-2   */


            if (numRandScoreAddizioni == 2) {

                numRand = 1 + (int) (Math.random() * 7);

                if (livello == 1) { /*PRINCIPIANTE*/
                    switch (numRand) {
                        case (7):
                            insertDomandeLV1_4();
                            break;
                        case (6):
                            insertDomandeLV1_2_2();
                            break;
                        case (5):
                            insertDomandeLV1_1_2();
                            break;
                        case (4):
                            insertDomandeLV1_4_2();
                            break;
                        case (3):
                            insertDomandeLV1_1();
                            break;
                        case (2):
                            insertDomandeLV1_2_1();
                            break;
                        case (1):
                            insertDomandeLV1_3();
                            break;

                    }
                } else if (livello == 2) {  /*NORMLE*/
                    switch (numRand) {
                        case (1):
                            insertDomandeLV2_3();
                            break;

                        case (2):
                            insertDomandeLV2_1();
                            break;
                        case (3):
                            insertDomandeLV2_1_2();
                            break;
                        case (4):
                            insertDomandeLV2_3_2();
                            break;
                        case (5):
                            insertDomandeLV2_2_2();
                            break;
                        case (6):
                            insertDomandeLV2_2();
                            break;
                        case (7):
                            insertDomandeLV2_1();
                            break;
                    }
                } else if (livello == 3) {  /*ESPERTO*/
                    switch (numRand) {
                        case (1):
                            insertDomandeLV3_3();
                            break;

                        case (2):
                            insertDomandeLV3_1();
                            break;
                        case (3):
                            insertDomandeLV3_1_2();
                            break;
                        case (4):
                            insertDomandeLV3_3_2();
                            break;
                        case (5):
                            insertDomandeLV3_2_2();
                            break;
                        case (6):
                            insertDomandeLV3_2();
                            break;
                        case (7):
                            insertDomandeLV3_1();
                            break;
                    }
                }

            }


    }


    private void disabilitaRisposte() {
        /**visibile il bottone risultati e disbilitazione dei pulsanti delle risposte**/
        bottoneRisultati.setVisibility(View.VISIBLE);
        llRisultati.setVisibility(View.VISIBLE);
        A.setClickable(false);
        B.setClickable(false);
        C.setClickable(false);
        D.setClickable(false);
    }



    /*---------------------gestione player --------------------*/

    /**
     * player campanella e bat
     */
    public void startCampanella() {

        if (suoni) {/*impostazioni suoni si **/
            /*Media layer*/
            mpCampanella = MediaPlayer.create(this, getResources().getIdentifier("campanella", "raw", getPackageName()));
            mpCampanella = MediaPlayer.create(this, R.raw.campanella);
            mpCampanella.start();
        }

    }

    /**
     * player campanella rilascio risorsa
     */
    private void releaseResourcesCampanella() {
        if (suoni) {
            mpCampanella.release();
        }

    }

    /**
     * player battuta
     */
    public void startBattuta() {
        /**suoni****/

        if (suoni) {
            mpBat = MediaPlayer.create(this, getResources().getIdentifier("bat", "raw", getPackageName()));
            mpBat = MediaPlayer.create(this, R.raw.bat);
            mpBat.start();
        }

    }

    public void releaseResourcesBattuta() {
        if (suoni) {
            mpBat.release();
        }

    }


    public void startTic() {
        if (suoni) {
            mpTic = MediaPlayer.create(this, getResources().getIdentifier("ticchettio", "raw", getPackageName()));
            mpTic = MediaPlayer.create(this, R.raw.ticchettio);
            mpTic.start();
        }


    }

    public void releaseResourcesTic() {

        if (suoni) {
            mpTic.release();
        }

    }








    /*---------------------------------------TIME TIME TIME *******************/


    /**
     * metodo che gestisce il countdown
     */
    private void startTimer() {

        startTic();

        /*suono ticchettio orologio digitale*/

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();

                if (ultimaDomanda) {
                    stopTimer();
                    lltimezone.setBackground(getDrawable(R.drawable.count_down_stop));
                    Toast.makeText(AddizioniActivity.this, "Domande finite", Toast.LENGTH_LONG).show();

                }
                /**funziona*/
                if (stopCountDownPressedHome) {
                    pauseTimer();
                    // startBattuta();
                    stopCountDownPressedHome = false;
                }


            }

            @Override
            public void onFinish() {

                startCampanella();
                if (suoni) {
                    mpTic.stop();
                }


                lltimezone = (LinearLayout) findViewById(R.id.timezone);
                lltimezone.setBackground(getDrawable(R.drawable.count_down_finish));
                mTimerRunning = false;
                disabilitaRisposte();
                Toast.makeText(AddizioniActivity.this, "Tempo scaduto", Toast.LENGTH_LONG).show();

            }


        }.start();
        mTimerRunning = true;


    }

    /**
     * metodi timer
     */
    public void pauseTimer() {
        if (suoni) {
            mpTic.pause();

        }
        mCountDownTimer.cancel();
        mTimerRunning = false;

    }

    /**
     * stoppa il countdown se hai risposto all'ultima domanda
     */
    public void stopTimer() {
        if (suoni) {
            mpTic.stop();
        }

        mTimerRunning = false;
        mCountDownTimer.cancel();
    }

    /**
     * disabilita i bottoni e mostra il  bottone risultati
     */
    public void updateCountDownText() {
        minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        //int milliseconds = (int) (mTimeLeftInMillis);

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        timeText.setText(timeLeftFormatted);
    }

    /**
     * setta il valore del countdown in base al livello scelto
     */
    protected void setCountDownLevel() {
        if (livello == 1) {
            tempoQuesito = 26000;
            mTimeLeftInMillis = tempoQuesito;

        } else if (livello == 2) {
            tempoQuesito = 31000;
            mTimeLeftInMillis = tempoQuesito;


        } else if (livello == 3) {
            tempoQuesito = 36000;
            mTimeLeftInMillis = tempoQuesito;


        }
    }


    /*---------vibrazione--------------------*/

    /**
     * @return
     */
    public boolean isHaveVibrate() {
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {

            return true;
        } else {
            return false;
        }

    }

    /**
     *
     */
    public void checkImpostazioni() {

        if ("si".equalsIgnoreCase(gf.caricaImpostazioni(getApplicationContext(), "Suoni"))) {
            suoni = true;
        } else if ("no".equalsIgnoreCase(gf.caricaImpostazioni(getApplicationContext(), "Suoni"))) {

            suoni = false;
        }

        if ("si".equalsIgnoreCase(gf.caricaImpostazioni(getApplicationContext(), "Vibrazione"))) {
            vibrazione = true;
        } else       if ("no".equalsIgnoreCase(gf.caricaImpostazioni(getApplicationContext(), "Suoni"))) {
            vibrazione = false;
        }

    }


    /*-----------------------------------------OVERRIDE METODI LIFECICLE ACTIVITY *******************/
    @Override
    protected void onPause() {
        //Toast.makeText(AddizioniActivity.this, "Pause", Toast.LENGTH_LONG).show();
        // pausePressed = true;
        super.onPause();
    }

    @Override
    protected void onResume() {

        //bottoneRisultati.setClickable(true);

        /*DISTINZIONE TRA I VARI RESUME*/
        if (countDownIniziato) {
            resumeStartCountDown = true;

            /*resume activity restart count down*/
            startTimer();

            //Toast.makeText(AddizioniActivity.this, "Resume countDown", Toast.LENGTH_LONG).show();

        } else {
            countDownIniziato = true;
        }

        // Toast.makeText(AddizioniActivity.this, "Resume", Toast.LENGTH_LONG).show();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        //Toast.makeText(AddizioniActivity.this, "Restart", Toast.LENGTH_LONG).show();
        super.onRestart();
    }

    /**
     * quando premo home
     */
    @Override
    protected void onStop() {
        stopCountDownPressedHome = true;
        //Toast.makeText(AddizioniActivity.this, "Stop", Toast.LENGTH_LONG).show();
        super.onStop();

    }


}

