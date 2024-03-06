package org.example.wordle;

import java.io.Serializable;

public class Wordle implements Serializable {
    String palabra;


    public Wordle(String palabra) {
        this.palabra = palabra;
    }


    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }
}
