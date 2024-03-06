package org.example.wordle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    int port = 0;
    @FXML
    Button config;

    @FXML
    Button portConfig;

    @FXML
    Label portLabel;

    @FXML //Menu.fmxl btn (Jugar)
    protected void onPlayClick() {
        if (port >= 1 && port <=65535) {
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
        }else {
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
    protected void onConfigClick(){
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
    protected void onPortCOnfigClick(){
        try {

        }catch (Exception e){}
    }
}