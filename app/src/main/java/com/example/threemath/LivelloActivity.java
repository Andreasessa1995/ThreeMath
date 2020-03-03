package com.example.threemath;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LivelloActivity extends AppCompatActivity {

    private Animation anim = null;


    private Button bLivello1;
    private Button bLivello2;
    private Button bLivello3;

    TextView textScore;


    String categoria = "";
    int livello = 1;

    GestoreFile gf = new GestoreFile();

    int score = 0;


    /**
     * suoni
     **/
    MediaPlayer mpBat;

    /**
     * Boolean
     */
    Boolean suoni = true;
    Boolean vibrazione = true;

    /**
     * gestione vibrazione
     */
    Vibrator vibrator;
    /*vib 100 millise riposo 1000 millsec, vir*/
    long[] pattern = {0, 100, 1000};
    long[] pattern2 = {0, 100, 100, 100, 100};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livelli);


        Intent intent = getIntent();
        categoria = intent.getStringExtra("CATEGORIA");

       // gf.azzeraScore(getApplicationContext(), categoria);
      //  gf.azzeraNumRisposte(getApplicationContext());
    //    gf.azzeraNumRisposteCategorie(getApplicationContext());

        /*Bottoni livelli*/

        bLivello1 = (Button) findViewById(R.id.bottoneLivello1);
        bLivello2 = (Button) findViewById(R.id.bottoneLivello2);
        bLivello3 = (Button) findViewById(R.id.bottoneLivello3);
        textScore = (TextView) findViewById(R.id.scores);

        checkImpostazioni();

        caricaScore();


        // Log log = null;
        //log.d("DEBUG", "CATEGORIA CATEGORIA CATEGORIA CATEGORIA 2 2 2 2 = " +categoria );


        // anim = AnimationUtils.loadAnimation(getApplicationContext(),R.transition.animazione);


    }




    /**
     * carica gli score della categoria scelta
     */
    public void caricaScore(){

        /**caricamento score della categoria scelta */

        if (categoria.equalsIgnoreCase("addizioni")) {
            score = gf.caricaScores(getApplicationContext(), "Addizioni");
            textScore.setText("" + score);
            checkLV(score);
        } else if (categoria.equalsIgnoreCase("sottrazioni")) {
            score = gf.caricaScores(getApplicationContext(), "Sottrazioni");
            textScore.setText("" + score);
            checkLV(score);
        } else if (categoria.equalsIgnoreCase("moltiplicazioni")) {
            score = gf.caricaScores(getApplicationContext(), "Moltiplicazioni");
            textScore.setText("" + score);
            checkLV(score);
        } else if (categoria.equalsIgnoreCase("divisioni")) {
            score = gf.caricaScores(getApplicationContext(), "Divisioni");
            textScore.setText("" + score);
            checkLV(score);
        }


    }

    /**
     * @param v
     */
    public void onClickLivello(View v) {


        /*livello principiante*/

        if (bLivello1.isPressed()) {
            /*evitare di premere due volte sul bottone*/
            bLivello1.setClickable(false);

            /*VIBRAZONE e SUONI*/

            if (vibrazione) {
                if (isHaveVibrate()) {
                    vibrator.vibrate(pattern, -1); // does not repeat
                    //vibrator.vibrate(pattern,  0); // repeats forever
                }
            }

            startBattuta();


            Intent i = new Intent(getApplicationContext(), CountDownActivity.class);
            i.putExtra("CATEGORIA", categoria);
            i.putExtra("LIVELLO", livello);
            i.putExtra("SCORE", score);
            startActivityForResult(i, 0);

            // releaseResourcesBattuta();


        } else if (bLivello2.isPressed()) {          /*livello normale*/

            /*evitare di premere due volte sul bottone*/
            bLivello2.setClickable(false);
            /*VIBRAZONE e SUONI*/

            if (vibrazione) {
                if (isHaveVibrate()) {
                    vibrator.vibrate(pattern, -1); // does not repeat
                    //vibrator.vibrate(pattern,  0); // repeats forever
                }
            }
            startBattuta();


            Intent i = new Intent(getApplicationContext(), CountDownActivity.class);
            i.putExtra("CATEGORIA", categoria);
            i.putExtra("LIVELLO", 2);
            i.putExtra("SCORE", score);
            startActivityForResult(i, 0);
            /*apri sott*/

        } else if (bLivello3.isPressed()) {          /*livello esperto/
            /*evitare di premere due volte sul bottone*/
            bLivello3.setClickable(false);

            /*VIBRAZONE e SUONI*/

            if (vibrazione) {
                if (isHaveVibrate()) {
                    vibrator.vibrate(pattern, -1); // does not repeat
                    //vibrator.vibrate(pattern,  0); // repeats forever
                }
            }

            startBattuta();


            Intent i = new Intent(getApplicationContext(), CountDownActivity.class);
            i.putExtra("CATEGORIA", categoria);
            i.putExtra("LIVELLO", 3);
            i.putExtra("SCORE", score);
            startActivityForResult(i, 0);


        }


    }

    /**
     * @param scoreCat
     */
    private void checkLV(int scoreCat) {


        if ((scoreCat >= 0) && (scoreCat < 1000)) {
            messageLV2();
            messageLV3();
        } else if ((scoreCat >= 1000) && (scoreCat < 2500)) {

            bLivello2.setBackground(getDrawable(R.drawable.categoria));
            messageLV3();
        } else if (scoreCat >= 2500) {

            bLivello2.setBackground(getDrawable(R.drawable.categoria));
            bLivello3.setBackground(getDrawable(R.drawable.categoria));

        }

    }


    /**
     * mostra il messaggio se si preme sul livello normale quando è ancora bloccato
     */
    protected void messageLV2() {
        bLivello2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(LivelloActivity.this);
                b.setTitle("Categoria NORMALE bloccata");
                b.setMessage("Non puoi scegliere questa categoria, per sbloccarla ti servono almeno 1000 punti");
                b.setIcon(getDrawable(R.drawable.lucchetto_chiuso));
                b.setPositiveButton("Ho capito", null);
                //b.setNegativeButton("", null);
                AlertDialog al = b.create();
                al.show();
                /*qui altrimenti nn va*/
                if (isHaveVibrate()) {
                    vibrator.vibrate(pattern2, -1); // does not repeat
                    //vibrator.vibrate(pattern,  0); // repeats forever
                }
            }
        });
    }

    /**
     * mostra il messaggio se si preme sul livello normale quando è ancora bloccato
     */
    protected void messageLV3() {
        bLivello3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(LivelloActivity.this);
                b.setTitle("Categoria  ESPERTO bloccata");
                b.setMessage("Non puoi scegliere questa categoria, per sbloccarla ti servono almeno 2500 punti");
                b.setIcon(getDrawable(R.drawable.lucchetto_chiuso));
                b.setPositiveButton("Ho capito", null);
                //b.setNegativeButton("", null);
                AlertDialog al = b.create();
                al.show();
                /*qui altrimenti nn va*/
                if (isHaveVibrate()) {
                    vibrator.vibrate(pattern2, -1); // does not repeat
                    //vibrator.vibrate(pattern,  0); // repeats forever
                }
            }
        });
    }

    /*---------------------------gestione activity state lifecycle------------------*/

    @Override
    protected void onResume() {
        /*perchevengono disabilitati quando si apre la nuova activity*/
        bLivello1.setClickable(true);
        bLivello2.setClickable(true);
        bLivello3.setClickable(true);
        checkLV(score);


        super.onResume();
    }
    /*---------------------------gestione player-------------------*/

    /**
     * player campanella e bat
     */
    public void startBattuta() {

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


}
