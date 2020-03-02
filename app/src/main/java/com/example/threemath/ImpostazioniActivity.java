package com.example.threemath;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;


public class ImpostazioniActivity extends AppCompatActivity {
    RadioButton rbSuoniSi;
    RadioButton rbSuoniNo;

    RadioButton rbVibSi;
    RadioButton rbVibNo;


    GestoreFile gf = new GestoreFile();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);

        rbSuoniSi = (RadioButton) findViewById(R.id.radioButtonSuoniSi);
        rbSuoniNo = (RadioButton) findViewById(R.id.radioButtonSuoniNo);
        rbVibSi = (RadioButton) findViewById(R.id.radioButtonVibSi);
        rbVibNo = (RadioButton) findViewById(R.id.radioButtonVibNo);

        Log log = null;
        checkImpostazioni();


    }

    /**
     * check delle impostazioni suono e vibrazione
     */
    public void checkImpostazioni() {

        if ("si".equalsIgnoreCase(gf.caricaImpostazioni(getApplicationContext(), "Suoni"))) {
            rbSuoniSi.setChecked(true);
        } else if ("no".equalsIgnoreCase(gf.caricaImpostazioni(getApplicationContext(), "Suoni"))) {
            rbSuoniNo.setChecked(true);
        }


        if ("si".equalsIgnoreCase(gf.caricaImpostazioni(getApplicationContext(), "Vibrazione"))) {
            rbVibSi.setChecked(true);
        } else if ("no".equalsIgnoreCase(gf.caricaImpostazioni(getApplicationContext(), "Vibrazione"))) {
            rbVibNo.setChecked(true);
        }


    }

    /**
     * @param v
     */
    public void onClickSuono(View v) {
        Log log = null;

        if (rbSuoniSi.isChecked()) {
            log.d("DEBUG", "STATO si checked--------");
            gf.salvaImpostazioni(getApplicationContext(), "Suoni", "si");

        }
        if (rbSuoniNo.isChecked()) {
            log.d("DEBUG", "STATO no checked--------");
            gf.salvaImpostazioni(getApplicationContext(), "Suoni", "no");

        }


    }

    /**
     *
     * @param v
     */
    public void onClickVibrazione(View v) {
        Log log = null;

        if (rbVibSi.isChecked()) {
            log.d("DEBUG", "STATO si checked--------vib");
            gf.salvaImpostazioni(getApplicationContext(), "Vibrazione", "si");


        }
        if (rbVibNo.isChecked()) {
            log.d("DEBUG", "STATO no checked--------vib");
            gf.salvaImpostazioni(getApplicationContext(), "Vibrazione", "no");


        }


    }
}
