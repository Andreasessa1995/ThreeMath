package com.example.threemath;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class StatisticheActivity extends AppCompatActivity {


    /**
     * TExt view Punteggi
     */

    TextView textPunteggioAddizioni;
    TextView textPunteggioSottrazioni;
    TextView textPunteggioMoltiplicazioni;
    TextView textPunteggioDivisioni;

    TextView textRisposteCorrette;
    TextView textRisposteErrate;

    TextView textPercentualeRisposteCorrette;
    TextView textPercentualeRisposteErrate;


    GestoreFile gf;
    int punteggio = 0;
    int numRispCorrette = 0;
    int numRispErrate = 0;
    int numTot = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistiche);


        /**Punteggi*/
        textPunteggioAddizioni = findViewById(R.id.textPunteggioAddizioni);
        textPunteggioSottrazioni = findViewById(R.id.textPunteggioSottrazioni);
        textPunteggioMoltiplicazioni = findViewById(R.id.textPunteggioMoltiplicazioni);
        textPunteggioDivisioni = findViewById(R.id.textPunteggioDivisioni);

        /**Risposte corrette ed errate*/
        textRisposteCorrette = findViewById(R.id.textRisposteCorrette);
        textRisposteErrate = findViewById(R.id.textRisposteErrate);

        /**Risposte percentuale corrette ed errate*/
        textPercentualeRisposteCorrette = (TextView) findViewById(R.id.textPercentualeRisposteCorrette);
        textPercentualeRisposteErrate = (TextView) findViewById(R.id.textPercentualeRisposteErrate);


        gf = new GestoreFile();

        punteggio = loadPunteggi(getApplicationContext(), "Addizioni");
        textPunteggioAddizioni.setText("" + punteggio);

        punteggio = loadPunteggi(getApplicationContext(), "Sottrazioni");
        textPunteggioSottrazioni.setText("" + punteggio);

        punteggio = loadPunteggi(getApplicationContext(), "Moltiplicazioni");
        textPunteggioMoltiplicazioni.setText("" + punteggio);

        punteggio = loadPunteggi(getApplicationContext(), "Divisioni");
        textPunteggioDivisioni.setText("" + punteggio);


        numRispCorrette = gf.caricaNumRisposteCorrette(getApplicationContext());
        textRisposteCorrette.setText("" + numRispCorrette);

        numRispErrate = gf.caricaNumRisposteErrate(getApplicationContext());
        textRisposteErrate.setText("" + numRispErrate);

        numTot = numRispCorrette + numRispErrate;

       // Log log = null;
       // log.d("DEBUG", "Corrette piu errrattteeeeeeee " + numTot);



        double drapportoCorrette = calcolaDivisione(numRispCorrette,numTot)*100;
        double drapportoErrate = calcolaDivisione(numRispErrate,numTot)*100;

       // log.d("DEBUG", "Rapporto  " + drapportoCorrette);
       // log.d("DEBUG", "Rapporto  " + drapportoErrate);



        //textPercentualeRisposteCorrette.setText("porc");


        if (numTot == 0) {
            textPercentualeRisposteCorrette.setText("" + 0 + "%");
            textPercentualeRisposteErrate.setText("" + 0 + "%");

        } else {


            textPercentualeRisposteCorrette.setText(""+drapportoCorrette+"%");
            textPercentualeRisposteErrate.setText(""+drapportoErrate+"%");

        }


    }

    /**
     *
     * @param context
     * @param categoria
     * @return
     */
    private int loadPunteggi(Context context, String categoria) {

        return gf.caricaScores(context, categoria);

    }


    /**
     * calocala divisone tra nueri interi ritornando un double
     * @param a
     * @param b
     * @return
     */
    public double calcolaDivisione(int a, int b){

        double da  = (double) 0.0 +a;
        double db = (double) 0.0 + b;


        return da/db;

    }
}
