package org.example.wordle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Esta clase sirve para poder randomizar la palabra gracias a una api externa y guardarla para que el servidor pueda hacr las comprobaciones
 */
public class PalabraRandom {
    private final String palabra;
    private boolean palabraEncontrada;

    public PalabraRandom() {
        this.palabra = encontrarPalabra();
        this.palabraEncontrada = false;
    }

    private String encontrarPalabra(){
        String respuesta = "";
        try {
            URL url = new URL("https://clientes.api.greenborn.com.ar/public-random-word?l=5");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            respuesta = reader.readLine();
            reader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public int[] comprobarPalabra(String recivido){
        char[] palabraCaracteres = palabra.toLowerCase().toCharArray();
        char[] recividoCaracteres = recivido.toLowerCase().toCharArray();
        int[] coincidaencias = new int[5];

        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                if (recividoCaracteres[i] == palabraCaracteres[i]){
                    coincidaencias[i] = 2;
                    break;
                } else if (recividoCaracteres[i] == palabraCaracteres[j]) {
                    coincidaencias[i] = 1;
                    break;
                }else{
                    coincidaencias[i] = 0;
                }
            }
        }

        return coincidaencias;
    }

    public String getPalabra() {
        return palabra;
    }

    public boolean isPalabraEncontrada() {
        return palabraEncontrada;
    }

    public void setPalabraEncontrada(boolean palabraEncontrada) {
        this.palabraEncontrada = palabraEncontrada;
    }
}
