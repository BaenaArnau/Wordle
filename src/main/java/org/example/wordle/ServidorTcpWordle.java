package org.example.wordle;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorTcpWordle {
    int puerto;

    public ServidorTcpWordle(int puerto) {
        this.puerto = puerto;
    }

    public void listen(){
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(puerto);
            System.out.println("Server listening on port " + puerto);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                ThreadServidorTcpWordle threadServidor = new ThreadServidorTcpWordle(clientSocket);
                Thread clientThread = new Thread(threadServidor);
                clientThread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(ServidorTcpWordle.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
