package com.example.oop_application.Controller;

import com.example.oop_application.Model.Context;
import com.example.oop_application.Model.Instruction;
import com.example.oop_application.Model.Student_Instruction;
import com.example.oop_application.Repository.Impl.InstructionRepositoryImpl;
import com.example.oop_application.Repository.InstructionRepository;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class InstructionController {

    InstructionRepository instructionRepository = new InstructionRepositoryImpl();

    @FXML
    public TextField studentIdInput;

    @FXML
    private AnchorPane addPanel;

    @FXML
    private Button back;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button cancel;

    @FXML
    private TableColumn<Instruction, LocalDate> dateOfIndication;

    @FXML
    private RadioMenuItem dateOfIndicationRadio;

    @FXML
    private TextField dateOfIndicationInput;

    @FXML
    private TextField daysToCompleteInput;


    @FXML
    private TextField findInput;

    @FXML
    private TableColumn<Instruction, Integer> id;

    @FXML
    private TextField idInput;

    @FXML
    private TableColumn<Instruction, String> instruction;

    @FXML
    private TableColumn<Instruction, Integer> daysToComplete;

    @FXML
    private TextField instructionInput;

    @FXML
    private RadioMenuItem instructionRadio;

    @FXML
    private Button submit;

    @FXML
    private TableView<Instruction> table;

    @FXML
    void initialize(){
        if(instructionRepository.getAllInstruction() != null)
            updateTable(instructionRepository.getAllInstruction());

        btnDelete.setOnAction(actionEvent -> {
            if(!table.getSelectionModel().isEmpty()) {
                delete();
                updateTable(instructionRepository.getAllInstruction());
            }
            else {
                modalWindow("Выберите строку, которую нужно удалить");
            }
        });

        cancel.setOnAction(action -> {
            clearInput();
            addPanel.setStyle("visibility: hidden;");
            idInput.setStyle("visibility: visible");
            studentIdInput.setStyle("visibility: visible");
        });

        btnAdd.setOnAction(actionEvent -> {
            addPanel.setStyle("visibility: visible;");

            submit.setOnAction(action -> {
                add();
                updateTable(instructionRepository.getAllInstruction());
            });
        });


        btnUpdate.setOnAction(actionEvent -> {
            if(!table.getSelectionModel().isEmpty()) {
                addPanel.setStyle("visibility: visible;");
                idInput.setStyle("visibility: hidden");
                studentIdInput.setStyle("visibility: hidden");
                fillingInput();

                submit.setOnAction(action -> {
                    update();
                    updateTable(instructionRepository.getAllInstruction());
                    addPanel.setStyle("visibility: hidden;");
                    idInput.setStyle("visibility: visible");
                    studentIdInput.setStyle("visibility: visible");
                    clearInput();
                });
            }
            else {
                modalWindow("Выберите строку, которую нужно обновить");
            }
        });


        findInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (instructionRadio.isSelected()) updateTable(instructionRepository.contentSearch(newValue));
            else if (dateOfIndicationRadio.isSelected()) updateTable(instructionRepository.dateOfIndicationSearch(newValue));
        });
    }

    private void update(){
        try {
            Instruction instruction = new Instruction();
            int id = table.getSelectionModel().getSelectedItem().getId();
            instruction.setId(Integer.valueOf(idInput.getText()));
            instruction.setContent(instructionInput.getText());
            instruction.setDateOfIndication(LocalDate.parse(dateOfIndicationInput.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            instruction.setDaysToComplete(Integer.valueOf(daysToCompleteInput.getText()));
            instructionRepository.updateInstructionById(id, instruction);
        }
        catch (Exception e){
            modalWindow("Неправильно введены данные");
        }
    }

    private void add() {

        try {
            if(instructionRepository.getInstructionById(Integer.parseInt(idInput.getText())) != null)
                modalWindow("Поручение с таким номером уже существует");

            Instruction instruction = new Instruction();
            instruction.setId(Integer.valueOf(idInput.getText()));
            instruction.setContent(instructionInput.getText());
            instruction.setDateOfIndication(LocalDate.parse(dateOfIndicationInput.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            instruction.setDaysToComplete(Integer.valueOf(daysToCompleteInput.getText()));

            Student_Instruction student_instruction = new Student_Instruction();
            student_instruction.setInstruction_id(instruction.getId());
            student_instruction.setStudent_id(Integer.valueOf(studentIdInput.getText()));
            instructionRepository.saveInstruction(instruction, student_instruction);
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


    private void updateTable(List<Instruction> list){

        ObservableList<Instruction> instructions = FXCollections.observableArrayList(list);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateOfIndication.setCellValueFactory(new PropertyValueFactory<>("dateOfIndication"));
        daysToComplete.setCellValueFactory(new PropertyValueFactory<>("daysToComplete"));
        instruction.setCellValueFactory(new PropertyValueFactory<>("content"));

        table.setItems(instructions);
    }

    private void delete(){
        instructionRepository.deleteInstructionById(table.getSelectionModel().getSelectedItem().getId());
    }

    private void clearInput(){
        idInput.setText("");
        instructionInput.setText("");
        dateOfIndicationInput.setText("");
        daysToCompleteInput.setText("");
        studentIdInput.setText("");
    }

    private void fillingInput(){
        idInput.setText(table.getSelectionModel().getSelectedItem().getId().toString());
        instructionInput.setText(table.getSelectionModel().getSelectedItem().getContent());
        dateOfIndicationInput.setText(table.getSelectionModel().getSelectedItem().getDateOfIndication().toString());
        daysToCompleteInput.setText(table.getSelectionModel().getSelectedItem().getDaysToComplete().toString());
    }
}
