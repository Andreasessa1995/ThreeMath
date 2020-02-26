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

    private Animation anim=null;


    /*bottone quiz */
    private Button bCategoriaDomanda1;
    private Button bCategoriaDomanda2;
    private Button bCategoriaDomanda3;
    private Button bCategoriaDomanda4;
    /*bottoni indovinelli, stat,impo,*/
    private Button bCategoriaDomanda5;
    private Button bCategoria6;
    private Button bCategoria7;

    String categoria ="";
    /**gestione vibrazione*/
    Vibrator vibrator;
    /*vib 100 millise riposo 1000 millsec, vir*/
    long[] pattern = {0, 100, 1000 };



    /**
     * suoni
     **/
    MediaPlayer mpBat;

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







    }

    /**
     *
     * @return
     */
    public  boolean isHaveVibrate(){
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {

            return true;
        }else    { return false;}

    }




    public void onClickCategoria(View v) {



        Log log = null;


        if (bCategoriaDomanda1.isPressed()) {

            categoria = bCategoriaDomanda1.getText().toString();
            //log.d("DEBUG", "CATEGORIA CATEGORIA CATEGORIA CATEGORIA = " +categoria );
            /*VIBRAZONE*/

            if (isHaveVibrate( ) ){
                vibrator.vibrate(pattern, -1); // does not repeat
                //vibrator.vibrate(pattern,  0); // repeats forever
            }

             bCategoriaDomanda1.setClickable(false);

           // startBattuta();
            /*new intent*/
            // Intent intent = new Intent();
            // intent.setClass(getApplicationContext(), AddizioniActivity.class);
            Intent intent = new Intent(getApplicationContext(), LivelloActivity.class);
            intent.putExtra("CATEGORIA",categoria);

            startActivityForResult(intent, 0);


            // releaseResourcesBattuta();


        }else if (bCategoriaDomanda2.isPressed()) {

            categoria = bCategoriaDomanda2.getText().toString();
            bCategoriaDomanda2.setClickable(false);

            Intent intent = new Intent(getApplicationContext(), LivelloActivity.class);
            intent.putExtra("CATEGORIA",categoria);

            startActivityForResult(intent, 0);

            /*apri sott*/

        } else if (bCategoriaDomanda3.isPressed()) {
            bCategoriaDomanda3.setClickable(false);


            /*apri divis*/

        }else if (bCategoriaDomanda4.isPressed()) {
            bCategoriaDomanda4.setClickable(false);

            /*apri molti*/

        }else if (bCategoriaDomanda5.isPressed()) {
            bCategoriaDomanda5.setClickable(false);

            Intent intent = new Intent(getApplicationContext(), IndovinelliActivity.class);
            intent.putExtra("CATEGORIA",categoria);

            startActivityForResult(intent, 0);

            /*apri molti*/

        }else if (bCategoria6.isPressed()) {
            bCategoria6.setClickable(false);

            Intent intent = new Intent(getApplicationContext(), StatisticheActivity.class);
            intent.putExtra("CATEGORIA",categoria);

            startActivityForResult(intent, 0);

            /*apri molti*/

        }else if (bCategoria7.isPressed()) {
            bCategoria7.setClickable(false);

            Intent intent = new Intent(getApplicationContext(), ImpostazioniActivity.class);
            intent.putExtra("CATEGORIA",categoria);

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
        super.onResume();
    }

    /**
     * player campanella e bat
     */
    public void startBattuta() {
        mpBat = MediaPlayer.create(this, getResources().getIdentifier("bat", "raw", getPackageName()));
        mpBat = MediaPlayer.create(this, R.raw.bat);
        mpBat.start();
    }

    public void releaseResourcesBattuta() {

        mpBat.release();
    }


}
