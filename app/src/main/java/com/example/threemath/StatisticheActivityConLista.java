package com.example.threemath;


import android.content.Context;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import android.widget.ListView;


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


    Categoria c18 = new Categoria("Perc. corrette",0.0);
    Categoria c19 = new Categoria("Perc. errate",0.0);


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
        punteggio = loadNumRisposteEsattePerCategoria(getApplicationContext(),"Addizioni");
        Categoria c2 = new Categoria ("Corrette",punteggio);
        punteggio = loadNumRisposteErratePerCategoria(getApplicationContext(),"Addizioni");
        Categoria c3 = new Categoria ("Erratte",punteggio);



        punteggio = loadPunteggi(getApplicationContext(), "Sottrazioni");
        Categoria c4 = new Categoria ("Sottrazioni",punteggio);
        punteggio = loadNumRisposteEsattePerCategoria(getApplicationContext(),"Sottrazioni");
        Categoria c5 = new Categoria ("Corrette",punteggio);
        punteggio = loadNumRisposteErratePerCategoria(getApplicationContext(),"Sottrazioni");
        Categoria c6 = new Categoria ("Erratte",punteggio);



        punteggio = loadPunteggi(getApplicationContext(), "Moltiplicazioni");
        Categoria c7 = new Categoria ("Moltiplicazioni",punteggio);
        punteggio = loadNumRisposteEsattePerCategoria(getApplicationContext(),"Moltiplicazioni");
        Categoria c8 = new Categoria ("Corrette",punteggio);
        punteggio = loadNumRisposteErratePerCategoria(getApplicationContext(),"Moltiplicazioni");
        Categoria c9 = new Categoria ("Erratte",punteggio);

        punteggio = loadPunteggi(getApplicationContext(), "Divisioni");
        Categoria c10 = new Categoria ("Divisioni",punteggio);
        punteggio = loadNumRisposteEsattePerCategoria(getApplicationContext(),"Divisioni");
        Categoria c11 = new Categoria ("Corrette",punteggio);
        punteggio = loadNumRisposteErratePerCategoria(getApplicationContext(),"Divisioni");
        Categoria c12 = new Categoria ("Erratte",punteggio);



        punteggio = loadPunteggi(getApplicationContext(), "Indovinelli");
        Categoria c13 = new Categoria ("Indovinelli",punteggio);
        punteggio = loadNumRisposteEsattePerCategoria(getApplicationContext(),"Indovinelli");
        Categoria c14 = new Categoria ("Corrette",punteggio);
        punteggio = loadNumRisposteErratePerCategoria(getApplicationContext(),"Indovinelli");
        Categoria c15 = new Categoria ("Erratte",punteggio);








        numRispCorrette = gf.caricaNumRisposteCorrette(getApplicationContext());
        Categoria c16 = new Categoria("Tot. Corrette",numRispCorrette);

        numRispErrate = gf.caricaNumRisposteErrate(getApplicationContext());
        Categoria c17 = new Categoria("Tot. Errate",numRispErrate);

        numTot = numRispCorrette + numRispErrate;

         Log log = null;
        // log.d("DEBUG", "Corrette piu errrattteeeeeeee " + numTot);

        if (numTot != 0) {

            double drapportoCorrette = calcolaDivisione(numRispCorrette,numTot)*100;
            double drapportoErrate = calcolaDivisione(numRispErrate,numTot)*100;
            c18 = new Categoria("Perc. corrette",drapportoCorrette);
            c19 = new Categoria("Perc. errate",drapportoErrate);

        }





        // double drapportoCorrette = 50.0/10.0;







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

        listaCategoria.add(c10);
        customAdapter.add(c10);

        listaCategoria.add(c11);
        customAdapter.add(c11);

        listaCategoria.add(c12);
        customAdapter.add(c12);

        listaCategoria.add(c13);
        customAdapter.add(c13);

        listaCategoria.add(c14);
        customAdapter.add(c14);

        listaCategoria.add(c15);
        customAdapter.add(c15);

        listaCategoria.add(c16);
        customAdapter.add(c16);

        listaCategoria.add(c17);
        customAdapter.add(c17);

        listaCategoria.add(c18);
        customAdapter.add(c18);

        listaCategoria.add(c19);
        customAdapter.add(c19);






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
     * carica il numero di risposte esatte per categoria
     * @param context
     * @param categoria
     * @return
     */
    private  int loadNumRisposteEsattePerCategoria(Context context,String categoria ){

        return gf.caricaNumRisposteCorretteCategoria(context,categoria);
    }

    /**
     * carica il numero di risposte errate per categoria
     * @param context
     * @param categoria
     * @return
     */
    private  int loadNumRisposteErratePerCategoria(Context context,String categoria ){

        return gf.caricaNumRisposteErrateCategoria(context,categoria);
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
