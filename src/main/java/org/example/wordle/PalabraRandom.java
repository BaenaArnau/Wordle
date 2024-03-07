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

    public int[] comprobarPalabra(String recibido){
        char[] palabraCaracteres = palabra.toLowerCase().toCharArray();
        char[] recibidoCaracteres = recibido.toLowerCase().toCharArray();
        int[] coincidencias = new int[5];

        for (int i = 0; i < 5; i++) {
            boolean coincidenciaExacta = false;

            for (int j = 0; j < 5; j++) {
                if (recibidoCaracteres[i] == palabraCaracteres[j]) {
                    coincidenciaExacta = true;
                    break;
                }
            }

            if (coincidenciaExacta) {
                coincidencias[i] = 2; // Verde
            } else {
                for (int j = 0; j < 5; j++) {
                    if (recibidoCaracteres[i] == palabraCaracteres[j] && coincidencias[j] == 0) {
                        coincidencias[i] = 1; // Naranja
                        coincidencias[j] = 1;
                        break;
                    }
                }
            }
        }
        return coincidencias;
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
