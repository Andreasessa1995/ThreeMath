package com.example.threemath;

import java.io.Serializable;

public class Categoria implements Serializable {

    private String categoria = "";

    int punteggio = 0;

    double dpunteggio = 0.0;


    public Categoria (String mcategoria, int mpunteggio){
        this.categoria=mcategoria;
        this.punteggio=mpunteggio;
    }

    public Categoria (String mcategoria, double mdpunteggio){
        this.categoria=mcategoria;
        this.dpunteggio=mdpunteggio;
    }



    public void setcategoria(String mcategoria) {
        this.categoria = mcategoria;
    }

    public String getcategoria() {
        return this.categoria;
    }

    public void setPunteggio(int mpunteggio){

        this.punteggio = mpunteggio;

    }
    public int getPunteggio(){
        return  this.punteggio;
    }

    public void setPunteggioDouble(double mdpunteggio){

        this.dpunteggio = mdpunteggio;

    }
    public double getPunteggioDouble(){
        return  this.dpunteggio;
    }
}
