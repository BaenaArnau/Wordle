module org.example.wordle {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.wordle to javafx.fxml;
    exports org.example.wordle;
}