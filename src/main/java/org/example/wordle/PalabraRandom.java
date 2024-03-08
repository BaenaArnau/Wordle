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
    private String palabra;
    private boolean palabraEncontrada;

    public PalabraRandom() {
        this.palabra = encontrarPalabra();
        this.palabraEncontrada = false;
    }

    private String encontrarPalabra() {
        String respuesta = "";
        try {
            URL url = new URL("https://clientes.api.greenborn.com.ar/public-random-word?l=5");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            respuesta = reader.readLine(); // Obtener la respuesta completa
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Extraer la palabra de la respuesta
        String palabra = respuesta.substring(2, respuesta.length() - 2); // Eliminar corchetes y comillas
        return palabra;
    }

    public int[] comprobarPalabra(String recibido) {

        System.out.println("Esta es la palabra a adivinar: " + palabra);
        System.out.println("Esta es la palabra recivida: " + recibido);

        // Convertimos la palabra almacenada y la palabra recibida a minúsculas y luego a arreglos de caracteres
        char[] palabraCaracteres = palabra.toLowerCase().toCharArray();
        char[] recibidoCaracteres = recibido.toLowerCase().toCharArray();

        // Creamos un arreglo de enteros para almacenar los resultados de la comparación
        int[] coincidencias = new int[5];

        // Iteramos sobre los caracteres de la palabra recibida
        for (int i = 0; i < 5; i++) {
            // Verificamos si hay una coincidencia exacta en posición y carácter
            if (recibidoCaracteres[i] == palabraCaracteres[i]) {
                coincidencias[i] = 2; // Verde
            } else {
                // Si no hay coincidencia exacta, buscamos coincidencias parciales en el resto de la palabra almacenada
                for (int j = 0; j < 5; j++) {
                    // Si encontramos una coincidencia parcial, la marcamos si no ha sido marcada previamente
                    if (recibidoCaracteres[i] == palabraCaracteres[j] && coincidencias[j] == 0) {
                        coincidencias[j] = 1; // Naranja
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
