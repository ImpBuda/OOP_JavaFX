package com.example.oop_application.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private Button headView;

    @FXML
    private Button instructionView;

    @FXML
    private Button studentView;

    @FXML
    void initialize(){
        studentView.setOnAction(actionEvent -> {
            studentView.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader(StudentController.class.getResource("/com/example/oop_application/student.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
    }
}
