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
        // Convertimos la palabra almacenada y la palabra recibida a minúsculas y luego a arreglos de caracteres
        char[] palabraCaracteres = palabra.toLowerCase().toCharArray();
        char[] recibidoCaracteres = recibido.toLowerCase().toCharArray();

        // Creamos un arreglo de enteros para almacenar los resultados de la comparación
        int[] coincidencias = new int[5];

        // Iteramos sobre los caracteres de la palabra recibida
        for (int i = 0; i < 5; i++) {
            // Inicializamos un booleano para verificar si hay coincidencia exacta entre los caracteres
            boolean coincidenciaExacta = false;

            // Iteramos sobre los caracteres de la palabra almacenada
            for (int j = 0; j < 5; j++) {
                // Si encontramos una coincidencia exacta entre los caracteres, marcamos como verdadero y salimos del bucle interno
                if (recibidoCaracteres[i] == palabraCaracteres[j]) {
                    coincidenciaExacta = true;
                    break;
                }
            }

            // Si hay coincidencia exacta, marcamos el valor correspondiente como 2 (verde)
            if (coincidenciaExacta) {
                coincidencias[i] = 2; // Verde
            } else {
                // Buscamos coincidencias parciales y las marcamos como 1 (naranja) si no han sido marcadas previamente
                for (int j = 0; j < 5; j++) {
                    if (recibidoCaracteres[i] == palabraCaracteres[j] && coincidencias[j] == 0) {
                        coincidencias[i] = 1; // Naranja
                        coincidencias[j] = 1;
                        break;
                    }
                }
            }
        }

        // Devolvemos el arreglo de coincidencias
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
