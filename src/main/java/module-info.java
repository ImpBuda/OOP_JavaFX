module com.example.oop_application {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires com.google.gson;

    opens com.example.oop_application to javafx.fxml;
    opens com.example.oop_application.Controller to javafx.fxml;
    opens com.example.oop_application.Model to javafx.fxml, javafx.graphics, com.google.gson;

    exports com.example.oop_application;
    exports com.example.oop_application.Controller;
    exports com.example.oop_application.Model;

}