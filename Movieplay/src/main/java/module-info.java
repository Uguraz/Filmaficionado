module com.example.movieplay {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;
    requires jdk.jconsole;


    opens com.example.movieplay to javafx.fxml;
    exports com.example.movieplay;
}