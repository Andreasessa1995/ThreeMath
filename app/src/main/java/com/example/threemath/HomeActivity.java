package com.example.threemath;


import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private Animation anim = null;


    /*bottone quiz */
    private Button bCategoriaDomanda1;
    private Button bCategoriaDomanda2;
    private Button bCategoriaDomanda3;
    private Button bCategoriaDomanda4;
    /*bottoni indovinelli, stat,impo,*/
    private Button bCategoriaDomanda5;
    private Button bCategoria6;
    private Button bCategoria7;

    String categoria = "";
    /**
     * gestione vibrazione
     */
    Vibrator vibrator;
    /*vib 100 millise riposo 1000 millsec, vir*/
    long[] pattern = {0, 100, 1000};


    /**
     * suoni
     **/
    MediaPlayer mpBat;


    GestoreFile gf = new GestoreFile();
    Boolean suoni = true;
    Boolean vibrazione = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bCategoriaDomanda1 = (Button) findViewById(R.id.bottoneCategoriaDomanda1);
        bCategoriaDomanda2 = (Button) findViewById(R.id.bottoneCategoriaDomanda2);
        bCategoriaDomanda3 = (Button) findViewById(R.id.bottoneCategoriaDomanda3);
        bCategoriaDomanda4 = (Button) findViewById(R.id.bottoneCategoriaDomanda4);
        bCategoriaDomanda5 = (Button) findViewById(R.id.bottoneCategoriaDomanda5);


        /*bottoni categoria statistiche e impostazioni*/
        bCategoria6 = (Button) findViewById(R.id.bottoneCategoria6);
        bCategoria7 = (Button) findViewById(R.id.bottoneCategoria7);

        checkImpostazioni();


        Log log = null;


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


    /** in base alla categoria scelta apre l'activity livello
     * la categoria la prende dal testp del bottone
     * @param v
     */
    public void onClickCategoria(View v) {


        Log log = null;

        /*categoria addizione*/

        if (bCategoriaDomanda1.isPressed()) { /**categoria addizioni*/

            categoria = bCategoriaDomanda1.getText().toString();
            //log.d("DEBUG", "CATEGORIA CATEGORIA CATEGORIA CATEGORIA = " +categoria );


            /*VIBRAZONE e SUONI*/

            if (vibrazione) {
                if (isHaveVibrate()) {
                    vibrator.vibrate(pattern, -1); // does not repeat
                    //vibrator.vibrate(pattern,  0); // repeats forever
                }
            }

            startBattuta();


            bCategoriaDomanda1.setClickable(false);


            /*new intent*/
            // Intent intent = new Intent();
            // intent.setClass(getApplicationContext(), AddizioniActivity.class);
            Intent intent = new Intent(getApplicationContext(), LivelloActivity.class);
            intent.putExtra("CATEGORIA", categoria);

            startActivityForResult(intent, 0);


            // releaseResourcesBattuta();


        } else if (bCategoriaDomanda2.isPressed()) {            /**categoria sottrazione*/


            categoria = bCategoriaDomanda2.getText().toString();
            bCategoriaDomanda2.setClickable(false);

            /*VIBRAZONE e SUONI*/

            if (vibrazione) {
                if (isHaveVibrate()) {
                    vibrator.vibrate(pattern, -1); // does not repeat
                    //vibrator.vibrate(pattern,  0); // repeats forever
                }
            }

            startBattuta();


            Intent intent = new Intent(getApplicationContext(), LivelloActivity.class);
            intent.putExtra("CATEGORIA", categoria);

            startActivityForResult(intent, 0);



            /*apri sott*/

        } else if (bCategoriaDomanda3.isPressed()) {           /**categoria moltiplicazione*/

            categoria = bCategoriaDomanda3.getText().toString();
            bCategoriaDomanda3.setClickable(false);

            /*VIBRAZONE e SUONI*/

            if (vibrazione) {
                if (isHaveVibrate()) {
                    vibrator.vibrate(pattern, -1); // does not repeat
                    //vibrator.vibrate(pattern,  0); // repeats forever
                }
            }

            startBattuta();

            Intent intent = new Intent(getApplicationContext(), LivelloActivity.class);
            intent.putExtra("CATEGORIA", categoria);

            startActivityForResult(intent, 0);


            /*apri divis*/

        } else if (bCategoriaDomanda4.isPressed()) {   /**categoria divisioni*/
            categoria = bCategoriaDomanda4.getText().toString();
            bCategoriaDomanda4.setClickable(false);

            /*VIBRAZONE e SUONI*/

            if (vibrazione) {
                if (isHaveVibrate()) {
                    vibrator.vibrate(pattern, -1); // does not repeat
                    //vibrator.vibrate(pattern,  0); // repeats forever
                }
            }

            startBattuta();

            /*apri molti*/

        } else if (bCategoriaDomanda5.isPressed()) {   /**categoria indovinelli*/
            categoria = bCategoriaDomanda5.getText().toString();

            bCategoriaDomanda5.setClickable(false);

            /*VIBRAZONE e SUONI*/

            if (vibrazione) {
                if (isHaveVibrate()) {
                    vibrator.vibrate(pattern, -1); // does not repeat
                    //vibrator.vibrate(pattern,  0); // repeats forever
                }
            }

            startBattuta();


            Intent intent = new Intent(getApplicationContext(), LivelloActivity.class);
            intent.putExtra("CATEGORIA", categoria);

            startActivityForResult(intent, 0);

            /*apri molti*/

        }

        /**--------------------statistiche ed impostazioni-------*/
        else if (bCategoria6.isPressed()) {
            bCategoria6.setClickable(false);

            /*VIBRAZONE e SUONI*/

            if (vibrazione) {
                if (isHaveVibrate()) {
                    vibrator.vibrate(pattern, -1); // does not repeat
                    //vibrator.vibrate(pattern,  0); // repeats forever
                }
            }

            startBattuta();


            Intent intent = new Intent(getApplicationContext(), StatisticheActivityConLista.class);
            intent.putExtra("CATEGORIA", categoria);

            startActivityForResult(intent, 0);

            /*apri molti*/

        } else if (bCategoria7.isPressed()) {
            bCategoria7.setClickable(false);

            /*VIBRAZONE e SUONI*/

            if (vibrazione) {
                if (isHaveVibrate()) {
                    vibrator.vibrate(pattern, -1); // does not repeat
                    //vibrator.vibrate(pattern,  0); // repeats forever
                }
            }

            startBattuta();


            Intent intent = new Intent(getApplicationContext(), ImpostazioniActivity.class);
            intent.putExtra("CATEGORIA", categoria);

            startActivityForResult(intent, 0);

            /*apri molti*/

        }


    }

    @Override
    protected void onResume() {
        bCategoriaDomanda1.setClickable(true);
        bCategoriaDomanda2.setClickable(true);
        bCategoriaDomanda3.setClickable(true);
        bCategoriaDomanda4.setClickable(true);
        bCategoriaDomanda5.setClickable(true);
        bCategoria6.setClickable(true);
        bCategoria7.setClickable(true);
        checkImpostazioni();
        super.onResume();
    }

    /**
     * player battuta
     */
    public void startBattuta() {
        /**suoni****/

        if (suoni) { /*impostazione del suono*/
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
     * controlla le impostazioni del suono e delle vibrazioni
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
