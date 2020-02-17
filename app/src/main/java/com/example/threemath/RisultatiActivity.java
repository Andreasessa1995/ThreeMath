package com.example.threemath;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RisultatiActivity extends AppCompatActivity {
    int numRispEsatte =0;
    int numRispErrate = 0;
    int minutes = 0;
    int seconds = 0;
    int tempoSecondiQuesito = 0;
    int tempoMinutiQuesito = 0;

    TextView textRisposteErrate ;
    TextView textRisposteCorrette ;
    TextView textTempo ;

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

        tempoSecondiQuesito = 60-seconds;
        tempoMinutiQuesito = 0-minutes;



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
        textTempo.setText("Tempo = "+ " minuti: "+minutes+" secondi : "+tempoSecondiQuesito);


    }

    public void onClickHome(View v){
        /*new intent*/
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), HomeActivity.class);
        startActivityForResult(intent, 0);
    }
}
