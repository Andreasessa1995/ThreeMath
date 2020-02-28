package com.example.threemath;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystemNotFoundException;

public class GestoreFile {
    Log log = null;


    public void creaFileScore(Context context) {

        salvaScores(context, 0, "Addizioni");
        salvaScores(context, 0, "Sottrazioni");
        salvaScores(context, 0, "Moltiplicazioni");
        salvaScores(context, 0, "Divisioni");

    }


    /**
     * salva il punteggio dello score della categoria passata
     *
     * @param context
     * @param punteggio
     * @param categoria
     */
    public void salvaScores(Context context, int punteggio, String categoria) {


        String SCORE = "punteggioLivello" + categoria + ".dat";


        //log.d("DEBUG", "M'arrriv  puntegg  = " + punteggio);

        String spunteggio = "" + punteggio;

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
     * carica il punteggio dello score della categoria passata
     *
     * @param context
     * @param categoria
     * @return
     */
    public int caricaScores(Context context, String categoria) {

        String SCORE = "punteggioLivello" + categoria + ".dat";

        int score = 0;
        FileInputStream fis = null;
        InputStreamReader isr = null;
        String spunteggio = "";
        String line = "";
        String data = "";
        try {




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
            Log log =null;
            log.d("DEBUG", "oooooooooooooo e nullllll il fileeeeeeee " );
            azzeraScore(context, categoria);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return score;

    }

    /**
     * @param context
     */
    public void azzeraScore(Context context, String categoria) {

        String SCORE = "punteggioLivello" + categoria + ".dat";

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


    /**
     *
     * @param context
     * @param numeroRisoCor
     */
    public void salvaRisposteCorrette(Context context, int numeroRisoCor) {


        String SCORE = "numeroRisposteCorrette" + ".dat";


        //log.d("DEBUG", "M'arrriv  puntegg  = " + punteggio);

        String spunteggio = "" + numeroRisoCor;

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
     * @return
     */

    public int caricaNumRisposteCorrette(Context context) {

        String SCORE = "numeroRisposteCorrette" + ".dat";

        int score = 0;
        FileInputStream fis = null;
        InputStreamReader isr = null;
        String spunteggio = "";
        String line = "";
        String data = "";
        try {




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
     * @param numeroRisoEr
     */
    public void salvaRisposteErrate(Context context, int numeroRisoEr) {


        String SCORE = "numeroRisposteErrate" + ".dat";


        //log.d("DEBUG", "M'arrriv  puntegg  = " + punteggio);

        String spunteggio = "" + numeroRisoEr;

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
     * @return
     */

    public int caricaNumRisposteErrate(Context context) {

        String SCORE = "numeroRisposteErrate" + ".dat";

        int score = 0;
        FileInputStream fis = null;
        InputStreamReader isr = null;
        String spunteggio = "";
        String line = "";
        String data = "";
        try {




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

    public  void azzeraNumRisposte (Context context){

        salvaRisposteErrate(context,0);
        salvaRisposteCorrette(context,0);

    }




}
