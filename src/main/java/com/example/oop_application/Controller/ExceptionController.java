package com.example.oop_application.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ExceptionController {

    @FXML
    private Label message;


    public Label getMessage() {
        return message;
    }

    public void setCategory1(Label category1) {
        this.message = category1;
    }


    @FXML
    void initialize(){

    }

}
