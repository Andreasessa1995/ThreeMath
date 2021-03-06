package com.example.threemath;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class RisultatiActivity extends AppCompatActivity {

    static final int PUNTEGGIOMAX = 5000;

    static final int NUMRISPMAX = 2000;

    static final int NUMRISTOTPMAX = 5000;


    /*---------variabili risposte ------*/
    int numRispEsatte = 0;
    int numRispErrate = 0;


    int tempoSecondiImpiegati = 0;
    int tempoMinutiImpiegati = 0;


    long tempoQuesitoMillis = 0;
    long tempoRestanteMillis = 0;
    long tempoImpiegatoQuesitoMillis = 0;

    TextView textRisposteErrate;
    TextView textRisposteCorrette;
    TextView textTempo;
    TextView textScores;

    String categoria = "";

    int livello = 1;


    /**
     * suoni
     **/
    MediaPlayer mpBat;

    /**
     * gestione vibrazione
     */
    Vibrator vibrator;
    /*vib 100 millise riposo 1000 millsec, vir*/
    long[] pattern = {0, 100, 1000};

    /**
     * IMPOSTAZIONI
     */
    GestoreFile gf = new GestoreFile();
    Boolean suoni = true;
    Boolean vibrazione = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_risultati);

        checkImpostazioni();

        /**valori passati dall'activity precedente quiz **/
        Intent i = getIntent();
        numRispEsatte = i.getIntExtra("CORRETTE", numRispEsatte);
        numRispErrate = i.getIntExtra("ERRATE", numRispErrate);
        categoria = i.getStringExtra("CATEGORIA");
        livello = i.getIntExtra("LIVELLO", livello);


        /**valori passati dall'activity precedente time **/

        tempoQuesitoMillis = i.getLongExtra("TIMEQUESITO", tempoQuesitoMillis);
        tempoRestanteMillis = i.getLongExtra("TIMERESTANTEQUESITO", tempoRestanteMillis);


        Log log = null;


        //calcolaTempoImpiegatoV1();
        calcolaTempoImpiegatoV2();
        /*calcola punteggio e salvatggio del punteggio*/
        int punteggio = 0;
        punteggio = calcolaPunteggio(numRispEsatte, numRispErrate);
        aggiornaPunteggio(getApplicationContext(), punteggio, categoria);


        textScores = (TextView) findViewById(R.id.scores);
        textRisposteCorrette = (TextView) findViewById(R.id.testoRisultatiCorretti);
        textRisposteErrate = (TextView) findViewById(R.id.testoRisultatiErrati);
        textTempo = (TextView) findViewById(R.id.textTempo);


        /*visualizzazione delle statistiche*/
        textScores.setText("" + punteggio);
        textRisposteCorrette.setText("" + numRispEsatte);
        textRisposteErrate.setText("" + numRispErrate);
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", tempoMinutiImpiegati, tempoSecondiImpiegati);
        textTempo.setText(timeLeftFormatted);


        /*salvataggio num risposte esatte e errate totali*/

        aggiornaNumeroRispsote(getApplicationContext(), numRispEsatte, numRispErrate);

        aggiornaNumeroRisposteCategoria(getApplicationContext(), numRispEsatte, numRispErrate, categoria);


    }

    /**
     * ritorna alla home
     *
     * @param v
     */
    public void onClickHome(View v) {


        startBattuta();

        if (vibrazione) {/*imposazione vibtazione*/
            if (isHaveVibrate()) {
                vibrator.vibrate(pattern, -1); // does not repeat
                //vibrator.vibrate(pattern,  0); // repeats forever
            }
        }

        /*new intent*/
        Intent intent = new Intent();
        //Intent intent =getIntent();

        intent.setClass(getApplicationContext(), HomeActivity.class);
        startActivityForResult(intent, 0);

        onBackPressed();
    }

    /**
     * calcola il tempo impiegato per il quiz o quesito r
     */
    private void calcolaTempoImpiegatoV2() {

        tempoImpiegatoQuesitoMillis = tempoQuesitoMillis - tempoRestanteMillis;
        tempoSecondiImpiegati = (int) ((tempoImpiegatoQuesitoMillis / 1000) % 60);
        tempoMinutiImpiegati = (int) ((tempoImpiegatoQuesitoMillis / 1000) / 60);

    }

    /**
     * calcola il punteggio in base alle risposte corrette
     * e in base al tempo impiegato e il restante.
     * più il tempo restante è alto più il punteggio è alto (  più veloce nelle risposte )
     *
     * @param numRispEsatte
     * @param numRispErrate
     * @return
     */
    private int calcolaPunteggio(int numRispEsatte, int numRispErrate) {

        //int numeroRisposte = numRispErrate+numRispEsatte;

        int punti = 0;
        Log log = null;
        //log.d("DEBUG", "tempo che mi arriva restante  = " + tempoRestanteMillis);

        if (tempoRestanteMillis >= 1000) {/*necessario quando il tempo finisce e si è risposto non tutte le domande*/
            if (livello == 1) {
                punti = (int) (numRispEsatte * (tempoRestanteMillis / 1000));

            } else if (livello == 2) {
                punti = (int) (  (numRispEsatte * (tempoRestanteMillis / 1000) )  * 1.7f);


            } else if (livello == 3) {
                punti = (int) (  (numRispEsatte * (tempoRestanteMillis / 1000) )  * 2.3f);

            }

        } else {
            punti = numRispEsatte * 1;/*evita di avere un punteggio 0 se finisce il tempo*/
        }


        return punti;

    }


    /**
     * salva il punteggio dello score aggiornandolo, sommandolo
     * al precedente score
     *
     * @param context
     * @param punteggio
     */
    public void aggiornaPunteggio(Context context, int punteggio, String categoria) {


        //  GestoreFile gf = new GestoreFile();
        Log log = null;
        //log.d("DEBUG", "Salvo questo nuovo punteggio il vecchio era= = " + gf.caricaScoresAddizioni(context));
        int punteggioTemp = 0;
        punteggioTemp = punteggio + gf.caricaScores(context, categoria);

        //punteggioTemp = 515; test

        if (punteggioTemp <= PUNTEGGIOMAX) {
            // log.d("DEBUG", "Salvo questo nuovo punteggio= = " + punteggioTemp);

            gf.salvaScores(context, punteggioTemp, categoria);
        } else {

            Toast.makeText(RisultatiActivity.this, "Attenzione hai rggiunto il punteggio massimo di score ( " + PUNTEGGIOMAX + " )" + "ne hai totalizzato = " +
                    punteggioTemp, Toast.LENGTH_LONG).show();


        }


    }


    /**
     * Salva il numero di risposte corrette ed errate totali
     *
     * @param context
     * @param corrette
     * @param errate
     */
    public void aggiornaNumeroRispsote(Context context, int corrette, int errate) {

        corrette = corrette + gf.caricaNumRisposteCorrette(context);
        errate = errate + gf.caricaNumRisposteErrate(context);

        if ((corrette <= NUMRISTOTPMAX) && (errate <= NUMRISTOTPMAX)) {
            gf.salvaRisposteCorrette(context, corrette);
            gf.salvaRisposteErrate(context, errate);
        } else {

            Toast.makeText(RisultatiActivity.this, "Attenzione hai rggiunto il numero massimo di domande corrette/errate ( " + NUMRISTOTPMAX + " )" + "ne hai totalizzato = " +
                    corrette + "Errate " + errate, Toast.LENGTH_LONG).show();


        }


    }


    /**
     * salva il numero di rispose corrette ed errate per categoria
     *
     * @param context
     * @param corrette
     * @param errate
     * @param categoria
     */
    public void aggiornaNumeroRisposteCategoria(Context context, int corrette, int errate, String categoria) {


        corrette = corrette + gf.caricaNumRisposteCorretteCategoria(context, categoria);
        errate = errate + gf.caricaNumRisposteErrateCategoria(context, categoria);


        if ((corrette <= NUMRISPMAX) && (errate <= NUMRISPMAX)) {
            /*salvataggio num risposte esatte per categoria*/
            gf.salvaRisposteErrateCategoria(getApplicationContext(), errate, categoria);
            gf.salvaRisposteCorretteCategoria(getApplicationContext(), corrette, categoria);

        } else {

            Toast.makeText(RisultatiActivity.this, "Attenzione hai rggiunto il numero massimo di domande corrette/errate ( " + NUMRISPMAX + " )" + "ne hai totalizzato = " +
                    corrette + "Errate " + errate, Toast.LENGTH_LONG).show();


        }


    }


    /**
     * player campanella e bat controlla inoltre s el'impostazione del suono è attiva
     */


    public void startBattuta() {
        if (suoni) {
            mpBat = MediaPlayer.create(this, getResources().getIdentifier("bat", "raw", getPackageName()));
            mpBat = MediaPlayer.create(this, R.raw.bat);
            mpBat.start();
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
     * rilascia la risorsa se nelle impostzioni è attiva
     */
    public void releaseResourcesBattuta() {
        if (suoni) {
            mpBat.release();
        }

    }

    /**
     * conrolla le impostazioni del suono e delle vibrazioni
     */
    public void checkImpostazioni() {

        if ("si".equalsIgnoreCase(gf.caricaImpostazioni(getApplicationContext(), "Suoni"))) {
            suoni = true;
        } else if ("no".equalsIgnoreCase(gf.caricaImpostazioni(getApplicationContext(), "Suoni"))) {

            suoni = false;
        }

        if ("si".equalsIgnoreCase(gf.caricaImpostazioni(getApplicationContext(), "Vibrazione"))) {
            vibrazione = true;
        } else if ("no".equalsIgnoreCase(gf.caricaImpostazioni(getApplicationContext(), "Suoni"))) {
            vibrazione = false;
        }

    }
}
