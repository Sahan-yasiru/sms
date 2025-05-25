package lk.ijse.main.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import lk.ijse.main.demo.dto.DtoAttendenceTea;
import lk.ijse.main.demo.model.AttendTeaModel;
import lk.ijse.main.demo.model.AttendanceStuModel;
import lk.ijse.main.demo.toggleButton.ToggleSwitch;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private TableView<DtoAttendenceTea> tableView;
    @FXML
    private TableColumn<DtoAttendenceTea, String> colAttendID, colDate, colAdminID, colTeacherID, colClassID;
    @FXML
    private TableColumn<DtoAttendenceTea, ToggleSwitch> colStatus;
    @FXML
    private TableColumn<DtoAttendenceTea, Void> colMark = new TableColumn<>("Mark");
    private AttendTeaModel attendTeaModel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            adminID.setText(AttendanceStuModel.getAdminName(LoginController.getLabel()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        chanageDateFormat();
        attendTeaModel = new AttendTeaModel();
        reLord();

    }
    public void chanageDateFormat(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date==null? "" :formatter.format(date);
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? LocalDate.parse(string, formatter ): null;
            }
        });
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

    public void lordTable() {
        String[] columNames = {"attendID", "date", "adminID", "teacherID", "classID"};
        TableColumn[] columns = {colAttendID, colDate, colAdminID, colTeacherID, colClassID};
        for (int i = 0; i < columns.length; i++) {
            columns[i].setCellValueFactory(new PropertyValueFactory<>(columNames[i]));
        }
        colStatus.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(ToggleSwitch item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    DtoAttendenceTea data = getTableView().getItems().get(getIndex());
                    if (data != null) {
                        System.out.println(data);
                        setGraphic(data.getToggleSwitch());
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });

        try {
            tableView.setItems(attendTeaModel.lordTable());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void reLord() {
        lordTable();


    }

    public void tableClicked(MouseEvent mouseEvent) {
        DtoAttendenceTea dtoAttendenceTea =  tableView.getSelectionModel().getSelectedItem();
        if (dtoAttendenceTea != null) {
            try {
                System.out.println(dtoAttendenceTea);
                attendTeaModel.setAttedance(dtoAttendenceTea.getAttendID(), dtoAttendenceTea.getToggleSwitch().getSwitchedOn());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void dragRow(MouseEvent mouseEvent) {


    }

}



