package org.example.wordle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class HelloController {
    static int port;

    static int turno = 1;

    static String palabra;
    @FXML
    Button config;

    @FXML
    Label labelPort;
    @FXML
    TextField textFieldPort;

    @FXML
    TextField textFieldPalabra;

    @FXML //Menu.fmxl btn (Jugar)
    protected void onPlayClick() {
        System.out.println(port);
        if (port >= 1 && port <= 65535) {
            Thread serverThread = new Thread(() -> {
                HelloApplication helloController = new HelloApplication();
                helloController.instanciarServer(port);
            });
            serverThread.start();
            Stage stage = new Stage();
            //Cargas el FXML que queres que abra en un Parent

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("hello-view2.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //Se declara una Scene y se le asigna el FXML (Una Scene es la ventana)
            Scene scene = new Scene(root);
            //Establecemos la scena en el Stage
            stage.setScene(scene);
            //titulo para la ventana
            stage.setTitle("Wordle");

            stage.setWidth(540);
            stage.setHeight(700);
            stage.show();

            //Cerramos la ventana anterior de Login. La obtenemos a partir de un control (Button)
            Stage old = (Stage) config.getScene().getWindow();
            old.close();
        } else {
            Stage stage = new Stage();
            //Cargas el FXML que queres que abra en un Parent
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("Warning.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //Se declara una Scene y se le asigna el FXML (Una Scene es la ventana)
            Scene scene = new Scene(root);
            //Establecemos la scena en el Stage
            stage.setScene(scene);
            //titulo para la ventana
            stage.setTitle("Warning");
            stage.setWidth(400);
            stage.setHeight(200);
            stage.setAlwaysOnTop(true);
            stage.show();
        }

    } //Menu.fmxl

    @FXML //Menu.fmxl btn (Configuración)
    protected void onConfigClick() {
        Stage stage = new Stage();
        //Cargas el FXML que queres que abra en un Parent
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Port.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Se declara una Scene y se le asigna el FXML (Una Scene es la ventana)
        Scene scene = new Scene(root);
        //Establecemos la scena en el Stage
        stage.setScene(scene);
        //titulo para la ventana
        stage.setTitle("Warning");
        stage.setWidth(440);
        stage.setHeight(200);
        stage.setAlwaysOnTop(true);
        stage.isFocused();
        stage.show();
    }

    @FXML //Port.fmxl btn (Configuración)
    protected void onPortCOnfigClick() {
        try {
            if (Integer.parseInt(textFieldPort.getText()) >= 1 && Integer.parseInt(textFieldPort.getText()) <= 65535) {
                port = Integer.parseInt(textFieldPort.getText());
                labelPort.setText("Puerto configurado con éxito");
                System.out.println(port);
            } else {
                labelPort.setText("Error puerto no válido");
            }
        } catch (Exception e) {
            labelPort.setText("Error puerto no válido");
        }
    }

    @FXML //hello-view2.fxml btn(Enter)
    protected void onPlay() throws IOException {

            // Obtener la dirección IP del servidor del campo de texto

            // Establecer la conexión con el servidor en la dirección IP y el puerto especificados
            System.out.println("antes del socket");
            Socket socket = new Socket("localhost", port);
            System.out.println("despues del socket");
            palabra = String.valueOf(textFieldPalabra);
            // Crea un ObjectOutputStream asociado al flujo de salida del socket
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            // Crea un objeto que deseas enviar al servidor (debe ser serializable)
            Object wordle = new Wordle(palabra); // Reemplaza 'Wordle' con tu clase de objeto

            // Envía el objeto al servidor
            outputStream.writeObject(palabra);
            outputStream.flush();


    }
}