package com.example.threemath;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LivelloActivity extends AppCompatActivity {

    private Animation anim = null;


    private Button bLivello1;
    private Button bLivello2;
    private Button bLivello3;
    private Button bLivello4;


    String categoria = "";
    int livello = 1;


    /**
     * suoni
     **/
    MediaPlayer mpBat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livelli);

        Intent intent = getIntent();
        categoria = intent.getStringExtra("CATEGORIA");
        // Log log = null;
        //log.d("DEBUG", "CATEGORIA CATEGORIA CATEGORIA CATEGORIA 2 2 2 2 = " +categoria );


        // anim = AnimationUtils.loadAnimation(getApplicationContext(),R.transition.animazione);


    }


    public void onClickLivello(View v) {

        bLivello1 = (Button) findViewById(R.id.bottoneLivello1);
        bLivello2 = (Button) findViewById(R.id.bottoneLivello2);
        bLivello3 = (Button) findViewById(R.id.bottoneLivello3);
        bLivello4 = (Button) findViewById(R.id.bottoneLivello4);

        if (bLivello1.isPressed()) {

            // bCategoria1.setClickable(false);

            startBattuta();


            Intent i = new Intent(getApplicationContext(), CountDownActivity.class);
            i.putExtra("CATEGORIA", categoria);
            i.putExtra("LIVELLO", livello);
            startActivityForResult(i, 0);

            // releaseResourcesBattuta();


        } else if (bLivello2.isPressed()) {

            Intent i = new Intent(getApplicationContext(), CountDownActivity.class);
            i.putExtra("CATEGORIA", categoria);
            i.putExtra("LIVELLO", 2);
            startActivityForResult(i, 0);
            /*apri sott*/

        } else if (bLivello3.isPressed()) {
            Intent i = new Intent(getApplicationContext(), CountDownActivity.class);
            i.putExtra("CATEGORIA", categoria);
            i.putExtra("LIVELLO", 3);
            startActivityForResult(i, 0);
            /*apri divis*/

        } else if (bLivello4.isPressed()) {
            Intent i = new Intent(getApplicationContext(), CountDownActivity.class);
            i.putExtra("CATEGORIA", categoria);
            i.putExtra("LIVELLO", 4);
            startActivityForResult(i, 0);
            /*apri molti*/

        }


    }

    @Override
    protected void onResume() {

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
