package com.example.threemath;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private Animation anim=null;



    private Button bCategoria1;
    private Button bCategoria2;
    private Button bCategoria3;
    private Button bCategoria4;


    /**
     * suoni
     **/
    MediaPlayer mpBat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       // anim = AnimationUtils.loadAnimation(getApplicationContext(),R.transition.animazione);





    }




    public void onClickCategoria(View v) {

        bCategoria1 = (Button) findViewById(R.id.bottoneCategoriaDomanda1);
        bCategoria2 = (Button) findViewById(R.id.bottoneCategoriaDomanda2);
        bCategoria3 = (Button) findViewById(R.id.bottoneCategoriaDomanda3);
        bCategoria4 = (Button) findViewById(R.id.bottoneCategoriaDomanda4);

        if (bCategoria1.isPressed()) {

            startBattuta();
            /*new intent*/
            // Intent intent = new Intent();
            // intent.setClass(getApplicationContext(), AddizioniActivity.class);
            Intent intent = new Intent(getApplicationContext(), CountDownActivity.class);
            startActivityForResult(intent, 0);

           // releaseResourcesBattuta();


        }
        if (bCategoria2.isPressed()) {
            /*apri sott*/

        }
        if (bCategoria3.isPressed()) {
            /*apri divis*/

        }
        if (bCategoria4.isPressed()) {
            /*apri molti*/

        }


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
