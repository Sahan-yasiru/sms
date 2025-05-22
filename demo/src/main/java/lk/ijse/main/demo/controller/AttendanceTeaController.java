package lk.ijse.main.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.main.demo.dto.DtoAttendenceTea;
import lk.ijse.main.demo.model.AttendTeaModel;

import java.awt.*;
import java.awt.Button;
import java.net.URL;
import java.util.ResourceBundle;

public class AttendanceTeaController implements Initializable {

    public javafx.scene.control.Label lblStatus;
    public javafx.scene.control.Label adminID;
    public Label attendanceID;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox cmbclassID;
    @FXML
    private RadioButton btnPresent;
    @FXML
    private RadioButton btnAbsent;
    @FXML
    private ComboBox cmbTeaID;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<DtoAttendenceTea, String> colAttendID,colDate,colAdminID,colTeacherID,colClassID;
    @FXML
    private TableColumn<DtoAttendenceTea, Boolean> colStatus;
    @FXML
    private TableColumn<String,String > colMark;
    private AttendTeaModel attendTeaModel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attendTeaModel=new AttendTeaModel();
        reLord();

    }

    public void clickPresent(ActionEvent event) {
    }

    public void clickAbsent(ActionEvent event) {
    }

    public void lordclassIDs(MouseEvent mouseEvent) {

    }

    public void lordStuIds(MouseEvent mouseEvent) {

    }

    public void boxClicked(MouseEvent mouseEvent) {
    }

    public void lordTable(){
        String [] columNames ={"attendID","date","adminID","teacherID","classID","status"};
        TableColumn [] columns={colAttendID,colDate,colAdminID,colTeacherID,colClassID,colStatus};
        for (int i = 0; i <columns.length; i++) {
            columns[i].setCellValueFactory(new PropertyValueFactory<>(columNames[i]));
        }
        Button button=new Button();
//        colMark.setCellValueFactory(new PropertyValueFactory<>("button"));
        try {
            tableView.setItems(attendTeaModel.lordTable());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void reLord(){
        lordTable();

    }
}
