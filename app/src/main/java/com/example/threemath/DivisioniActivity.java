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


public class DivisioniActivity extends AppCompatActivity {

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
    int numRandScoreDivisioni = 0;
    String categoria = "Divisioni";

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
        numRandScoreDivisioni = intent.getIntExtra("SCORE", numRandScoreDivisioni);


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
        testoCategoriaDomanda.setText("DIVISIONI");
        testoCategoriaDomanda.setTextSize(25);
        categoriaQuiz.setText("÷");
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

                //log.d("DEBUG", "Numero random dello score modulo 3 = " + numRandScoreMoltiplicazioni);


                /*-----num random dello score cioè se lo score mod 3da come resto 0-1-2   */

                if (numRandScoreDivisioni == 0) {

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

                } else if (numRandScoreDivisioni == 1) {
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
                } else if (numRandScoreDivisioni == 2) {          /*-----num random dello score cioè se lo score mod 3da come resto 0-1-2   */
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
        startActivityForResult(intent, 0);


        releaseResourcesCampanella();
        releaseResourcesBattuta();
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
     * Ogni categoria ha un livello LV1_ un numero progressivo _1 e un identificativo che indica
     * se è mischiata o meno_2
     * inserisce le domande della categoria scelta ogni verione ha qualcosa di differente
     * quelle con _2_2 significa che sono sempre le stesse domande ma ruotate per aumentare ancora di
     * più la casualità...
     */
    public void insertDomandeLV1_1() {
        quesito.clear();
        Domanda domanda1 = new Domanda("6 ÷ 3", "2", "3", "1", "4");
        quesito.add(domanda1);
        Domanda domanda2 = new Domanda("12 ÷ 3", "4", "8", "6", "2");
        quesito.add(domanda2);
        Domanda domanda3 = new Domanda("20 ÷ 4", "5", "6", "3", "2");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("18 ÷ 6", "3", "2", "6", "4");
        quesito.add(domanda4);
        Domanda domanda5 = new Domanda("56 ÷ 8", "7", "6", "8", "5");
        quesito.add(domanda5);

    }

    /**
     * inserisce le domande della categoria scelta ogni verione ha qualcosa di differente
     * quelle con _2_2 significa che sono sempre le stesse domande ma ruotate per aumentare ancora di
     * più la casualità...
     */
    public void insertDomandeLV1_1_2() {
        quesito.clear();
        Domanda domanda5 = new Domanda("56 ÷ 8", "7", "6", "8", "5");
        quesito.add(domanda5);
        Domanda domanda3 = new Domanda("20 ÷ 4", "5", "6", "3", "2");
        quesito.add(domanda3);
        Domanda domanda1 = new Domanda("6 ÷ 3", "2", "3", "1", "4");
        quesito.add(domanda1);
        Domanda domanda4 = new Domanda("18 ÷ 6", "3", "2", "6", "4");
        quesito.add(domanda4);
        Domanda domanda2 = new Domanda("12 ÷ 3", "4", "8", "6", "2");
        quesito.add(domanda2);


    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV1_2_1() {

        quesito.clear();
        Domanda domanda1 = new Domanda("14 ÷ 7", "2", "1", "3", "7");
        quesito.add(domanda1);
        Domanda domanda2 = new Domanda("21 ÷ 3", "7", "8", "4", "6");
        quesito.add(domanda2);
        Domanda domanda3 = new Domanda("40 ÷ 8", "5", "4", "3", "7");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("10 ÷ 5", "6", "5", "7", "2");
        quesito.add(domanda4);
        Domanda domanda5 = new Domanda("24 ÷ 3", "8", "2", "6", "9");
        quesito.add(domanda5);


    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV1_2_2() {

        quesito.clear();
        Domanda domanda5 = new Domanda("24 ÷ 3", "8", "2", "6", "9");
        quesito.add(domanda5);
        Domanda domanda3 = new Domanda("40 ÷ 8", "5", "4", "3", "7");
        quesito.add(domanda3);
        Domanda domanda2 = new Domanda("21 ÷ 3", "7", "8", "4", "6");
        quesito.add(domanda2);
        Domanda domanda4 = new Domanda("10 ÷ 5", "6", "5", "7", "2");
        quesito.add(domanda4);
        Domanda domanda1 = new Domanda("14 ÷ 7", "2", "1", "3", "7");
        quesito.add(domanda1);


    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV1_3() {

        quesito.clear();
        Domanda domanda1 = new Domanda("27 ÷ 9", "3", "2", "1", "5");
        quesito.add(domanda1);
        Domanda domanda5 = new Domanda("48 ÷ 6", "8", "4", "6", "12");
        quesito.add(domanda5);
        Domanda domanda2 = new Domanda("36 ÷ 6", "6", "4", "2", "3");
        quesito.add(domanda2);
        Domanda domanda3 = new Domanda("63 ÷ 9", "7", "6", "7", "8");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("81 ÷ 9", "9", "7", "8", "6");
        quesito.add(domanda4);


    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV1_4() {

        quesito.clear();

        Domanda domanda1 = new Domanda("25 ÷ 5", "5", "3", "4", "2");
        quesito.add(domanda1);
        Domanda domanda2 = new Domanda("64 ÷ 8", "8", "7", "6", "9");
        quesito.add(domanda2);
        Domanda domanda3 = new Domanda("9 ÷ 3", "3", "2", "1", "4");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("16 ÷ 4", "4", "2", "6", "3");
        quesito.add(domanda4);
        Domanda domanda5 = new Domanda("49 ÷ 7", "7", "3", "5", "6");
        quesito.add(domanda5);
    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV1_4_2() {

        quesito.clear();
        Domanda domanda4 = new Domanda("16 ÷ 4", "4", "2", "6", "3");
        quesito.add(domanda4);
        Domanda domanda3 = new Domanda("9 ÷ 3", "3", "2", "1", "4");
        quesito.add(domanda3);
        Domanda domanda1 = new Domanda("25 ÷ 5", "5", "3", "4", "2");
        quesito.add(domanda1);
        Domanda domanda5 = new Domanda("49 ÷ 7", "7", "3", "5", "6");
        quesito.add(domanda5);
        Domanda domanda2 = new Domanda("64 ÷ 8", "8", "7", "6", "9");
        quesito.add(domanda2);


    }





    /*-------------------------------------------LV2--normale--------------------------------------------------------------------------------------------------*/

    /**
     * inserisce le domande della categoria scelta, versione 1 (v1) LV2
     */
    public void insertDomandeLV2_1() {
        quesito.clear();

        Domanda domanda1 = new Domanda("120 ÷ 12", "10", "14", "20", "12");
        quesito.add(domanda1);
        Domanda domanda2 = new Domanda("104 ÷ 13", "8", "10", "13", "12");
        quesito.add(domanda2);

        Domanda domanda3 = new Domanda("77 ÷ 11", "7", "6", "11", "17");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("225 ÷ 15", "15", "10", "25", "5");
        quesito.add(domanda4);

        Domanda domanda5 = new Domanda("126 ÷ 9", "14", "13", "12", "15");
        quesito.add(domanda5);
        Domanda domanda6 = new Domanda("60 ÷ 5", "12", "10", "9", "15");
        quesito.add(domanda6);

        Domanda domanda7 = new Domanda("54 ÷ 18", "3", "2", "6", "4");
        quesito.add(domanda7);

    }

    /**
     * inserisce le domande della categoria scelta questa è la versione normale, v1 , mischiata sempre della numero 1
     */
    public void insertDomandeLV2_1_2() {
        quesito.clear();
        Domanda domanda5 = new Domanda("126 ÷ 9", "14", "13", "12", "15");
        quesito.add(domanda5);
        Domanda domanda1 = new Domanda("120 ÷ 12", "10", "14", "20", "12");
        quesito.add(domanda1);
        Domanda domanda6 = new Domanda("60 ÷ 5", "12", "10", "9", "15");
        quesito.add(domanda6);
        Domanda domanda4 = new Domanda("225 ÷ 15", "15", "10", "25", "5");
        quesito.add(domanda4);
        Domanda domanda7 = new Domanda("54 ÷ 18", "3", "2", "6", "4");
        quesito.add(domanda7);
        Domanda domanda3 = new Domanda("77 ÷ 11", "7", "6", "11", "17");
        quesito.add(domanda3);
        Domanda domanda2 = new Domanda("104 ÷ 13", "8", "10", "13", "12");
        quesito.add(domanda2);
    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV2_2() {

        quesito.clear();
        Domanda domanda1 = new Domanda("108 ÷ 12", "9", "12", "10", "6");
        quesito.add(domanda1);
        Domanda domanda2 = new Domanda("112 ÷ 8", "14", "12", "8", "10");
        quesito.add(domanda2);

        Domanda domanda3 = new Domanda("45 ÷ 15", "3", "4", "3", "6");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("100 ÷ 5", "20", "10", "25", "5");
        quesito.add(domanda4);

        Domanda domanda5 = new Domanda("119 ÷ 7", "17", "14", "15", "13");
        quesito.add(domanda5);
        Domanda domanda6 = new Domanda("90 ÷ 18", "5", "6", "8", "4");
        quesito.add(domanda6);

        Domanda domanda7 = new Domanda("152 ÷ 8", "19", "17", "16", "18");
        quesito.add(domanda7);


    }

    /**
     * inserisce le domande della categoria scelta  versione mischiata della seconda scelta v2
     */
    public void insertDomandeLV2_2_2() {

        quesito.clear();
        Domanda domanda5 = new Domanda("119 ÷ 7", "17", "14", "15", "13");
        quesito.add(domanda5);
        Domanda domanda1 = new Domanda("108 ÷ 12", "9", "12", "10", "6");
        quesito.add(domanda1);
        Domanda domanda6 = new Domanda("90 ÷ 18", "5", "6", "8", "4");
        quesito.add(domanda6);
        Domanda domanda4 = new Domanda("100 ÷ 5", "20", "10", "25", "5");
        quesito.add(domanda4);
        Domanda domanda7 = new Domanda("152 ÷ 8", "19", "17", "16", "18");
        quesito.add(domanda7);
        Domanda domanda3 = new Domanda("45 ÷ 15", "3", "4", "3", "6");
        quesito.add(domanda3);
        Domanda domanda2 = new Domanda("112 ÷ 8", "14", "12", "8", "10");
        quesito.add(domanda2);
    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV2_3() {

        quesito.clear();
        Domanda domanda1 = new Domanda("288 ÷ 18", "16", "14", "15", "18");
        quesito.add(domanda1);
        Domanda domanda2 = new Domanda("182 ÷ 14", "13", "12", "16", "14");
        quesito.add(domanda2);

        Domanda domanda3 = new Domanda("228 ÷ 19", "12", "14", "18", "16");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("168 ÷ 12", "14", "18", "16", "12");
        quesito.add(domanda4);

        Domanda domanda5 = new Domanda("240 ÷ 15", "16", "12", "14", "15");
        quesito.add(domanda5);
        Domanda domanda6 = new Domanda("380 ÷ 20", "19", "20", "18", "21");
        quesito.add(domanda6);

        Domanda domanda7 = new Domanda("378 ÷ 18", "21", "28", "20", "18");
        quesito.add(domanda7);


    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV2_3_2() {

        quesito.clear();
        Domanda domanda5 = new Domanda("240 ÷ 15", "16", "12", "14", "15");
        quesito.add(domanda5);
        Domanda domanda1 = new Domanda("288 ÷ 18", "16", "14", "15", "18");
        quesito.add(domanda1);
        Domanda domanda6 = new Domanda("380 ÷ 20", "19", "20", "18", "21");
        quesito.add(domanda6);
        Domanda domanda4 = new Domanda("168 ÷ 12", "14", "18", "16", "12");
        quesito.add(domanda4);
        Domanda domanda2 = new Domanda("182 ÷ 14", "13", "12", "16", "14");
        quesito.add(domanda2);
        Domanda domanda7 = new Domanda("378 ÷ 18", "21", "28", "20", "18");
        quesito.add(domanda7);
        Domanda domanda3 = new Domanda("228 ÷ 19", "12", "14", "18", "16");
        quesito.add(domanda3);


    }

    /*-------------------------------------------LV3--esperto--------------------------------------------------------------------------------------------------*/

    /**
     * inserisce le domande della categoria scelta, versione 1 (v1)
     */
    public void insertDomandeLV3_1() {
        quesito.clear();

        Domanda domanda1 = new Domanda("1200 ÷ 12", "100", "112", "12", "20");
        quesito.add(domanda1);
        Domanda domanda2 = new Domanda("1470 ÷ 14", "105", "104", "15", "25");
        quesito.add(domanda2);

        Domanda domanda3 = new Domanda("2232 ÷ 22", "106", "108", "104", "112");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("2240 ÷ 20", "112", "120", "100", "104");
        quesito.add(domanda4);

        Domanda domanda5 = new Domanda("2596 ÷ 22", "118", "116", "114", "120");
        quesito.add(domanda5);
        Domanda domanda6 = new Domanda("2500 ÷ 20", "125", "150", "115", "120");
        quesito.add(domanda6);

        Domanda domanda7 = new Domanda("3600 ÷ 30", "30", "24", "34", "38");
        quesito.add(domanda7);

    }

    /**
     * inserisce le domande della categoria scelta, versione 1 (v1_2)
     */
    public void insertDomandeLV3_1_2() {
        quesito.clear();

        Domanda domanda5 = new Domanda("2596 ÷ 22", "118", "116", "114", "120");
        quesito.add(domanda5);
        Domanda domanda7 = new Domanda("3600 ÷ 30", "30", "24", "34", "38");
        quesito.add(domanda7);
        Domanda domanda1 = new Domanda("1200 ÷ 12", "100", "112", "12", "20");
        quesito.add(domanda1);
        Domanda domanda4 = new Domanda("2240 ÷ 20", "112", "120", "100", "104");
        quesito.add(domanda4);
        Domanda domanda2 = new Domanda("1470 ÷ 14", "105", "104", "15", "25");
        quesito.add(domanda2);
        Domanda domanda6 = new Domanda("2500 ÷ 20", "125", "150", "115", "120");
        quesito.add(domanda6);
        Domanda domanda3 = new Domanda("2232 ÷ 22", "106", "108", "104", "112");
        quesito.add(domanda3);


    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV3_2() {

        quesito.clear();
        Domanda domanda1 = new Domanda("1875 ÷ 15", "125", "130", "115", "20");
        quesito.add(domanda1);
        Domanda domanda2 = new Domanda("1819 ÷ 17", "107", "100", "104", "115");
        quesito.add(domanda2);

        Domanda domanda3 = new Domanda("2880 ÷ 18", "160", "180", "22", "28");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("1596 ÷ 14", "114", "116", "120", "150");
        quesito.add(domanda4);

        Domanda domanda5 = new Domanda("2750 ÷ 110", "25", "27", "23", "26");
        quesito.add(domanda5);
        Domanda domanda6 = new Domanda("6000 ÷ 150", "40", "50", "54", "62");
        quesito.add(domanda6);

        Domanda domanda7 = new Domanda("1980 ÷ 18", "110", "100", "180", "11");
        quesito.add(domanda7);


    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV3_2_2() {

        quesito.clear();
        Domanda domanda5 = new Domanda("2750 ÷ 110", "25", "27", "23", "26");
        quesito.add(domanda5);
        Domanda domanda3 = new Domanda("2880 ÷ 18", "160", "180", "22", "28");
        quesito.add(domanda3);
        Domanda domanda1 = new Domanda("1875 ÷ 15", "125", "130", "115", "20");
        quesito.add(domanda1);
        Domanda domanda4 = new Domanda("1596 ÷ 14", "114", "116", "120", "150");
        quesito.add(domanda4);
        Domanda domanda7 = new Domanda("1980 ÷ 18", "110", "100", "180", "11");
        quesito.add(domanda7);
        Domanda domanda2 = new Domanda("1819 ÷ 17", "107", "100", "104", "115");
        quesito.add(domanda2);
        Domanda domanda6 = new Domanda("6000 ÷ 150", "40", "50", "54", "62");
        quesito.add(domanda6);


    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV3_3() {

        quesito.clear();
        Domanda domanda1 = new Domanda("3375 ÷ 25", "135", "133", "129", "150");
        quesito.add(domanda1);
        Domanda domanda2 = new Domanda("3186 ÷ 118", "27", "31", "24", "15");
        quesito.add(domanda2);

        Domanda domanda3 = new Domanda("2640 ÷ 120", "22", "26", "20", "24");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("1392 ÷ 116", "12", "13", "18", "16");
        quesito.add(domanda4);

        Domanda domanda5 = new Domanda("5250 ÷ 150", "35", "45", "50", "60");
        quesito.add(domanda5);
        Domanda domanda6 = new Domanda("5400 ÷ 30", "180", "160", "140", "220");
        quesito.add(domanda6);

        Domanda domanda7 = new Domanda("3080 ÷ 110", "28", "24", "26", "20");
        quesito.add(domanda7);


    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV3_3_2() {

        quesito.clear();
        Domanda domanda5 = new Domanda("5250 ÷ 150", "35", "45", "50", "60");
        quesito.add(domanda5);
        Domanda domanda4 = new Domanda("1392 ÷ 116", "12", "13", "18", "16");
        quesito.add(domanda4);
        Domanda domanda1 = new Domanda("3375 ÷ 25", "135", "133", "129", "150");
        quesito.add(domanda1);
        Domanda domanda6 = new Domanda("5400 ÷ 30", "180", "160", "140", "220");
        quesito.add(domanda6);
        Domanda domanda2 = new Domanda("3186 ÷ 118", "27", "31", "24", "15");
        quesito.add(domanda2);
        Domanda domanda7 = new Domanda("3080 ÷ 110", "28", "24", "26", "20");
        quesito.add(domanda7);
        Domanda domanda3 = new Domanda("2640 ÷ 120", "22", "26", "20", "24");
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
        log.d("DEBUG", "Numero random con math= " + numRand);

        /*-----num random dello score cioè se lo score mod 3da come resto 0-1-2   */
        if (numRandScoreDivisioni == 0) {

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
        else if (numRandScoreDivisioni== 1) {

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


            if (numRandScoreDivisioni == 2) {

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
                    Toast.makeText(DivisioniActivity.this, "Domande finite", Toast.LENGTH_LONG).show();

                }
                /**funziona*/
                if (stopCountDownPressedHome) {
                    pauseTimer();
                    // startBattuta();
                    stopCountDownPressedHome = false;
                }

                /** if(pausePressed){
                 pauseTimer();
                 }
                 if(resumePressed){
                 startTimer();
                 }*/
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
                Toast.makeText(DivisioniActivity.this, "Tempo scaduto", Toast.LENGTH_LONG).show();

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
        } else {
            suoni = false;
        }

        if ("si".equalsIgnoreCase(gf.caricaImpostazioni(getApplicationContext(), "Vibrazione"))) {
            vibrazione = true;
        } else {
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

