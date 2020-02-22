package com.example.threemath;

import android.content.Context;
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
        int punteggio = 0;

        punteggio= calcolaPunteggio(numRispEsatte,numRispErrate);

        aggiornaPunteggio(getApplicationContext(),punteggio);




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
     * calcola il tempo impiegato per il quiz o quesito r
     */
    private  void calcolaTempoImpiegatoV2(){

        tempoImpiegatoQuesitoMillis = tempoQuesitoMillis-tempoRestanteMillis;
        tempoSecondiImpiegati = (int ) ((tempoImpiegatoQuesitoMillis/1000)%60);
        tempoMinutiImpiegati = (int)(( tempoImpiegatoQuesitoMillis/ 1000) / 60);

    }

    /**
     * calcola il punteggio in base alle risposte corrette
     * e in base al tempo impiegato e il restante.
     * più il tempo restante è alto più il punteggio è alto (  più veloce nelle risposte )
     * @param numRispEsatte
     * @param numRispErrate
     * @return
     */
    private int calcolaPunteggio(int numRispEsatte,int numRispErrate){

        int numeroRisposte = numRispErrate+numRispEsatte;

        int punti = 0;
        punti = (int) (numRispEsatte*(tempoRestanteMillis/1000));


        return punti;

    }

    /**
     * salva il punteggio dello score aggiornandolo, sommandolo
     * al precedente score
     * @param context
     * @param punteggio
     */
    public void aggiornaPunteggio(Context context, int punteggio){
        GestoreFile gf = new GestoreFile();
        Log log =null;
        //log.d("DEBUG", "Salvo questo nuovo punteggio il vecchio era= = " + gf.caricaScoresAddizioni(context));
        int punteggioTemp = 0;
        punteggioTemp = punteggio + gf.caricaScoresAddizioni(context);

       // log.d("DEBUG", "Salvo questo nuovo punteggio= = " + punteggioTemp);

        gf.salvaScoresAddizioni(context,punteggioTemp);
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
