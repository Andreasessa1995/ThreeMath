package com.example.threemath;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
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
    private Button bLivello4;

    TextView textScore;


    String categoria = "";
    int livello = 1;

    GestoreFile gf = new GestoreFile();

    int scoreAddizioni = 0;


    /**
     * suoni
     **/
    MediaPlayer mpBat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livelli);

       // gf.azzeraScoreAddizioni(getApplicationContext());


        Intent intent = getIntent();
        categoria = intent.getStringExtra("CATEGORIA");


        bLivello1 = (Button) findViewById(R.id.bottoneLivello1);
        bLivello2 = (Button) findViewById(R.id.bottoneLivello2);
        bLivello3 = (Button) findViewById(R.id.bottoneLivello3);
        bLivello4 = (Button) findViewById(R.id.bottoneLivello4);

        textScore = (TextView) findViewById(R.id.scores);


        scoreAddizioni = gf.caricaScoresAddizioni(getApplicationContext());
        textScore.setText("" + scoreAddizioni);
        checkLV(scoreAddizioni);


        // Log log = null;
        //log.d("DEBUG", "CATEGORIA CATEGORIA CATEGORIA CATEGORIA 2 2 2 2 = " +categoria );


        // anim = AnimationUtils.loadAnimation(getApplicationContext(),R.transition.animazione);


    }

    /**
     * @param v
     */
    public void onClickLivello(View v) {


        if (bLivello1.isPressed()) {
            /*evitare di premere due volte sul bottone*/
            bLivello1.setClickable(false);

            startBattuta();


            Intent i = new Intent(getApplicationContext(), CountDownActivity.class);
            i.putExtra("CATEGORIA", categoria);
            i.putExtra("LIVELLO", livello);
            startActivityForResult(i, 0);

            // releaseResourcesBattuta();


        } else if (bLivello2.isPressed()) {
            bLivello2.setClickable(false);

            Intent i = new Intent(getApplicationContext(), CountDownActivity.class);
            i.putExtra("CATEGORIA", categoria);
            i.putExtra("LIVELLO", 2);
            startActivityForResult(i, 0);
            /*apri sott*/

        } else if (bLivello3.isPressed()) {
            bLivello3.setClickable(false);

            Intent i = new Intent(getApplicationContext(), CountDownActivity.class);
            i.putExtra("CATEGORIA", categoria);
            i.putExtra("LIVELLO", 3);
            startActivityForResult(i, 0);
            /*apri divis*/

        } else if (bLivello4.isPressed()) {
            bLivello3.setClickable(false);
            Intent i = new Intent(getApplicationContext(), CountDownActivity.class);
            i.putExtra("CATEGORIA", categoria);
            i.putExtra("LIVELLO", 4);
            startActivityForResult(i, 0);
            /*apri molti*/

        }


    }

    /**
     * @param scoreAddizioni
     */
    private void checkLV(int scoreAddizioni) {
        if ((scoreAddizioni > 10) && (scoreAddizioni < 150)) {
            bLivello2.setClickable(false);
            bLivello3.setClickable(false);
            bLivello4.setClickable(false);
        } else if (scoreAddizioni > 250) {

            bLivello2.setBackground(getDrawable(R.drawable.categoria));
            bLivello3.setClickable(false);
            bLivello4.setClickable(false);
        }
    }

    /*---------------------------gestione activity state lifecycle------------------*/

    @Override
    protected void onResume() {
        bLivello1.setClickable(true);
        bLivello2.setClickable(true);
        bLivello3.setClickable(true);
        bLivello4.setClickable(true);
        checkLV(scoreAddizioni);


        super.onResume();
    }
    /*---------------------------gestione player-------------------*/

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
