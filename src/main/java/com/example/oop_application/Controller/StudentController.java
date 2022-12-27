package com.example.oop_application.Controller;

import com.example.oop_application.Model.*;
import com.example.oop_application.Repository.HeadRepository;
import com.example.oop_application.Repository.Impl.StudentRepositoryImpl;
import com.example.oop_application.Repository.StudentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Data
public class StudentController {


    StudentRepository studentRepository = new StudentRepositoryImpl();

    @FXML
    public TextField headIdInput;

    @FXML
    private ToggleGroup find;

    @FXML
    private TextField findInput;

    @FXML
    private RadioMenuItem nameRadio;

    @FXML
    private RadioMenuItem surnameRadio;

    @FXML
    private AnchorPane addPanel;

    @FXML
    private Button cancel;

    @FXML
    private TextField idInput;

    @FXML
    private TextField nameInput;

    @FXML
    private Button submit;

    @FXML
    private TextField surnameInput;

    @FXML
    private TextField instructionIdInput;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<Student> table;

    @FXML
    private TableColumn<Student, String> firstName;

    @FXML
    private TableColumn<Student, Integer> id;

    @FXML
    private TableColumn<Student, String> lastName;

    @FXML
    void initialize(){

            updateTable(studentRepository.getAllStudent());

            btnDelete.setOnAction(actionEvent -> {
                if(!table.getSelectionModel().isEmpty()) {
                    delete();
                    updateTable(studentRepository.getAllStudent());
                }
                else {
                    modalWindow("Выберите строку, которую нужно удалить");
                }
            });

            cancel.setOnAction(action -> {
                    clearInput();
                    addPanel.setStyle("visibility: hidden;");
                    idInput.setStyle("visibility: visible");
                    headIdInput.setStyle("visibility: visible");
            });

            btnAdd.setOnAction(actionEvent -> {
                    addPanel.setStyle("visibility: visible;");
                    submit.setOnAction(action -> {
                        add();
                        updateTable(studentRepository.getAllStudent());
                    });
            });


            btnUpdate.setOnAction(actionEvent -> {
                if(!table.getSelectionModel().isEmpty()) {
                        addPanel.setStyle("visibility: visible;");
                        idInput.setStyle("visibility: hidden");
                        headIdInput.setStyle("visibility: hidden");
                        fillingInput();

                        submit.setOnAction(action -> {
                            update();
                            updateTable(studentRepository.getAllStudent());
                            addPanel.setStyle("visibility: hidden;");
                            idInput.setStyle("visibility: visible");
                            headIdInput.setStyle("visibility: visible");
                            clearInput();
                        });
                }
                else {
                    modalWindow("Выберите строку, которую нужно обновить");
                }
            });


        findInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (nameRadio.isSelected()) updateTable(studentRepository.nameSearch(newValue));
            else if (surnameRadio.isSelected()) updateTable(studentRepository.surnameSearch(newValue));
        });
    }

    private void update(){
        try {
            Student student = new Student();
            int id = table.getSelectionModel().getSelectedItem().getId();
            student.setId(Integer.valueOf(idInput.getText()));
            student.setFirstName(nameInput.getText());
            student.setLastName(surnameInput.getText());
            studentRepository.updateStudentById(id, student);
        }
        catch (Exception e){
            modalWindow("Неправильно введены данные");
        }
    }

    private void add() {

        try {
            if(studentRepository.getStudentById(Integer.parseInt(idInput.getText())) != null)
                modalWindow("Студент с таким уже существует");
            Student student = new Student();
            student.setId(Integer.valueOf(idInput.getText()));
            student.setFirstName(nameInput.getText());
            student.setLastName(surnameInput.getText());

            Student_Instruction student_instruction = new Student_Instruction();
            student_instruction.setStudent_id(student.getId());
            student_instruction.setInstruction_id(Integer.valueOf(instructionIdInput.getText()));

            Head_Student head_student = new Head_Student();
            head_student.setStudent_id(student.getId());
            head_student.setHead_id(Integer.valueOf(headIdInput.getText()));
            studentRepository.saveStudent(student, head_student, student_instruction);
        }
        catch (Exception e) {
            modalWindow("Неправильно введены данные");
        }

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


    private void updateTable(List<Student> list){

        ObservableList<Student> students = FXCollections.observableArrayList(list);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        table.setItems(students);
    }

    private void delete(){
        studentRepository.deleteStudentById(table.getSelectionModel().getSelectedItem().getId());
    }

    private void clearInput(){
        idInput.setText("");
        nameInput.setText("");
        surnameInput.setText("");
        headIdInput.setText("");
    }

    private void fillingInput(){
        idInput.setText(table.getSelectionModel().getSelectedItem().getId().toString());
        nameInput.setText(table.getSelectionModel().getSelectedItem().getFirstName());
        surnameInput.setText(table.getSelectionModel().getSelectedItem().getLastName());
    }

}
