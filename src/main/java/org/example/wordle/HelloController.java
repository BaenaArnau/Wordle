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
import java.io.InputStream;
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

    boolean palabraEncontrada;

    int [] estadoPalabra;
    Socket socket;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;

    @FXML private Label
            PrimeraLetra6, SegundaLetra6, TerceraLetra6, CuartaLetra6, QuintaLetra6,
            PrimeraLetra5, SegundaLetra5, TerceraLetra5, CuartaLetra5, QuintaLetra5,
            PrimeraLetra4, SegundaLetra4, TerceraLetra4, CuartaLetra4, QuintaLetra4,
            PrimeraLetra3, SegundaLetra3, TerceraLetra3, CuartaLetra3, QuintaLetra3,
            PrimeraLetra2, SegundaLetra2, TerceraLetra2, CuartaLetra2, QuintaLetra2,
            PrimeraLetra1, SegundaLetra1, TerceraLetra1, CuartaLetra1, QuintaLetra1;

    String palabraBuena;
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
            if (turno == 1) {
                // Establecer la conexión con el servidor en la dirección IP y el puerto especificados
                System.out.println("antes del socket");
                socket = new Socket("localhost", port);
                System.out.println("despues del socket");
                // Crea un ObjectOutputStream asociado al flujo de salida del socket
                inputStream = new ObjectInputStream(socket.getInputStream());
                outputStream = new ObjectOutputStream(socket.getOutputStream());
            }

                palabra = String.valueOf(textFieldPalabra.getText());
                // Crea un objeto que deseas enviar al servidor (debe ser serializable)
                Wordle wordle = new Wordle(palabra);

                // Envía el objeto al servidor
                outputStream.writeObject(wordle);
                outputStream.flush();

                try {
                    inputPartida(inputStream);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
    }

    protected void inputPartida(ObjectInputStream respuestaServidor) throws IOException, ClassNotFoundException {
        turno++;
        palabraEncontrada = respuestaServidor.readBoolean();
        estadoPalabra = (int[]) respuestaServidor.readObject();
        palabraBuena = (String) respuestaServidor.readObject();
        for (int i = 0; i < estadoPalabra.length; i++) {
            System.out.println(estadoPalabra[i]);
        }
        System.out.println(palabraBuena);
            if (turno-1 == 1){
                switch (estadoPalabra[0]){
                    case 0: PrimeraLetra1.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: PrimeraLetra1.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: PrimeraLetra1.setStyle("-fx-background-color:  #60f026");
                }
                PrimeraLetra1.setText(String.valueOf(palabra.charAt(0)).toUpperCase());
                switch (estadoPalabra[1]){
                    case 0: SegundaLetra1.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: SegundaLetra1.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: SegundaLetra1.setStyle("-fx-background-color:  #60f026");
                }
                SegundaLetra1.setText(String.valueOf(palabra.charAt(1)).toUpperCase());
                switch (estadoPalabra[2]){
                    case 0: TerceraLetra1.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: TerceraLetra1.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: TerceraLetra1.setStyle("-fx-background-color:  #60f026");
                }
                TerceraLetra1.setText(String.valueOf(palabra.charAt(2)).toUpperCase());
                switch (estadoPalabra[3]){
                    case 0: CuartaLetra1.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: CuartaLetra1.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: CuartaLetra1.setStyle("-fx-background-color:  #60f026");
                }
                CuartaLetra1.setText(String.valueOf(palabra.charAt(3)).toUpperCase());
                switch (estadoPalabra[4]){
                    case 0: QuintaLetra1.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: QuintaLetra1.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: QuintaLetra1.setStyle("-fx-background-color:  #60f026");
                }
                QuintaLetra1.setText(String.valueOf(palabra.charAt(4)).toUpperCase());
            }
            else if (turno -1 == 2) {
                switch (estadoPalabra[0]){
                    case 0: PrimeraLetra2.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: PrimeraLetra2.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: PrimeraLetra2.setStyle("-fx-background-color:  #60f026");
                }
                PrimeraLetra2.setText(String.valueOf(palabra.charAt(0)).toUpperCase());
                switch (estadoPalabra[1]){
                    case 0: SegundaLetra2.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: SegundaLetra2.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: SegundaLetra2.setStyle("-fx-background-color:  #60f026");
                }
                SegundaLetra2.setText(String.valueOf(palabra.charAt(1)).toUpperCase());
                switch (estadoPalabra[2]){
                    case 0: TerceraLetra2.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: TerceraLetra2.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: TerceraLetra2.setStyle("-fx-background-color:  #60f026");
                }
                TerceraLetra2.setText(String.valueOf(palabra.charAt(2)).toUpperCase());
                switch (estadoPalabra[3]){
                    case 0: CuartaLetra2.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: CuartaLetra2.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: CuartaLetra2.setStyle("-fx-background-color:  #60f026");
                }
                CuartaLetra2.setText(String.valueOf(palabra.charAt(3)).toUpperCase());
                switch (estadoPalabra[4]){
                    case 0: QuintaLetra2.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: QuintaLetra2.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: QuintaLetra2.setStyle("-fx-background-color:  #60f026");
                }
                QuintaLetra2.setText(String.valueOf(palabra.charAt(4)).toUpperCase());
            }
            else if (turno-1 == 3) {
                switch (estadoPalabra[0]){
                    case 0: PrimeraLetra3.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: PrimeraLetra3.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: PrimeraLetra3.setStyle("-fx-background-color:  #60f026");
                }
                PrimeraLetra3.setText(String.valueOf(palabra.charAt(0)).toUpperCase());
                switch (estadoPalabra[1]){
                    case 0: SegundaLetra3.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: SegundaLetra3.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: SegundaLetra3.setStyle("-fx-background-color:  #60f026");
                }
                SegundaLetra3.setText(String.valueOf(palabra.charAt(1)).toUpperCase());
                switch (estadoPalabra[2]){
                    case 0: TerceraLetra3.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: TerceraLetra3.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: TerceraLetra3.setStyle("-fx-background-color:  #60f026");
                }
                TerceraLetra3.setText(String.valueOf(palabra.charAt(2)).toUpperCase());
                switch (estadoPalabra[3]){
                    case 0: CuartaLetra3.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: CuartaLetra3.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: CuartaLetra3.setStyle("-fx-background-color:  #60f026");
                }
                CuartaLetra3.setText(String.valueOf(palabra.charAt(3)).toUpperCase());
                switch (estadoPalabra[4]){
                    case 0: QuintaLetra3.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: QuintaLetra3.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: QuintaLetra3.setStyle("-fx-background-color:  #60f026");
                }
                QuintaLetra3.setText(String.valueOf(palabra.charAt(4)).toUpperCase());
            }
            else if (turno-1 == 4){
                switch (estadoPalabra[0]){
                    case 0: PrimeraLetra4.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: PrimeraLetra4.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: PrimeraLetra4.setStyle("-fx-background-color:  #60f026");
                }
                PrimeraLetra4.setText(String.valueOf(palabra.charAt(0)).toUpperCase());
                switch (estadoPalabra[1]){
                    case 0: SegundaLetra4.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: SegundaLetra4.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: SegundaLetra4.setStyle("-fx-background-color:  #60f026");
                }
                SegundaLetra4.setText(String.valueOf(palabra.charAt(1)).toUpperCase());
                switch (estadoPalabra[2]){
                    case 0: TerceraLetra4.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: TerceraLetra4.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: TerceraLetra4.setStyle("-fx-background-color:  #60f026");
                }
                TerceraLetra4.setText(String.valueOf(palabra.charAt(2)).toUpperCase());
                switch (estadoPalabra[3]){
                    case 0: CuartaLetra4.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: CuartaLetra4.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: CuartaLetra4.setStyle("-fx-background-color:  #60f026");
                }
                CuartaLetra4.setText(String.valueOf(palabra.charAt(3)).toUpperCase());
                switch (estadoPalabra[4]){
                    case 0: QuintaLetra4.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: QuintaLetra4.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: QuintaLetra4.setStyle("-fx-background-color:  #60f026");
                }
                QuintaLetra4.setText(String.valueOf(palabra.charAt(4)).toUpperCase());
            }
            else if (turno-1 == 5) {
                switch (estadoPalabra[0]){
                    case 0: PrimeraLetra5.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: PrimeraLetra5.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: PrimeraLetra5.setStyle("-fx-background-color:  #60f026");
                }
                PrimeraLetra5.setText(String.valueOf(palabra.charAt(0)).toUpperCase());
                switch (estadoPalabra[1]){
                    case 0: SegundaLetra5.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: SegundaLetra5.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: SegundaLetra5.setStyle("-fx-background-color:  #60f026");
                }
                SegundaLetra5.setText(String.valueOf(palabra.charAt(1)).toUpperCase());
                switch (estadoPalabra[2]){
                    case 0: TerceraLetra5.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: TerceraLetra5.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: TerceraLetra5.setStyle("-fx-background-color:  #60f026");
                }
                TerceraLetra5.setText(String.valueOf(palabra.charAt(2)).toUpperCase());
                switch (estadoPalabra[3]){
                    case 0: CuartaLetra5.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: CuartaLetra5.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: CuartaLetra5.setStyle("-fx-background-color:  #60f026");
                }
                CuartaLetra5.setText(String.valueOf(palabra.charAt(3)).toUpperCase());
                switch (estadoPalabra[4]){
                    case 0: QuintaLetra5.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: QuintaLetra5.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: QuintaLetra5.setStyle("-fx-background-color:  #60f026");
                }
                QuintaLetra5.setText(String.valueOf(palabra.charAt(4)).toUpperCase());
            }
            else if (turno-1 == 6) {
                switch (estadoPalabra[0]){
                    case 0: PrimeraLetra6.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: PrimeraLetra6.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: PrimeraLetra6.setStyle("-fx-background-color:  #60f026");
                }
                PrimeraLetra6.setText(String.valueOf(palabra.charAt(0)).toUpperCase());
                switch (estadoPalabra[1]){
                    case 0: SegundaLetra6.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: SegundaLetra6.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: SegundaLetra6.setStyle("-fx-background-color:  #60f026");
                }
                SegundaLetra6.setText(String.valueOf(palabra.charAt(1)).toUpperCase());
                switch (estadoPalabra[2]){
                    case 0: TerceraLetra6.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: TerceraLetra6.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: TerceraLetra6.setStyle("-fx-background-color:  #60f026");
                }
                TerceraLetra6.setText(String.valueOf(palabra.charAt(2)).toUpperCase());
                switch (estadoPalabra[3]){
                    case 0: CuartaLetra6.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: CuartaLetra6.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: CuartaLetra6.setStyle("-fx-background-color:  #60f026");
                }
                CuartaLetra6.setText(String.valueOf(palabra.charAt(3)).toUpperCase());
                switch (estadoPalabra[4]){
                    case 0: QuintaLetra6.setStyle("-fx-background-color:  #f13131"); break;
                    case 1: QuintaLetra6.setStyle("-fx-background-color:  #ffad3f"); break;
                    case 2: QuintaLetra6.setStyle("-fx-background-color:  #60f026");
                }
                QuintaLetra6.setText(String.valueOf(palabra.charAt(4)).toUpperCase());
            }


    }
}