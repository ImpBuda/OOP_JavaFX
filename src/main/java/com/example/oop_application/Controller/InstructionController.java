package com.example.oop_application.Controller;

import com.example.oop_application.Model.Instruction;
import com.example.oop_application.Service.Impl.InstructionServiceImpl;
import com.example.oop_application.Service.InstructionService;
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

    private final InstructionService instructionService = new InstructionServiceImpl();

    static public String filepath;

    @FXML
    private AnchorPane addPanel;

    @FXML
    private Button back;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnOpen;

    @FXML
    private Button btnSave;

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

        back.setOnAction(actionEvent -> backToMenu());

        btnSave.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
            File file = fileChooser.showSaveDialog(btnSave.getScene().getWindow());
            if(file != null) {
                instructionService.saveFileSystem(file);
            }
        });

        btnOpen.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
            File file = fileChooser.showOpenDialog(btnOpen.getScene().getWindow());
            if (file != null) {
                filepath = file.getPath();
                updateTable(instructionService.getAllInstruction());
            }
        });

        btnDelete.setOnAction(actionEvent -> {
            if(!table.getSelectionModel().isEmpty()) {
                delete();
                updateTable(instructionService.getAllInstruction());
            }
            else {
                modalWindow("Выберите строку, которую нужно удалить");
            }
        });

        cancel.setOnAction(action -> {
            clearInput();
            addPanel.setStyle("visibility: hidden;");
        });

        btnAdd.setOnAction(actionEvent -> {
            addPanel.setStyle("visibility: visible;");

            submit.setOnAction(action -> add());
        });


        btnUpdate.setOnAction(actionEvent -> {
            if(!table.getSelectionModel().isEmpty()) {
                addPanel.setStyle("visibility: visible;");
                fillingInput();

                submit.setOnAction(action -> {
                    update();
                    updateTable(instructionService.getAllInstruction());
                    addPanel.setStyle("visibility: hidden;");
                    clearInput();
                });
            }
            else {
                modalWindow("Выберите строку, которую нужно обновить");
            }
        });


        findInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (instructionRadio.isSelected()) updateTable(instructionService.contentSearch(newValue));
            else if (dateOfIndicationRadio.isSelected()) updateTable(instructionService.dateOfIndicationSearch(newValue));
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
           instructionService.updateInstructionById(id, instruction);
        }
        catch (Exception e){
            modalWindow("Неправильно введены данные");
        }
    }

    private void add() {

        try {
            if(instructionService.getInstructionById(Integer.parseInt(idInput.getText())) != null){
                modalWindow("Студент с таким номером уже существует");
            }
            Instruction instruction = new Instruction();
            instruction.setId(Integer.valueOf(idInput.getText()));
            instruction.setContent(instructionInput.getText());
            instruction.setDateOfIndication(LocalDate.parse(dateOfIndicationInput.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            instruction.setDaysToComplete(Integer.valueOf(daysToCompleteInput.getText()));
            instructionService.saveInstruction(instruction);
            updateTable(instructionService.getAllInstruction());
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
        instructionService.deleteInstructionById(table.getSelectionModel().getSelectedItem().getId());
    }

    private void clearInput(){
        idInput.setText("");
        instructionInput.setText("");
        dateOfIndicationInput.setText("");
        daysToCompleteInput.setText("");
    }

    private void fillingInput(){
        idInput.setText(table.getSelectionModel().getSelectedItem().getId().toString());
        instructionInput.setText(table.getSelectionModel().getSelectedItem().getContent());
        dateOfIndicationInput.setText(table.getSelectionModel().getSelectedItem().getDateOfIndication().toString());
        daysToCompleteInput.setText(table.getSelectionModel().getSelectedItem().getDaysToComplete().toString());
    }

    private void backToMenu(){
        back.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader(HeadController.class.getResource("/com/example/oop_application/menu.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
