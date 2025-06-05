package lk.ijse.main.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import lk.ijse.main.demo.dto.DtoExam;
import lk.ijse.main.demo.model.ExamModel;

import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class ExamController implements Initializable {

    @FXML
    private TextField SearchBar;
    @FXML
    private javafx.scene.control.Label lblExmaID;
    @FXML
    private ComboBox<String> cmbSubjectIDs,cmbStudentIDs,cmbTeacherIDs;
    @FXML
    private TableView<DtoExam> tableView;
    @FXML
    private TableColumn<DtoExam,String> colSubjectID,colExamID,colStudentID,colExamDate,colTeacherID,colMarks;
    @FXML
    private DatePicker datePicker;
    private ExamModel examModel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        examModel = new ExamModel();
        fixDate();
        lordTable();

    }
    public void fixDate(){
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
    public void lordTable(){
        String [] colNames = {"SubjectID","ExamID","StudentID","ClassID"};
    }

    public void reFreshTable(MouseEvent mouseEvent) {
    }

    public void lordSubjectIDs(MouseEvent mouseEvent) {
    }

    public void lordStudentIDs(MouseEvent mouseEvent) {
    }

    public void lordTeacherIDs(MouseEvent mouseEvent) {

    }

    public void btnDelete(ActionEvent actionEvent) {
    }

    public void btnSave(ActionEvent actionEvent) {
    }

    public void btnUpdate(ActionEvent actionEvent) {
    }

    public void btnClear(ActionEvent actionEvent) {

    }

    public void tableClicked(MouseEvent mouseEvent) {
    }
}
