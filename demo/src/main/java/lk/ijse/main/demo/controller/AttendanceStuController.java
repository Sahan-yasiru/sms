package lk.ijse.main.demo.controller;

import com.gluonhq.charm.glisten.control.ToggleButtonGroup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import lk.ijse.main.demo.dto.DtoAttendenceStu;
import lk.ijse.main.demo.getID.IDGenerator;
import lk.ijse.main.demo.model.AttendanceStuModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AttendanceStuController implements Initializable {
    public DatePicker datePicker;
    public RadioButton btnPresent;
    public RadioButton btnAbsent;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;


    @FXML
    private Label lblNumPresent;
    @FXML
    private Label lblNumAbsent;
    @FXML
    private TextField txtSerch;
    @FXML
    private ComboBox<String> cmbStuName;
    @FXML
    private ComboBox<String> cmbclassID;
    @FXML
    private ComboBox<String> cmbStuID;
    @FXML
    private Label attendanceID;
    @FXML
    private Label adminID;
    @FXML
    private Label lblStatus;
    @FXML
    private ToggleButtonGroup btnToggle;
    @FXML
    private TableView<DtoAttendenceStu> tableView;
    @FXML
    private TableColumn<DtoAttendenceStu,String> tblAttendID,tblAdminID,tblDate,tblStuID,tblStuName,tblClassID;
    @FXML
    private TableColumn<DtoAttendenceStu,String> tblStatus;

    private LoginController loginController = new LoginController();
    private AttendanceStuModel attendanceStuModel;
    private IDGenerator idGenerator;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? formatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? LocalDate.parse(string, formatter) : null;
            }
        });
        idGenerator = new IDGenerator();
        attendanceStuModel = new AttendanceStuModel();
        try {
            adminID.setText(attendanceStuModel.getAdminName(loginController.getLabel()));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        reLord();


    }

    public void getId() {
        try {
            attendanceID.setText(idGenerator.getID("AT", "Attend_ID", "Attendance_Stu"));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void reLord() {
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        clickPresent(new ActionEvent());
        btnPresent.setSelected(true);
        getId();
        datePicker.setValue(LocalDate.now());
        lordTable();
        setNumbers();
        clear();

    }

    public void lordStuIds(MouseEvent actionEvent) {
        try {
            cmbStuID.setItems(attendanceStuModel.getIDs());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void lordStuNames(MouseEvent actionEvent) {
        try {
            cmbStuName.setItems(attendanceStuModel.getStuNames());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void saveAttend(ActionEvent actionEvent) {
        if (cmbStuID.getValue() != null && cmbStuName.getValue() != null &&cmbclassID.getValue()!=null&& datePicker.getValue() != null && (btnPresent.isSelected() || btnAbsent.isSelected())) {
            try {

                String result = attendanceStuModel.saveStuAttend(new DtoAttendenceStu(attendanceID.getText(), datePicker.getEditor().getText(), adminID.getText(), cmbStuID.getValue(),
                        cmbStuName.getValue(), btnPresent.isSelected() ? true : false,cmbclassID.getValue()));
                new Alert(Alert.AlertType.INFORMATION, result).show();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        } else {
            if(cmbStuID.getValue()==null){
                cmbStuID.setStyle(cmbStuID.getStyle()+" ;-fx-border-color: #CB0404;");
            }
            if (cmbStuName.getValue()==null){
                cmbStuName.setStyle(cmbStuName.getStyle() +";-fx-border-color: #CB0404;");
            }
            if(datePicker.getValue()==null){
                datePicker.setStyle(datePicker.getStyle() +";-fx-border-color: #CB0404;");
            }
            if(cmbclassID.getValue()==null){
                cmbclassID.setStyle(datePicker.getStyle() +";-fx-border-color: #CB0404;");
            }
        }
        reLord();


    }

    public void updateAttend(ActionEvent actionEvent) {
        try {
            String s = attendanceStuModel.updateAttendStu(new DtoAttendenceStu(attendanceID.getText(), datePicker.getValue().toString(), adminID.getText(), cmbStuID.getValue(), cmbStuName.getValue(), btnPresent.isSelected() ? true : false, cmbclassID.getValue()));
            new Alert(Alert.AlertType.INFORMATION,s).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        reLord();


    }

    public void deleteAttend(ActionEvent actionEvent) {
        try {
            String s=attendanceStuModel.deleteAttendStu(attendanceID.getText());
            new Alert(Alert.AlertType.INFORMATION,s).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        reLord();

    }
    public void clear(){
        txtSerch.clear();

    }

    public void clickPresent(ActionEvent actionEvent) {
        lblStatus.setStyle(lblStatus.getStyle() + ";-fx-background-color: #4ED7F1;");


    }

    public void clickAbsent(ActionEvent actionEvent) {
        lblStatus.setStyle(lblStatus.getStyle() + ";-fx-background-color: #FF8282;");

    }
    public void lordTable(){
        tblAdminID.setCellValueFactory(new PropertyValueFactory<>("adminID"));
        tblAttendID.setCellValueFactory(new PropertyValueFactory<>("attendID"));
        tblDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        tblStuID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        tblStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tblStuName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblClassID.setCellValueFactory(new PropertyValueFactory<>("classID"));
        try {
            tableView.setItems(attendanceStuModel.lordTable());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public void lordclassIDs(MouseEvent mouseEvent){
        try {
            cmbclassID.setItems(attendanceStuModel.getclassIDs());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }
    public void rowClicked(MouseEvent mouseEvent){
        DtoAttendenceStu dtoAttendenceStu=tableView.getSelectionModel().getSelectedItem();
        if(dtoAttendenceStu != null){
            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            adminID.setText(dtoAttendenceStu.getAdminID());
            attendanceID.setText(dtoAttendenceStu.getAttendID());
            cmbclassID.setValue(dtoAttendenceStu.getClassID());
            cmbStuID.setValue(dtoAttendenceStu.getStudentID());
            cmbStuName.setValue(dtoAttendenceStu.getName());
            datePicker.setValue(LocalDate.parse(dtoAttendenceStu.getDate()));
            if(dtoAttendenceStu.getStatus()==true){
                btnPresent.setSelected(true);
                clickPresent(new ActionEvent());
            }else {
                btnAbsent.setSelected(true);
                clickAbsent(new ActionEvent());
            }

        }

    }
    public void paneClicked(MouseEvent mouseEvent){
        reLord();
    }
    public void boxClicked(MouseEvent mouseEvent){
        reLord();
    }
    public void serchAttedStu(KeyEvent keyEvent){
        try {
            tableView.setItems(attendanceStuModel.serchAttendStu( txtSerch.getText()));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }
    public void setNumbers(){
        try {
            lblNumPresent.setText(attendanceStuModel.presentNumSet());
            lblNumAbsent.setText(attendanceStuModel.absentNumSet());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}