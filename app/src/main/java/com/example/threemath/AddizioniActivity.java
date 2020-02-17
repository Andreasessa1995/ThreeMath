package com.example.threemath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import android.view.Display;

import java.util.Locale;


public class AddizioniActivity extends AppCompatActivity {

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
    boolean ultimaDomanda =false;

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




    /**TIME **/

    private static final long START_TIME_IN_MILLIS = 60000;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;/*indica se sta scendendo il tempo*/
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    TextView timeText ;


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

        timeText = (TextView) findViewById(R.id.timeText);





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


        insertDomandeLV1();






        sizeq = quesito.size();



        log.d("DEBUG", "dimensioni altezza =  " + deviceHeight + " dimensioni base" + deviceWidth);



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

            log.d("DEBUG", "wwww333333333  Wwwwwwwwwwwwww= presssedddd = " + risposteQuesito.get(indiceDomanda));

            if(indiceDomanda==sizeq-1){ /** ultima domanda **/
                ultimaDomanda=true;
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

    /**
     * apre l'activity dei risultati ottenuti
     * @param v
     */
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


    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV1(){

        Domanda domanda1 = new Domanda("1 + 3", "4", "2", "3", "5");
        quesito.add( domanda1);
        Domanda domanda2 = new Domanda("5 + 4", "9", "8", "10", "6");
        quesito.add( domanda2 );
        Domanda domanda3 = new Domanda("10 + 9", "19", "18", "29", "15");
        quesito.add( domanda3);
        Domanda domanda4 = new Domanda("12+13", "25", "35", "26", "32");
        quesito.add( domanda4);
        Domanda domanda5 = new Domanda("18 + 22", "40", "30", "28", "50");
        quesito.add( domanda5);

    }
    /**
     * inserisce le domande della categoria scelta
     */
    public void insertDomandeLV2(){

        Domanda domanda1 = new Domanda("25 + 12", "37", "2", "3", "5");
        quesito.add( domanda1);
        Domanda domanda2 = new Domanda("37 + 15", "52", "54", "42", "56");
        quesito.add( domanda2 );
        Domanda domanda3 = new Domanda("42 + 19", "61", "72", "51", "63");
        quesito.add( domanda3);


    }

    /**
     * metodo che gestisce il countdown
     */
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                if (ultimaDomanda){
                    mTimerRunning=false;
                    mCountDownTimer.cancel();
                }
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;

            }
        }.start();
        mTimerRunning = true;

    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        timeText.setText(timeLeftFormatted);
    }




}

