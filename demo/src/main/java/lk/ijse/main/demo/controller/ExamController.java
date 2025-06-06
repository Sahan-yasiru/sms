package lk.ijse.main.demo.controller;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import lk.ijse.main.demo.dto.DtoAttendenceStu;
import lk.ijse.main.demo.dto.DtoExam;
import lk.ijse.main.demo.getID.IDGenerator;
import lk.ijse.main.demo.model.ExamModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class ExamController implements Initializable {

    @FXML
    private TextField SearchBar;
    @FXML
    private Button saveButton,DeleteBtn,UpdateButton,clearbutton;
    @FXML
    private javafx.scene.control.Label lblExmaID;
    @FXML
    private ComboBox<String> cmbSubjectIDs, cmbStudentIDs, cmbTeacherIDs;
    @FXML
    private TableView<DtoExam> tableView;
    @FXML
    private TableColumn<DtoExam, String> colSubjectID, colExamID, colStudentID, colExamDate, colTeacherID, colMarks;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField txtMaks;
    private ExamModel examModel;
    private IDGenerator idGenerator = new IDGenerator();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        examModel = new ExamModel();
        fixDate();
        reLorde();
        clear();

    }

    public void fixDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date == null ? "" : formatter.format(date);
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? LocalDate.parse(string, formatter) : null;
            }
        });
        datePicker.setEditable(false);
    }

    public void reLorde() {
        lordTable();
        DeleteBtn.setDisable(true);
        UpdateButton.setDisable(true);
        saveButton.setDisable(false);
        try {
            lblExmaID.setText(idGenerator.getID("EX", "Exam_ID", "Exam"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Control[] controls = {cmbSubjectIDs, cmbStudentIDs, cmbTeacherIDs, txtMaks, datePicker};
        for (int i = 0; i < controls.length; i++) {
            controls[i].setStyle(controls[i].getStyle() + ";-fx-border-color: black;");
        }
    }

    public void lordTable() {
        ArrayList<String> colNamesList = new ArrayList<>(List.of("subjectID", "examID", "studentID", "exmaDate", "teacherID", "marks"));
        ArrayList<TableColumn> tableColumnsList = new ArrayList<>(List.of(colSubjectID, colExamID, colStudentID, colExamDate, colTeacherID, colMarks));
        AtomicInteger index = new AtomicInteger(0);
        tableColumnsList.forEach(column -> {
            column.setCellValueFactory(new PropertyValueFactory<>(colNamesList.get(index.getAndIncrement())));
        });
        try {
            tableView.setItems(examModel.lordTable());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void reFreshTable(MouseEvent mouseEvent) {
        reLorde();

    }

    public void lordSubjectIDs(MouseEvent mouseEvent) {
        try {
            cmbSubjectIDs.setItems(examModel.getSubjectIDs());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void lordStudentIDs(MouseEvent mouseEvent) {
        try {
            cmbStudentIDs.setItems(examModel.getStudentIDs());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void lordTeacherIDs(MouseEvent mouseEvent) {
        try {
            cmbTeacherIDs.setItems(examModel.getTeacherIDs());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnDelete(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            if(alert.showAndWait().get()==ButtonType.OK){
                DtoExam dtoExam=new DtoExam();
                dtoExam.setExamID(lblExmaID.getText());
                String result=examModel.deleteExam(dtoExam);
                reLorde();
                clear();
            }
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnSave(ActionEvent actionEvent) {
        if (chackInseted()) {
            try {
                String result = examModel.saveExam(new DtoExam(lblExmaID.getText(), cmbSubjectIDs.getValue(), cmbStudentIDs.getValue(), datePicker.getValue().toString(), cmbTeacherIDs.getValue(), Integer.parseInt(txtMaks.getText())));
                new Alert(Alert.AlertType.INFORMATION, result).show();
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            reLorde();
            clear();
        }

    }
    public void clear(){
        txtMaks.clear();
        cmbSubjectIDs.setValue(null);
        cmbStudentIDs.setValue(null);
        cmbTeacherIDs.setValue(null);
        datePicker.setValue(null);


    }

    public boolean chackInseted() {
        boolean[] chack = {cmbSubjectIDs.getValue() == null, cmbStudentIDs.getValue() == null, cmbTeacherIDs.getValue() == null, txtMaks.getText() == null, datePicker.getValue() == null};
        Control[] controls = {cmbSubjectIDs, cmbStudentIDs, cmbTeacherIDs, txtMaks, datePicker};
        boolean flag = true;
        for (int i = 0; i < chack.length; i++) {
            if (chack[i]) {
                controls[i].setStyle(controls[i].getStyle() + ";-fx-border-color: #CB0404;");
                flag = false;
            }else {
                controls[i].setStyle(controls[i].getStyle() + ";-fx-border-color: black;");
            }
        }
        if (flag) {
            if (txtMaks.getText() != null) {
                try {
                    int marks = Integer.parseInt(txtMaks.getText());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    txtMaks.setStyle(txtMaks.getStyle() + ";-fx-border-color: #CB0404;");
                    flag = false;
                }
            }
        }
        return flag;
    }

    public void btnUpdate(ActionEvent actionEvent) {
        if (chackInseted()) {
            try {
                String result = examModel.updateExam(new DtoExam(lblExmaID.getText(), cmbSubjectIDs.getValue(), cmbStudentIDs.getValue(), datePicker.getValue().toString(), cmbTeacherIDs.getValue(), Integer.parseInt(txtMaks.getText())));
                new Alert(Alert.AlertType.INFORMATION, result).show();
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            reLorde();
            clear();
        }

    }

    public void btnClear(ActionEvent actionEvent) {
        clear();

    }

    public void tableClicked(MouseEvent mouseEvent) {
        DtoExam dtoExam=tableView.getSelectionModel().getSelectedItem();
        if(dtoExam!=null){
            DeleteBtn.setDisable(false);
            UpdateButton.setDisable(false);
            saveButton.setDisable(true);
            txtMaks.setText(dtoExam.getMarks()+"");
            cmbSubjectIDs.setValue(dtoExam.getSubjectID());
            cmbStudentIDs.setValue(dtoExam.getStudentID());
            cmbTeacherIDs.setValue(dtoExam.getTeacherID());
            datePicker.setValue(LocalDate.parse(dtoExam.getExmaDate()));
            lblExmaID.setText(dtoExam.getExamID());
        }
    }
    public void serchExam(KeyEvent keyEvent) {
        lordTable();
        FilteredList<DtoExam> filterDate = new FilteredList<>(tableView.getItems(), e -> true);
        SearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filterDate.setPredicate(dto -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterText = newValue.toLowerCase();
                return dto.getExamID().toLowerCase().contains(filterText) ||
                        dto.getExmaDate().contains(filterText) ||
                        dto.getSubjectID().toLowerCase().contains(filterText) ||
                        dto.getStudentID() .toLowerCase().contains(filterText) ||
                        dto.getTeacherID().toLowerCase().contains(filterText);



            });
        });
        SortedList<DtoExam> sortedList = new SortedList<>(filterDate);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

    }
}
