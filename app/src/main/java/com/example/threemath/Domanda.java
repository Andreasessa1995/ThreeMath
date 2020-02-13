package com.example.threemath;

import java.util.ArrayList;

public class Domanda {

    private String domanda = "";
    private String rispostaEsatta = "";
    private String rispostaErrata1 = "";
    private String rispostaErrata2 = "";
    private String rispostaErrata3 = "";


//static ArrayList<String> risposteErrate = new ArrayList<String>();

    public Domanda(String domanda, String rispostaEsatta, String rispostaErrata1, String rispostaErrata2, String rispostaErrata3) {
        this.domanda = domanda;
        this.rispostaEsatta = rispostaEsatta;
        this.rispostaErrata1 = rispostaErrata1;
        this.rispostaErrata2 = rispostaErrata2;
        this.rispostaErrata3 = rispostaErrata3;


        //  this.risposteErrate=risposteErrate;

    }


    public String getDomanda() {
        return domanda;
    }

    public String getRispostaEsatta() {
        return rispostaEsatta;
    }

    public String getRispostaErrata1() {
        return rispostaErrata1;
    }

    public String getRispostaErrata2() {
        return rispostaErrata2;
    }

    public String getRispostaErrata3() {
        return rispostaErrata3;
    }
    // public static ArrayList<String> getRisposteErrate() {
    ///   return risposteErrate;
    //}

    public boolean checkRisposta(String risposta) {
        boolean esito = false;
        if (risposta.equalsIgnoreCase(rispostaEsatta)) {
            esito = true;
        } else {
            esito = false;
        }
        return esito;
    }
}
