package com.example.threemath;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class StatisticheActivityConLista extends AppCompatActivity {





    /** lista **/
    ListView lista;
    CustomAdapter customAdapter;

    GestoreFile gf ;
    int punteggio = 0;
    int numRispCorrette = 0;
    int numRispErrate = 0;

    int numTot = 0;


    /**
     * suoni
     **/
    MediaPlayer mpBat;
    ArrayList<Categoria> listaCategoria = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistiche_2);

        gf = new GestoreFile();

        punteggio = (int) loadPunteggi(getApplicationContext(), "Addizioni");
        Categoria c = new Categoria ("Addizioni",punteggio);

        punteggio = loadPunteggi(getApplicationContext(), "Sottrazioni");
        Categoria c2 = new Categoria ("Sottrazioni",punteggio);


        punteggio = loadPunteggi(getApplicationContext(), "Moltiplicazione");
        Categoria c3 = new Categoria ("Moltiplicazione",punteggio);

        punteggio = loadPunteggi(getApplicationContext(), "Divisioni");
        Categoria c4 = new Categoria ("Divisioni",punteggio);

        punteggio = loadPunteggi(getApplicationContext(), "Indovinelli");
        Categoria c5 = new Categoria ("Indovinelli",punteggio);





        numRispCorrette = gf.caricaNumRisposteCorrette(getApplicationContext());
        Categoria c6 = new Categoria("Corrette",numRispCorrette);

        numRispErrate = gf.caricaNumRisposteErrate(getApplicationContext());
        Categoria c7 = new Categoria("Errate",numRispErrate);

        numTot = numRispCorrette + numRispErrate;

         Log log = null;
         log.d("DEBUG", "Corrette piu errrattteeeeeeee " + numTot);



        double drapportoCorrette = calcolaDivisione(numRispCorrette,numTot)*100;
        double drapportoErrate = calcolaDivisione(numRispErrate,numTot)*100;


        // double drapportoCorrette = 50.0/10.0;

        Categoria c8 = new Categoria("Perc. corrette",drapportoCorrette);
        Categoria c9 = new Categoria("Perc. errate",drapportoErrate);





        /**creazione lista **/
        lista= findViewById(R.id.mylist);
        customAdapter = new CustomAdapter(this, R.layout.elemento_categoria, new ArrayList<Categoria>());
        lista.setAdapter(customAdapter);

        listaCategoria.add(c);
        customAdapter.add(c);

        listaCategoria.add(c2);
        customAdapter.add(c2);

        listaCategoria.add(c3);
        customAdapter.add(c3);

        listaCategoria.add(c4);
        customAdapter.add(c4);

        listaCategoria.add(c5);
        customAdapter.add(c5);

        listaCategoria.add(c6);
        customAdapter.add(c6);

        listaCategoria.add(c7);
        customAdapter.add(c7);

        listaCategoria.add(c8);
        customAdapter.add(c8);

        listaCategoria.add(c9);
        customAdapter.add(c9);




        /***suoni**/
       // mpBat = MediaPlayer.create(this, R.raw.bat);


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
