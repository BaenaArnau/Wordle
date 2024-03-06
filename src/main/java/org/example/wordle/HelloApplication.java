package org.example.wordle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 540, 700);

        stage.setTitle("Wordle!");
        stage.setScene(scene);
        stage.show();

        /*FXMLLoader fxmlLoader2 = new FXMLLoader(HelloApplication.class.getResource("hello-view2.fxml"));
        Scene scene2 = new Scene(fxmlLoader.load(), 540, 700);
        stage.setTitle("Wordle!");
        stage.setScene(scene);
        stage.show();*/
    }

    public static void main(String[] args) {
        launch();
    }

    public void instanciarServer(int port){
        ServidorTcpWordle tcpWordle = new ServidorTcpWordle(port);
        tcpWordle.listen();

    }
}