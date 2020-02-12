package com.example.threemath;

import java.util.ArrayList;

public class Domanda {
static String domanda ="";
static String rispostaEsatta ="";

static String rispostaErrata1;
static String rispostaErrata2;
static String rispostaErrata3;



//static ArrayList<String> risposteErrate = new ArrayList<String>();

public Domanda (String domanda, String rispostaEsatta,String rispostaErrata1,String rispostaErrata2,String rispostaErrata3){
    this.domanda=domanda;
    this.rispostaEsatta=rispostaEsatta;
    this.rispostaErrata1=rispostaErrata1;
    this.rispostaErrata2=rispostaErrata2;
    this.rispostaErrata3=rispostaErrata3;


    //  this.risposteErrate=risposteErrate;

}


    public static String getDomanda() {
        return domanda;
    }

    public static String getRispostaEsatta() {
        return rispostaEsatta;
    }

    public static String getRispostaErrata1() {
        return rispostaErrata1;
    }

    public static String getRispostaErrata2() {
        return rispostaErrata2;
    }

    public static String getRispostaErrata3() {
        return rispostaErrata3;
    }
    // public static ArrayList<String> getRisposteErrate() {
     ///   return risposteErrate;
   //}

    public boolean checkRisposta(String risposta){
    boolean esito = false;
        if (risposta.equalsIgnoreCase(rispostaEsatta)){
            esito = true ;
        }
        else {
            esito =  false;
        }
        return esito;
    }
}
