package com.example.threemath;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;



public class ImpostazioniActivity extends AppCompatActivity {
    RadioButton rbSuoniSi;
    RadioButton rbSuoniNo;

    RadioButton rbVibSi;
    RadioButton rbVibNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);

        rbSuoniSi = (RadioButton) findViewById(R.id.radioButtonSuoniSi);
        rbSuoniNo = (RadioButton) findViewById(R.id.radioButtonSuoniNo);
        rbVibSi = (RadioButton) findViewById(R.id.radioButtonVibSi);
        rbVibNo = (RadioButton) findViewById(R.id.radioButtonVibNo);

        Log log = null;


        if( rbSuoniSi.isActivated()){
            log.d("DEBUG", "STATO si isActivated----"  );
        }
        if (rbSuoniSi.isChecked()){
            log.d("DEBUG", "STATO si checked--------"  );

        }
        if( rbSuoniNo.isActivated()){
            log.d("DEBUG", "STATO no isActivated----"  );
        }
        if (rbSuoniNo.isChecked()){
            log.d("DEBUG", "STATO no checked--------"  );

        }






    }
}
