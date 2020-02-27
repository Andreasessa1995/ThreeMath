package com.example.threemath;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class StatisticheActivity extends AppCompatActivity {



    /**TExt view Punteggi*/

    TextView textPunteggioAddizioni ;
    TextView textPunteggioSottrazioni ;
    TextView textPunteggioMoltiplicazioni ;
    TextView textPunteggioDivisioni ;

    TextView textRisposteCorrette;
    TextView textRisposteErrate;

    TextView textPercentualeRisposteCorrette;
    TextView textPercentualeRisposteErrate;


    GestoreFile gf ;
    int punteggio =0;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistiche);




        /**Punteggi*/
        TextView textPunteggioAddizioni =  findViewById(R.id.textPunteggioAddizioni);
        TextView textPunteggioSottrazioni =  findViewById(R.id.textPunteggioSottrazioni);
        TextView textPunteggioMoltiplicazioni = findViewById(R.id.textPunteggioMoltiplicazioni);
        TextView textPunteggioDivisioni =  findViewById(R.id.textPunteggioDivisioni);

        /**Risposte corrette ed errate*/
        TextView textRisposteCorrette =  findViewById(R.id.textRisposteCorrette);
        TextView textRisposteErrate =  findViewById(R.id.textRisposteErrate);

        /**Risposte percentuale corrette ed errate*/
        TextView textPercentualeRisposteCorrette =  findViewById(R.id.textPercentualeRisposteCorrette);
        TextView textPercentualeRisposteErrate =  findViewById(R.id.textPercentualeRisposteErrate);


        gf = new GestoreFile();

        punteggio = loadPunteggi(getApplicationContext(),"Addizioni");
        textPunteggioAddizioni.setText(""+punteggio);

        punteggio = loadPunteggi(getApplicationContext(),"Sottrazioni");
        textPunteggioSottrazioni.setText(""+punteggio);

        punteggio = loadPunteggi(getApplicationContext(),"Moltiplicazioni");
        textPunteggioMoltiplicazioni.setText(""+punteggio);

        punteggio = loadPunteggi(getApplicationContext(),"Divisioni");
        textPunteggioDivisioni.setText(""+punteggio);











    }

    private int  loadPunteggi(Context context, String categoria){

        return gf.caricaScores(context,categoria);

    }
}
