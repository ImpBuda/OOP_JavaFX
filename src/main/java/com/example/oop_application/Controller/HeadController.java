package com.example.oop_application.Controller;

import com.example.oop_application.Model.Context;
import com.example.oop_application.Model.Head;
import com.example.oop_application.Service.HeadService;
import com.example.oop_application.Service.Impl.HeadServiceImpl;
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
import java.util.ArrayList;
import java.util.List;

public class HeadController {
    private final HeadService headService = new HeadServiceImpl();

    @FXML
    public Button btnSave;

    @FXML
    public Button studentmodel;

    @FXML
    public Button instructionmodel;

    @FXML
    private TextField findInput;


    @FXML
    private RadioMenuItem nameRadio;

    @FXML
    private RadioMenuItem patronymicRadio;

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
    private TextField patronymicInput;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnOpen;

    @FXML
    private TableView<Head> table;

    @FXML
    private TableColumn<Head, String> firstName;

    @FXML
    private TableColumn<Head, Integer> id;

    @FXML
    private TableColumn<Head, String> patronymic;

    @FXML
    private TableColumn<Head, String> lastName;

    @FXML
    void initialize(){

        btnSave.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
            File file = fileChooser.showSaveDialog(btnSave.getScene().getWindow());
            if(file != null) {
                headService.saveFileSystem(file);
            }
        });

        studentmodel.setOnAction(actionEvent -> {
                loadStudentWindow();
        });

        instructionmodel.setOnAction(actionEvent -> loadInstructionWindow());

        btnOpen.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
            File file = fileChooser.showOpenDialog(btnOpen.getScene().getWindow());
            if (file != null) {
                Context.filepath = file.getPath();
                updateTable(headService.getAllHead());
            }
        });

        btnDelete.setOnAction(actionEvent -> {
            if(!table.getSelectionModel().isEmpty()) {
                delete();
                updateTable(headService.getAllHead());
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
                    updateTable(headService.getAllHead());
                    addPanel.setStyle("visibility: hidden;");
                    clearInput();
                });
            }
            else {
                modalWindow("Выберите строку, которую нужно обновить");
            }
        });


        findInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (nameRadio.isSelected()) updateTable(headService.nameSearch(newValue));
            else if (surnameRadio.isSelected()) updateTable(headService.surnameSearch(newValue));
            else if (patronymicRadio.isSelected()) updateTable(headService.patronymicSearch(newValue));
        });
    }

    private void update(){
        try {
            Head head = new Head();
            int id = table.getSelectionModel().getSelectedItem().getId();
            head.setId(Integer.valueOf(idInput.getText()));
            head.setFirstName(nameInput.getText());
            head.setLastName(surnameInput.getText());
            head.setPatronymic(patronymicInput.getText());
            head.setStudentList(headService.getHeadById(id).getStudentList());
            headService.updateHeadById(id, head);
        }
        catch (Exception e){
            modalWindow("Неправильно введены данные");
        }
    }

    private void add() {

        try {
            if(headService.getHeadById(Integer.parseInt(idInput.getText())) != null){
                modalWindow("Преподаватель с таким номером уже существует");
            }
            Head head = new Head();
            head.setId(Integer.valueOf(idInput.getText()));
            head.setFirstName(nameInput.getText());
            head.setLastName(surnameInput.getText());
            head.setPatronymic(patronymicInput.getText());
            head.setStudentList(new ArrayList<>());
            headService.saveHead(head);
            updateTable(headService.getAllHead());
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


    private void updateTable(List<Head> list){

        ObservableList<Head> heads = FXCollections.observableArrayList(list);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        patronymic.setCellValueFactory(new PropertyValueFactory<>("patronymic"));

        table.setItems(heads);
    }

    private void delete(){
        headService.deleteHeadById(table.getSelectionModel().getSelectedItem().getId());
    }

    private void clearInput(){
        idInput.setText("");
        nameInput.setText("");
        surnameInput.setText("");
    }

    private void fillingInput(){
        idInput.setText(table.getSelectionModel().getSelectedItem().getId().toString());
        nameInput.setText(table.getSelectionModel().getSelectedItem().getFirstName());
        surnameInput.setText(table.getSelectionModel().getSelectedItem().getLastName());
    }

    private void loadStudentWindow(){
        FXMLLoader loader = new FXMLLoader(HeadController.class.getResource("/com/example/oop_application/student.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        /*context.setStudentController(loader.getController());*/
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void loadInstructionWindow(){
        FXMLLoader loader = new FXMLLoader(HeadController.class.getResource("/com/example/oop_application/instruction.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        /*context.setInstructionController(loader.getController());*/
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
