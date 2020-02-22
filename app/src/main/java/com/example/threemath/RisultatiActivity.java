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


    /*---------variabili risposte ------*/
    int numRispEsatte =0;
    int numRispErrate = 0;



    int tempoSecondiImpiegati = 0;
    int tempoMinutiImpiegati = 0;


    long tempoQuesitoMillis = 0;
    long tempoRestanteMillis = 0;
    long tempoImpiegatoQuesitoMillis = 0;

    TextView textRisposteErrate ;
    TextView textRisposteCorrette ;
    TextView textTempo ;
    TextView textScores;


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


        tempoQuesitoMillis =i.getLongExtra("TIMEQUESITO",tempoQuesitoMillis);

        tempoRestanteMillis= i.getLongExtra("TIMERESTANTEQUESITO",tempoQuesitoMillis);


        Log log = null;


        //calcolaTempoImpiegatoV1();
        calcolaTempoImpiegatoV2();
        int punteggio = calcolaPunteggio(numRispEsatte,numRispErrate);




        textScores = (TextView) findViewById(R.id.scores);
        textRisposteCorrette = (TextView) findViewById(R.id.testoRisultatiCorretti);
        textRisposteErrate = (TextView) findViewById(R.id.testoRisultatiErrati);
        textTempo = (TextView) findViewById(R.id.textTempo);



        textScores.setText(""+punteggio);
        textRisposteCorrette.setText(""+numRispEsatte);
        textRisposteErrate.setText(""+numRispErrate);
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", tempoMinutiImpiegati,  tempoSecondiImpiegati);

        textTempo.setText(timeLeftFormatted);


    }

    /**
     * ritorna alla home
     * @param v
     */
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
     * calcola il tempo impiegato per il quiz o quesito
     */
    private  void calcolaTempoImpiegatoV2(){

        tempoImpiegatoQuesitoMillis = tempoQuesitoMillis-tempoRestanteMillis;
        tempoSecondiImpiegati = (int ) ((tempoImpiegatoQuesitoMillis/1000)%60);
        tempoMinutiImpiegati = (int)(( tempoImpiegatoQuesitoMillis/ 1000) / 60);

    }

    private int calcolaPunteggio(int numRispEsatte,int numRispErrate){

        int numeroRisposte = numRispErrate+numRispEsatte;

        int punti = (int) (numRispEsatte*(tempoRestanteMillis/1000));


        return punti;

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
