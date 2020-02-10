package com.example.threemath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Domanda> quesito = new ArrayList<>();


        Domanda domanda1 = new Domanda("Come ti chiami ","andrea ","giacomo","mario","amedeo");
        Domanda domanda2 = new Domanda("Il tuo cognome  ","sessa ","landi","de paoli","brighi");

        String x = domanda1.getDomanda();

        quesito.add(domanda1);
        quesito.add(domanda2);

        String y = quesito.get(0).getDomanda();
        String j = quesito.get(0).getRispostaEsatta();

        Log log = null;
        log.d("DEBUG","domanda in arrivo = "+ y);
        log.d("DEBUG","domanda in arrivo-risposta esatta= "+ j);



    }





}

