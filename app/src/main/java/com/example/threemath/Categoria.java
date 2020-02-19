package com.example.threemath;

import java.io.Serializable;

public class Categoria implements Serializable {

    private String categoria = "";

    public Categoria (String mcategoria){
        this.categoria=mcategoria;
    }

    public void setcategoria(String mcategoria) {
        this.categoria = mcategoria;
    }

    public String getcategoria() {
        return categoria;
    }
}
