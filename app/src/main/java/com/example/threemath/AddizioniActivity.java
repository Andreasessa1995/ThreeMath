package com.example.threemath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Locale;


public class AddizioniActivity extends AppCompatActivity {

    /**
     * elementi della view
     **/
    TextView testoDomanda;
    TextView testoCategoriaDomanda;
    Button A, B, C, D, bottoneRisultati;
    LinearLayout llcategoriaDomanda;
    LinearLayout llDomanda;
    LinearLayout llCorpoDomanda;
    LinearLayout llRisposte;
    LinearLayout llRisultati;


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

    /**
     * elementi suoni
     */
    MediaPlayer mpCampanella;
    MediaPlayer mpBat;


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


    /**
     * TIME
     **/

    private static final long START_TIME_IN_MILLIS = 25000;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addizione);


        /** dimensioni schermo telefono **/
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        deviceWidth = metrics.heightPixels;
        deviceHeight = metrics.widthPixels;

        Log log = null;
        log.d("DEBUG", "DIMENSIONI SCHERMO = " + deviceHeight);
        log.d("DEBUG", "DIMENSIONI SCHERMO = " + deviceWidth);


        timeText = (TextView) findViewById(R.id.timeText);


        //llcategoriaDomanda = ( LinearLayout)  findViewById(R.id.llcategoriaDomanda);
        //llcategoriaDomanda.setX((deviceHeight*15)/100);
        //llcategoriaDomanda.x
        // llCorpoDomanda = ( LinearLayout)  findViewById(R.id.corpoDomanda);
        // llRisposte = ( LinearLayout) findViewById(R.id.risposte);


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

        /**sceglie le domande da inserire **/
        choseDomande();

        sizeq = quesito.size();


        //log.d("DEBUG", "dimensioni altezza =  " + deviceHeight + " dimensioni base" + deviceWidth);


        testoCategoriaDomanda.setText("Addizione");

        testoDomanda.setText(quesito.get(indiceDomanda).getDomanda());

        A.setText(quesito.get(indiceDomanda).getRispostaEsatta());
        B.setText(quesito.get(indiceDomanda).getRispostaErrata1());
        C.setText(quesito.get(indiceDomanda).getRispostaErrata2());
        D.setText(quesito.get(indiceDomanda).getRispostaErrata3());

        /**TIME TIME TIME */

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

            // log.d("DEBUG", "wwww333333333  Wwwwwwwwwwwwww= presssedddd = " + risposteQuesito.get(indiceDomanda));
            /** ultima domanda **/
            if (indiceDomanda == sizeq - 1) {
                startBattuta();
                startCampanella();

                ultimaDomanda = true;
                indiceDomanda++;
                for (int i = 0; i < risposteQuesito.size(); i++) {
                    // log.d("DEBUG", "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqq = " );

                    if (risposteQuesito.get(i)) {
                        numRispEsatte++;
                    } else {
                        numRispErrate++;
                    }
                }

                //log.d("DEBUG", "corrette = " +numRispEsatte);
                //log.d("DEBUG", "errate= " + numRispErrate);


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
                startBattuta();
                /**disposizione randomica risposte sui vari bottoni**/

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

    /**
     * player campanella e bat
     */
    public void startCampanella() {
        /*Media layer*/
        mpCampanella = MediaPlayer.create(this, getResources().getIdentifier("campanella", "raw", getPackageName()));
        mpCampanella = MediaPlayer.create(this, R.raw.campanella);
        mpCampanella.start();


    }

    public void releaseResourcesCampanella() {
        mpCampanella.release();
    }

    public void startBattuta() {
        /**suoni*/

        mpCampanella = MediaPlayer.create(this, getResources().getIdentifier("bat", "raw", getPackageName()));

        mpBat = MediaPlayer.create(this, R.raw.bat);
        mpBat.start();
    }

    public void releaseResourcesBattuta() {
        mpBat.release();
    }


    /**
     * apre l'activity dei risultati ottenuti
     *
     * @param v
     */
    public void onClickRisultati(View v) {

        startBattuta();
        mTimerRunning = false;
        mCountDownTimer.cancel();
        //releaseResourcesBattuta();
        //releaseResourcesCampanella();

        /*new intent*/
        Intent intent = new Intent();
        //Intent intent =getIntent();
        intent.setClass(getApplicationContext(), RisultatiActivity.class);
        intent.putExtra("CORRETTE", numRispEsatte);
        intent.putExtra("ERRATE", numRispErrate);
        intent.putExtra("TIMEMINUTI", minutes);
        intent.putExtra("TIMESECONDI", seconds);
        intent.putExtra("TIMEQUESITO", tempoQuesito);
        startActivityForResult(intent, 0);
        onBackPressed();

    }


    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV1_1() {

        Domanda domanda1 = new Domanda("1 + 3", "4", "2", "3", "5");
        quesito.add(domanda1);
        Domanda domanda2 = new Domanda("5 + 4", "9", "8", "10", "6");
        quesito.add(domanda2);
        Domanda domanda3 = new Domanda("10 + 9", "19", "18", "29", "15");
        quesito.add(domanda3);
        Domanda domanda4 = new Domanda("12+13", "25", "35", "26", "32");
        quesito.add(domanda4);
        Domanda domanda5 = new Domanda("18 + 22", "40", "30", "28", "50");
        quesito.add(domanda5);

    }

    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV1_2() {

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
     * sceglie randomicamente le domande da caricare
     */
    private void choseDomande() {

        numRand = 1 + generatore.nextInt(2);
        switch (numRand) {
            case (1):
                insertDomandeLV1_1();
            case (2):
                insertDomandeLV1_2();
        }


    }


    /*---------------------------------------TIME TIME TIME *******************/


    /**
     * metodo che gestisce il countdown
     */
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();

                if (ultimaDomanda) {
                    stopTimer();
                }
                /**funziona*/
                if (stopCountDownPressedHome){
                    pauseTimer();
                    startBattuta();
                    stopCountDownPressedHome=false;
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
                mTimerRunning = false;
                startCampanella();


            }



        }.start();
        mTimerRunning = true;


    }

    /**
     * metodi timer
     */
    public void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;

    }

    /**
     * stoppa il countdown se hai risposto all'ultima domanda
     */
    public void stopTimer() {
        mTimerRunning = false;
        mCountDownTimer.cancel();
    }

/*-----------------------------------------OVERRIDE METODI LIFECICLE ACTIVITY *******************/
    @Override
    protected void onPause() {
        Toast.makeText(AddizioniActivity.this, "Pause", Toast.LENGTH_LONG).show();
        // pausePressed = true;
        super.onPause();
    }

    @Override
    protected void onResume() {

        /*DISTINZIONE TRA I VARI RESUME*/
        if(countDownIniziato){
            resumeStartCountDown = true;
            
            /*resume activity restart count down*/
            startTimer();

            //Toast.makeText(AddizioniActivity.this, "Resume countDown", Toast.LENGTH_LONG).show();

        }else {
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
        Toast.makeText(AddizioniActivity.this, "Stop", Toast.LENGTH_LONG).show();
        super.onStop();

    }


    public void updateCountDownText() {
        minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        //int milliseconds = (int) (mTimeLeftInMillis);

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        timeText.setText(timeLeftFormatted);
    }


}

