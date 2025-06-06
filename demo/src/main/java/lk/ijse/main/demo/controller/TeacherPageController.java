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
import lk.ijse.main.demo.dto.DtoStudent;
import lk.ijse.main.demo.dto.DtoTeacher;
import lk.ijse.main.demo.getID.IDGenerator;
import lk.ijse.main.demo.model.StudetModel;
import lk.ijse.main.demo.model.TeacherModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class TeacherPageController implements Initializable {
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<String> cmbClassID;
    @FXML
    private ComboBox<Integer> cmbGrade;
    @FXML
    private ComboBox<String> cmbSubjectID;
    @FXML
    private TableView<DtoTeacher> tableView;
    ;
    @FXML
    private Button UpdateButton, saveButton, btnClear, DeleteBtn;
    @FXML
    private TextField SearchBar;
    @FXML
    private Label lblTeacherID;
    @FXML
    private TableColumn<DtoTeacher, String> colTeacherID, colSubjectID, colClassID, colTeaName;
    @FXML
    private TableColumn<DtoStudent, Integer> colGrade;
    @FXML
    private Label lblNumber;
    private TeacherModel teacherModel;
    private IDGenerator idGenerator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        teacherModel = new TeacherModel();
        idGenerator = new IDGenerator();
        reLode();
        clear();
        btnClear.setOnAction(actionEvent -> {
            txtName.clear();
            SearchBar.clear();
            cmbClassID.setValue(null);
            cmbGrade.setValue(null);
            cmbSubjectID.setValue(null);
        });
        searchSubject();
    }

    public void getID() {
        try {
            lblTeacherID.setText(idGenerator.getID("T", "Teacher_ID", "Teacher"));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnSave(ActionEvent actionEvent) {
        if (txtName.getText().isEmpty() || cmbClassID.getValue().isEmpty() || cmbGrade.getValue() == 0 || cmbSubjectID.getValue().isEmpty()) {
            ArrayList<Control> controls = new ArrayList<>(Arrays.asList(txtName, cmbSubjectID, cmbClassID, cmbGrade));
            controls.forEach(e -> {
                e.setStyle(e.getStyle() + " -fx-border-color: red;");
            });

        } else {
            try {
                String result=teacherModel.teacherSave(new DtoTeacher(lblTeacherID.getText(), cmbSubjectID.getValue(), txtName.getText(), cmbClassID.getValue(), cmbGrade.getValue()));
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
        if (txtName.getText().isEmpty() || cmbClassID.getValue().isEmpty() || cmbGrade.getValue() == 0 || cmbSubjectID.getValue().isEmpty()) {
            ArrayList<Control> controls = new ArrayList<>(Arrays.asList(txtName, cmbSubjectID, cmbClassID, cmbGrade));
            controls.forEach(e -> {
                e.setStyle(e.getStyle() + " -fx-border-color: red;");
            });

        } else {
            try {
                String result=teacherModel.teacherUpdate(new DtoTeacher(lblTeacherID.getText(), cmbSubjectID.getValue(), txtName.getText(), cmbClassID.getValue(), cmbGrade.getValue()));
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
            DtoTeacher dtoTeacher = new DtoTeacher();
            dtoTeacher.setTeacherID(lblTeacherID.getText());
            String result = teacherModel.deleteTea(dtoTeacher);
            new Alert(Alert.AlertType.INFORMATION, result).show();
            reLode();
            clear();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    public void lordTable() {
        colTeacherID.setCellValueFactory(new PropertyValueFactory<>("TeacherID"));
        colSubjectID.setCellValueFactory(new PropertyValueFactory<>("subjectID"));
        colTeaName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colClassID.setCellValueFactory(new PropertyValueFactory<>("classId"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("gradeAssign"));


        try {
            ObservableList<DtoTeacher> dtoTeachers = teacherModel.getTeacherData();
            tableView.setItems(dtoTeachers);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void reLode() {
        ArrayList<Control> controls = new ArrayList<>(Arrays.asList(txtName, cmbSubjectID, cmbClassID, cmbGrade));
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
        txtName.clear();
        SearchBar.clear();
        cmbClassID.setValue(null);
        cmbGrade.setValue(null);
        cmbSubjectID.setValue(null);
    }

    public void setNumber() {
        try {
            String result = teacherModel.getNumber();
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
        FilteredList<DtoTeacher> filteredList = new FilteredList<>(tableView.getItems(), e -> true);
        SearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(dtoTeacher -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String filterText = newValue.toLowerCase();
                return dtoTeacher.getName().toLowerCase().contains(filterText) ||
                        dtoTeacher.getSubjectID().toLowerCase().contains(filterText) ||
                        dtoTeacher.getName().toLowerCase().contains(filterText) ||
                        dtoTeacher.getClassId().toLowerCase().contains(filterText) ||
                        dtoTeacher.getTeacherID().toLowerCase().contains(filterText)||
                        Integer.toString(dtoTeacher.getGradeAssign()).toLowerCase().contains(filterText) ;
            });
        });
        SortedList<DtoTeacher> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

    }


    public void tableClicked(MouseEvent mouseEvent) {
        clear();
        DtoTeacher dtoTeacher = tableView.getSelectionModel().getSelectedItem();

        if (dtoTeacher != null) {
            saveButton.setDisable(true);
            UpdateButton.setDisable(false);
            DeleteBtn.setDisable(false);
            lblTeacherID.setText(dtoTeacher.getTeacherID());
            txtName.setText(dtoTeacher.getName());
            cmbSubjectID.setValue(dtoTeacher.getSubjectID());
            cmbGrade.setValue(dtoTeacher.getGradeAssign());
            cmbClassID.setValue(dtoTeacher.getClassId());
        }


    }

    public void lordClassID(MouseEvent mouseEvent) {
        try {
            StudetModel studetModel = new StudetModel();
            cmbClassID.setItems(studetModel.getclassIDs());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void lordGrade(MouseEvent mouseEvent) {
        ObservableList<Integer> grade = FXCollections.observableArrayList();
        for (int i = 1; i <= 13; i++) {
            grade.add(i);
        }

        cmbGrade.setItems(grade);
    }
    public void lordSubjectIDs(MouseEvent mouseEvent) {
        try {
            ObservableList<String> observableList=FXCollections.observableList(teacherModel.getSubjectIDs());
            cmbSubjectID.setItems(observableList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
