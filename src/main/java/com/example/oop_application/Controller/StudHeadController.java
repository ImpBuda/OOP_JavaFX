package com.example.oop_application.Controller;

import com.example.oop_application.Model.Head_Student;
import com.example.oop_application.Model.Student;
import com.example.oop_application.Repository.HeadStudentRepository;
import com.example.oop_application.Repository.Impl.HeadStudentRepositoryImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class StudHeadController {

    HeadStudentRepository headStudentRepository = new HeadStudentRepositoryImpl();

    @FXML
    private Button cancel;

    @FXML
    private ToggleGroup find;

    @FXML
    private TableColumn<Head_Student, Integer> headid;

    @FXML
    private TextField headidInput;

    @FXML
    private TextField idstudInput;

    @FXML
    private TextField search;

    @FXML
    private TableColumn<Head_Student, Integer> studentid;

    @FXML
    private TableView<Head_Student> head_student;

    @FXML
    private Button submit;

    @FXML
    private Button updatebtn;

    @FXML
    private Button deletebtn;

    @FXML
    private AnchorPane addPanel;

    @FXML
    private RadioMenuItem headradio;

    @FXML
    private RadioMenuItem studradio;

    @FXML
    void initialize(){

        updateTable(headStudentRepository.getAll());

        cancel.setOnAction(action -> {
            clearInput();
            addPanel.setStyle("visibility: hidden;");
        });

        deletebtn.setOnAction(actionEvent -> {
            if(!head_student.getSelectionModel().isEmpty()) {
                delete();
                updateTable(headStudentRepository.getAll());
            }
            else {
                modalWindow("Выберите строку, которую нужно удалить");
            }
        });

        updatebtn.setOnAction(actionEvent -> {
            if(!head_student.getSelectionModel().isEmpty()) {
                addPanel.setStyle("visibility: visible;");
                fillingInput();

                submit.setOnAction(action -> {
                    update();
                    updateTable(headStudentRepository.getAll());
                    addPanel.setStyle("visibility: hidden;");
                    clearInput();
                });
            }
            else {
                modalWindow("Выберите строку, которую нужно обновить");
            }
        });

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            if (headradio.isSelected()) updateTable(headStudentRepository.searchHead(newValue));
            else if (studradio.isSelected()) updateTable(headStudentRepository.searchStud(newValue));
        });

    }

    public void update(){
        try {
            int idstud = Integer.parseInt(idstudInput.getText());
            int idhead = Integer.parseInt(headidInput.getText());
            headStudentRepository.update(idstud, idhead);
        }catch (Exception e){
            modalWindow("Неправильно введены данные");
        }

    }

    public void delete(){
        int idstud = head_student.getSelectionModel().getSelectedItem().getStudent_id();
        int idhead = head_student.getSelectionModel().getSelectedItem().getHead_id();
        headStudentRepository.delete(idstud, idhead);
    }

    private void updateTable(List<Head_Student> list){

        ObservableList<Head_Student> head_students = FXCollections.observableArrayList(list);

        headid.setCellValueFactory(new PropertyValueFactory<>("head_id"));
        studentid.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        head_student.setItems(head_students);
    }

    public void modalWindow(String str) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oop_application/exception.fxml"));
        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        ExceptionController controller = loader.getController();
        controller.getMessage().setText(str);


        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    private void clearInput(){
        idstudInput.setText("");
        headidInput.setText("");
    }

    private void fillingInput(){
        headidInput.setText(head_student.getSelectionModel().getSelectedItem().getHead_id().toString());
        idstudInput.setText(head_student.getSelectionModel().getSelectedItem().getStudent_id().toString());
    }
}
