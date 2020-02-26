package com.example.threemath;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class GestoreFile {
    Log log = null;

    public static final String SCOREADDIZIONI = "punteggioLivelloAddizioni.dat";

    /**
     * Salva come formato stringa + ,  il punteggio totalizzato
     * sommandolo al precedente.
     * @param context
     * @param punteggio
     */
    public void salvaScoresAddizioni(Context context, int punteggio) {
        //log.d("DEBUG", "M'arrriv  puntegg  = " + punteggio);

        String spunteggio = ""+punteggio;

        FileOutputStream fos = null;

        try {
            spunteggio += ",";
            fos = context.openFileOutput(SCOREADDIZIONI, Context.MODE_PRIVATE);
            fos.flush();
            fos.write(spunteggio.getBytes());

           // log.d("DEBUG", "Salvo questo sul file = " + spunteggio);
            fos.close();
           // log.d("DEBUG", "Leggo questo sul file  appena scritto = " + caricaScoresAddizioni(context));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * carico il punteggio sottoforma di stringa + , la splitto e
     * successivamente la converto in intero ritornando il valore dello score
     * @param context
     * @return
     */
    public int caricaScoresAddizioni(Context context) {

        int score =0;
        FileInputStream fis = null;
        InputStreamReader isr = null;
        String spunteggio= "";
        String line = "";
        String data ="";
        try{
            fis = context.openFileInput(SCOREADDIZIONI);
            isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            while (null != (line = br.readLine())) {
                data += line;
            }
            br.close();
            fis.close();

            int x = data.indexOf(",");
            String[] dati = data.split(",");
            //score=br.read();
            log.d("DEBUG", "Leggo questo dal file = = " + data);

            spunteggio = dati[0];
            score = Integer.parseInt(spunteggio);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return score;

    }

    /**
     * azzero lo score nel file
     * @param context
     */
    public void azzeraScoreAddizioni (Context context){
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(SCOREADDIZIONI, Context.MODE_PRIVATE);
            fos.write("0,".getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     *
     * @param context
     * @param punteggio
     * @param categoria
     */
    public void salvaScores(Context context, int punteggio,String categoria) {



         String SCORE = "punteggioLivello"+categoria+".dat";



        //log.d("DEBUG", "M'arrriv  puntegg  = " + punteggio);

        String spunteggio = ""+punteggio;

        FileOutputStream fos = null;

        try {
            spunteggio += ",";
            fos = context.openFileOutput(SCORE, Context.MODE_PRIVATE);
            fos.flush();
            fos.write(spunteggio.getBytes());

            // log.d("DEBUG", "Salvo questo sul file = " + spunteggio);
            fos.close();
            // log.d("DEBUG", "Leggo questo sul file  appena scritto = " + caricaScoresAddizioni(context));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     *
     * @param context
     * @param categoria
     * @return
     */
    public int caricaScores(Context context,String categoria) {

        String SCORE = "punteggioLivello"+categoria+".dat";

        int score =0;
        FileInputStream fis = null;
        InputStreamReader isr = null;
        String spunteggio= "";
        String line = "";
        String data ="";
        try{
            fis = context.openFileInput(SCORE);
            isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            while (null != (line = br.readLine())) {
                data += line;
            }
            br.close();
            fis.close();

            int x = data.indexOf(",");
            String[] dati = data.split(",");
            //score=br.read();
            log.d("DEBUG", "Leggo questo dal file = = " + data);

            spunteggio = dati[0];
            score = Integer.parseInt(spunteggio);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return score;

    }

    /**
     *
     * @param context
     */
    public void azzeraScore (Context context,String categoria){

        String SCORE = "punteggioLivello"+categoria+".dat";

        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(SCORE, Context.MODE_PRIVATE);
            fos.write("0,".getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
