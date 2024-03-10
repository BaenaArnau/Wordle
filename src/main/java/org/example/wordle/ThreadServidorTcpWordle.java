package org.example.wordle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadServidorTcpWordle implements Runnable {
    private Socket clientSocket;
    private PalabraRandom palabraRandom;
    private final int[] completado = new int[]{2, 2, 2, 2, 2};

    public ThreadServidorTcpWordle(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.palabraRandom = new PalabraRandom();
    }

    @Override
    public void run() {
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("antes try");
        while (true) {
            try {
                System.out.println("despues try");

                // Cambia la conversión a Wordle en lugar de String
                Wordle receivedWordle = (Wordle) inputStream.readObject();
                String receivedString = receivedWordle.getPalabra();

                int[] comprobacion = palabraRandom.comprobarPalabra(receivedString);

                // Compara si comprobacion es igual a completado
                boolean palabraEncontrada = Arrays.equals(comprobacion, completado);

                outputStream.writeBoolean(palabraEncontrada);
                outputStream.writeObject(comprobacion);
                outputStream.flush();

                System.out.println("La palabra ha sido encontrada: " + palabraEncontrada);
                System.out.println("Array: " + comprobacion[1]);
                System.out.println(receivedString);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
