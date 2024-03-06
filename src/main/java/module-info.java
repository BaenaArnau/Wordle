module org.example.wordle {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens org.example.wordle to javafx.fxml;
    exports org.example.wordle;
}