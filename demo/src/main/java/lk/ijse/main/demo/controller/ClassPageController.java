package lk.ijse.main.demo.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.main.demo.dto.DtoClass;
import lk.ijse.main.demo.dto.DtoStudent;
import lk.ijse.main.demo.getID.IDGenerator;
import lk.ijse.main.demo.model.ClassModel;
import lk.ijse.main.demo.model.StudetModel;
import lk.ijse.main.demo.model.TeacherModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ClassPageController implements Initializable {
    @FXML
    private ComboBox<Integer> cmbGrade;
    @FXML
    private ComboBox<String> cmbTimeTB,cmbSubjectID;
    @FXML
    private TableView<DtoClass> tableView;
    @FXML
    private Button UpdateButton, saveButton, btnClear, DeleteBtn;
    @FXML
    private TextField SearchBar;
    @FXML
    private Label lblClassID;
    @FXML
    private TableColumn<DtoClass, String> colClassID, colTimeTB, colSubjectID;
    @FXML
    private TableColumn<DtoStudent, Integer> colGrade;
    @FXML
    private Label lblNumber;
    private ClassModel classModel;
    private IDGenerator idGenerator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        classModel = new ClassModel();
        idGenerator = new IDGenerator();
        reLode();
        clear();
        btnClear.setOnAction(actionEvent -> {
            cmbTimeTB.setValue(null);
            SearchBar.clear();
            cmbGrade.setValue(null);
            cmbSubjectID.setValue(null);
        });
        searchSubject();
    }

    public void getID() {
        try {
            lblClassID.setText(idGenerator.getID("C", "Class_ID", "Class"));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnSave(ActionEvent actionEvent) {
        if (cmbGrade.getValue() == null || cmbSubjectID.getValue().isEmpty() || cmbTimeTB.getValue().isEmpty()) {
            ArrayList<Control> controls = new ArrayList<>(Arrays.asList(cmbTimeTB, cmbSubjectID, cmbGrade));
            controls.forEach(e -> {
                e.setStyle(e.getStyle() + " -fx-border-color: red;");
            });

        } else {
            try {
                String result= classModel.classSave(new DtoClass(lblClassID.getText(), cmbGrade.getValue(),cmbTimeTB.getValue(), cmbSubjectID.getValue()));
                new Alert(Alert.AlertType.INFORMATION, result).show();
                reLode();
                clear();
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }


    public void btnUpdate(ActionEvent actionEvent) {
        if (cmbGrade.getValue() == 0 || cmbSubjectID.getValue().isEmpty() || cmbTimeTB.getValue().isEmpty()) {
            ArrayList<Control> controls = new ArrayList<>(Arrays.asList(cmbTimeTB, cmbSubjectID, cmbGrade));
            controls.forEach(e -> {
                e.setStyle(e.getStyle() + " -fx-border-color: red;");
            });

        } else {
            try {
                String result= classModel.classUpdate(new DtoClass(lblClassID.getText(), cmbGrade.getValue(),cmbTimeTB.getValue(), cmbSubjectID.getValue()));
                new Alert(Alert.AlertType.INFORMATION, result).show();
                reLode();
                clear();
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    }

    public void btnDelete(ActionEvent actionEvent) {
        try {
            DtoClass dtoClass = new DtoClass();
            dtoClass.setClassID(lblClassID.getText());
            String result = classModel.deleteClass(dtoClass);
            new Alert(Alert.AlertType.INFORMATION, result).show();
            reLode();
            clear();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    public void lordTable() {
        colSubjectID.setCellValueFactory(new PropertyValueFactory<>("subjectID"));
        colTimeTB.setCellValueFactory(new PropertyValueFactory<>("timeTableID"));
        colClassID.setCellValueFactory(new PropertyValueFactory<>("classID"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));

        try {
            ObservableList<DtoClass> dtoClasses = classModel.getClassData();
            tableView.setItems(dtoClasses);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void reLode() {
        ArrayList<Control> controls = new ArrayList<>(Arrays.asList(cmbTimeTB, cmbSubjectID, cmbGrade));
        controls.forEach(e -> {
            e.setStyle(e.getStyle() + " -fx-border-color: Black;");
        });
        lordTable();
        getID();
        setNumber();
        saveButton.setDisable(false);
        UpdateButton.setDisable(true);
        DeleteBtn.setDisable(true);


    }

    public void clear() {
        SearchBar.clear();
        cmbGrade.setValue(null);
        cmbSubjectID.setValue(null);
        cmbTimeTB.setValue(null);
    }

    public void setNumber() {
        try {
            String result = classModel.getNumber();
            lblNumber.setText(result);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void reFreshTable(MouseEvent mouseEvent) {
        reLode();
    }

    public void searchSubject() {
        lordTable();
        FilteredList<DtoClass> filteredList = new FilteredList<>(tableView.getItems(), e -> true);
        SearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(dtoClass -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String filterText = newValue.toLowerCase();
                return dtoClass.getClassID().toLowerCase().contains(filterText) ||
                        dtoClass.getSubjectID().toLowerCase().contains(filterText) ||
                        dtoClass.getTimeTableID().toLowerCase().contains(filterText) ||
                        Integer.toString(dtoClass.getGrade()).toLowerCase().contains(filterText) ;
            });
        });
        SortedList<DtoClass> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

    }

    public void tableClicked(MouseEvent mouseEvent) {
        clear();
        DtoClass dtoClass = tableView.getSelectionModel().getSelectedItem();

        if (dtoClass != null) {
            saveButton.setDisable(true);
            UpdateButton.setDisable(false);
            DeleteBtn.setDisable(false);
            lblClassID.setText(dtoClass.getClassID());
            cmbSubjectID.setValue(dtoClass.getSubjectID());
            cmbGrade.setValue(dtoClass.getGrade());
            cmbSubjectID.setValue(dtoClass.getSubjectID());
            cmbTimeTB.setValue(dtoClass.getTimeTableID());
        }

    }

    public void lordGrade(MouseEvent mouseEvent) {
        ObservableList<Integer> grade = FXCollections.observableArrayList();
        for (int i = 1; i <= 13; i++) {
            grade.add(i);
        }

        cmbGrade.setItems(grade);
    }
    public void lordSubjectID(MouseEvent mouseEvent) {
        try {
            ObservableList<String> observableList=FXCollections.observableList(classModel.getSubjectIDs());
            cmbSubjectID.setItems(observableList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void lordTimeTBIDs(MouseEvent mouseEvent) {
        try {
            ObservableList<String> observableList=FXCollections.observableList(classModel.getTimeTBIDs());
            cmbTimeTB.setItems(observableList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
