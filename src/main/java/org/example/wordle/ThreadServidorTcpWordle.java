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
        System.out.println("antes try");
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

            System.out.println("despues try");
            String receivedString = (String) inputStream.readObject();
            int[] comprobacion = palabraRandom.comprobarPalabra(receivedString);

            // Compara si comprobacion es igual a completado
            boolean palabraEncontrada = Arrays.equals(comprobacion, completado);

            outputStream.writeBoolean(palabraEncontrada);
            outputStream.flush();
            System.out.println("La palabra ha sido encontrada: " + palabraEncontrada);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(ThreadServidorTcpWordle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
