package com.example.threemath;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class RisultatiActivity extends AppCompatActivity {
    int numRispEsatte =0;
    int numRispErrate = 0;
    int minutes = 0;
    int seconds = 0;
    int tempoSecondiImpiegati = 0;
    int tempoMinutiImpiegati = 0;
    long tempoSecondiDisponibili=0;

    TextView textRisposteErrate ;
    TextView textRisposteCorrette ;
    TextView textTempo ;


    /**
     * suoni
     **/
    MediaPlayer mpBat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.risultati_activity);

        /**valori passati dall'activity precedente**/
        Intent i = getIntent();
        numRispEsatte=i.getIntExtra("CORRETTE",numRispEsatte);
        numRispErrate=i.getIntExtra("ERRATE",numRispErrate);
        minutes =i.getIntExtra("TIMEMINUTI",minutes);
        seconds =i.getIntExtra("TIMESECONDI",seconds);
        tempoSecondiDisponibili=i.getLongExtra("TIMEQUESITO",tempoSecondiDisponibili);
        tempoSecondiDisponibili = (( tempoSecondiDisponibili/ 1000) % 60);


        tempoSecondiImpiegati = (int) (tempoSecondiDisponibili-seconds);
        tempoMinutiImpiegati = 0-minutes;



        textRisposteCorrette = (TextView) findViewById(R.id.testoRisultatiCorretti);
        textRisposteErrate = (TextView) findViewById(R.id.testoRisultatiErrati);
        textTempo = (TextView) findViewById(R.id.textTempo);

        Log log = null;
        //log.d("DEBUG", "corrette = " +numRispEsatte);
        //log.d("DEBUG", "errate= " + numRispErrate);
        log.d("DEBUG", "corrette minuti minuti = " +minutes);
        log.d("DEBUG", "errate secondi secondi= " + seconds);


        textRisposteCorrette.setText(""+numRispEsatte);
        textRisposteErrate.setText(""+numRispErrate);
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", tempoMinutiImpiegati,  tempoSecondiImpiegati);

        textTempo.setText(timeLeftFormatted);


    }

    public void onClickHome(View v){

        startBattuta();
        /*new intent*/
        Intent intent = new Intent();
        //Intent intent =getIntent();

        intent.setClass(getApplicationContext(), HomeActivity.class);
        startActivityForResult(intent, 0);
       // releaseResourcesBattuta();
        mpBat.release();

        onBackPressed();
    }

    /**
     * player campanella e bat
     */


    public void startBattuta() {
        mpBat= MediaPlayer.create(this, getResources().getIdentifier("bat", "raw", getPackageName()));
        mpBat = MediaPlayer.create(this, R.raw.bat);
        mpBat.start();
    }

    public void releaseResourcesBattuta() {
        mpBat.release();
    }
}
