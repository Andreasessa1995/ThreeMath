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

    TextView textRisposteErrate ;
    TextView textRisposteCorrette ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.risultati_activity);

        /**valori passati dall'activity precedente**/
        Intent i = getIntent();
        numRispEsatte=i.getIntExtra("CORRETTE",numRispEsatte);
        numRispErrate=i.getIntExtra("ERRATE",numRispErrate);


        textRisposteCorrette = (TextView) findViewById(R.id.testoRisultatiCorretti);
        textRisposteErrate = (TextView) findViewById(R.id.testoRisultatiErrati);

        Log log = null;
        //log.d("DEBUG", "corrette = " +numRispEsatte);
        //log.d("DEBUG", "errate= " + numRispErrate);

        textRisposteCorrette.setText(""+numRispEsatte);
        textRisposteErrate.setText(""+numRispErrate);


    }

    public void onClickHome(View v){
        /*new intent*/
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), HomeActivity.class);
        startActivityForResult(intent, 0);
    }
}
