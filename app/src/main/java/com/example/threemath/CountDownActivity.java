package com.example.threemath;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class CountDownActivity extends AppCompatActivity {


    /**
     * time
     */
    TextView count_down;
    private static final long START_TIME_IN_MILLIS = 3100;
    long tempoQuesito = START_TIME_IN_MILLIS;
    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;/*indica se sta scendendo il tempo*/
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    int seconds = 0;
    boolean countDownIniziato = false;
    boolean resumeStartCountDown = false;
    boolean stopCountDownPressedHome = false;


    MediaPlayer mpCountDown;


    String categoria = "";
    int livello = 1;
    int scoreAddizioni = 0;
    int numScoreRandom = 0;


    GestoreFile gf = new GestoreFile();
    Boolean suoni = true;
    Boolean vibrazione = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_iniziale);

        count_down = (TextView) findViewById(R.id.coutDown);


        checkImpostazioni();

        startCountDown();

        startTimer();

        Intent intent = getIntent();
        categoria = intent.getStringExtra("CATEGORIA");
        Log log = null;
        //  log.d("DEBUG", "CATEGORIA CATEGORIA CATEGORIA CATEGORIA 3 3 3 3 3 = " +categoria );

        livello = intent.getIntExtra("LIVELLO", livello);
        scoreAddizioni = intent.getIntExtra("SCORE", scoreAddizioni);

        if (scoreAddizioni % 3 == 0) {
            numScoreRandom = 0;
        } else if (scoreAddizioni % 3 == 1) {
            numScoreRandom = 1;
        } else {
            numScoreRandom = 2;
            //log.d("DEBUG", "222 random score random score score= " +numScoreRandom );

        }


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

                /**funziona*/
                if (stopCountDownPressedHome) {
                    pauseTimer();
                    stopCountDownPressedHome = false;
                }

            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                Intent i = new Intent();
                if (categoria.equalsIgnoreCase("Addizioni")) {
                    i = new Intent(getApplicationContext(), AddizioniActivity.class);
                    i.putExtra("LIVELLO", livello);
                    i.putExtra("SCORE", numScoreRandom);
                    startActivityForResult(i, 0);

                    countDownRelease();

                    onBackPressed();

                } else if (categoria.equalsIgnoreCase("Sottrazioni")) {
                    i = new Intent(getApplicationContext(), SottrazioniActivity.class);
                    i.putExtra("LIVELLO", livello);
                    i.putExtra("SCORE", numScoreRandom);
                    startActivityForResult(i, 0);
                    countDownRelease();
                    onBackPressed();

                }else if (categoria.equalsIgnoreCase("Moltiplicazioni")) {
                    i = new Intent(getApplicationContext(), MoltiplicazioniActivity.class);
                    i.putExtra("LIVELLO", livello);
                    i.putExtra("SCORE", numScoreRandom);
                    startActivityForResult(i, 0);
                    countDownRelease();
                    onBackPressed();

                }else if (categoria.equalsIgnoreCase("Divisioni")) {
                    i = new Intent(getApplicationContext(), DivisioniActivity.class);
                    i.putExtra("LIVELLO", livello);
                    i.putExtra("SCORE", numScoreRandom);
                    startActivityForResult(i, 0);
                    countDownRelease();
                    onBackPressed();

                }else if (categoria.equalsIgnoreCase("Indovinelli")) {
                    i = new Intent(getApplicationContext(), IndovinelliActivity.class);
                    i.putExtra("LIVELLO", livello);
                    i.putExtra("SCORE", numScoreRandom);
                    startActivityForResult(i, 0);
                    countDownRelease();
                    onBackPressed();

                }




                onBackPressed();


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


    public void updateCountDownText() {
        seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        //int milliseconds = (int) (mTimeLeftInMillis);

        String timeLeftFormatted = String.format(Locale.getDefault(), "%01d", seconds);

        count_down.setText(timeLeftFormatted);
    }

    @Override
    protected void onResume() {

        /*DISTINZIONE TRA I VARI RESUME*/
        if (countDownIniziato) {
            resumeStartCountDown = true;
            /*resume activity restart count down timer e suono*/
            startTimer();
            startCountDown();


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
        /*rilacia risors del player*/

        countDownRelease();


        // Toast.makeText(CountDownActivity.this, "Stop", Toast.LENGTH_LONG).show();
        super.onStop();

    }

    /**
     *
     */
    public void startCountDown() {
        /**suoni****/

        if (suoni) {
            mpCountDown = MediaPlayer.create(this, getResources().getIdentifier("count_down", "raw", getPackageName()));
            mpCountDown = MediaPlayer.create(this, R.raw.count_down);
            mpCountDown.start();
            mpCountDown.setVolume(100.0f, 100.0f);
        }

    }

    /**
     * rilascia la risorsa se l'impostazione suono è attiva
     */
    public void countDownRelease(){
        if(suoni){
            mpCountDown.release();
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

}
