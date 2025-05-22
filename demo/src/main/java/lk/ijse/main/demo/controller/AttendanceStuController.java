package lk.ijse.main.demo.controller;

import com.gluonhq.charm.glisten.control.ToggleButtonGroup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import lk.ijse.main.demo.dto.DtoAttendenceStu;
import lk.ijse.main.demo.getID.IDGenerator;
import lk.ijse.main.demo.model.AttendanceStuModel;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AttendanceStuController implements Initializable {
    @FXML
    private DatePicker datePicker;
    @FXML
    private RadioButton btnPresent;
    @FXML
    private RadioButton btnAbsent;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSearch;
    @FXML
    private RadioButton SbtnAbsent;
    @FXML
    private RadioButton SbtnPresent;
    @FXML
    private ToggleButtonGroup btnToggle1;
    @FXML
    private Label SlblStatus;
    @FXML
    private ComboBox<String> sCmbclassID;
    @FXML
    private DatePicker sDatePicker;
    @FXML
    private ComboBox<String> scmbStuName;
    @FXML
    private ComboBox<String> scmbStuID;
    @FXML
    private Pane serachPane;

    @FXML
    private Label classWarning;
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
    private TableColumn<DtoAttendenceStu, String> tblAttendID, tblAdminID, tblDate, tblStuID, tblStuName, tblClassID;
    @FXML
    private TableColumn<DtoAttendenceStu, String> tblStatus;

    private LoginController loginController = new LoginController();
    private AttendanceStuModel attendanceStuModel;
    private IDGenerator idGenerator;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fixDate();
        idGenerator = new IDGenerator();
        attendanceStuModel = new AttendanceStuModel();
        try {
            adminID.setText(AttendanceStuModel.getAdminName(loginController.getLabel()));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        reLord();


    }

    public void fixDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DatePicker[] datePickers = {datePicker, sDatePicker};
        for (int i = 0; i < datePickers.length; i++) {
            datePickers[i].setConverter(new StringConverter<LocalDate>() {
                @Override
                public String toString(LocalDate date) {
                    return (date != null) ? formatter.format(date) : "";
                }

                @Override
                public LocalDate fromString(String string) {
                    return (string != null && !string.isEmpty()) ? LocalDate.parse(string, formatter) : null;
                }
            });
        }
    }


    public void getId() {
        try {
            attendanceID.setText(idGenerator.getID("AT", "Attend_ID", "Attendance_Stu"));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void reLord() {
        serachPane.setVisible(false);
        clearText();
        classWarning.setVisible(false);
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        clickPresent(new ActionEvent());
        btnPresent.setSelected(true);
        getId();
        datePicker.setValue(LocalDate.now());
        lordTable();
        setNumbers();


    }

    public void lordStuIds(MouseEvent mouseEvent) {
        lordStuIDsAndNames("M", "ID", this.cmbclassID);

    }

    public void lordStuNames(MouseEvent actionEvent) {
        lordStuIDsAndNames("M", "Name", this.cmbclassID);
    }

    public void saveAttend(ActionEvent actionEvent) {
        if (cmbStuID.getValue() != null && cmbStuName.getValue() != null && cmbclassID.getValue() != null && datePicker.getValue() != null && (btnPresent.isSelected() || btnAbsent.isSelected())) {
            try {
                if (!attendanceStuModel.chackAlreadyAttend(cmbStuID.getValue(), cmbclassID.getValue(), datePicker.getValue().toString())) {
                    String result = attendanceStuModel.saveStuAttend(new DtoAttendenceStu(attendanceID.getText(), datePicker.getEditor().getText(), adminID.getText(), cmbStuID.getValue(),
                            cmbStuName.getValue(), btnPresent.isSelected() ? true : false, cmbclassID.getValue()));
                    if (result.contains("your Date is invalid")) {
                        datePicker.setStyle(datePicker.getStyle() + " ;-fx-border-color: #CB0404;");
                    }
                    new Alert(Alert.AlertType.INFORMATION, result).show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Student Already Marked ").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        } else {
            if (cmbStuID.getValue() == null) {
                cmbStuID.setStyle(cmbStuID.getStyle() + " ;-fx-border-color: #CB0404;");
            }
            if (cmbStuName.getValue() == null) {
                cmbStuName.setStyle(cmbStuName.getStyle() + ";-fx-border-color: #CB0404;");
            }
            if (datePicker.getValue() == null) {
                datePicker.setStyle(datePicker.getStyle() + ";-fx-border-color: #CB0404;");
            }
            if (cmbclassID.getValue() == null) {
                cmbclassID.setStyle(cmbclassID.getStyle() + ";-fx-border-color: #CB0404;");
            }
        }
        reLord();
    }


    public void updateAttend(ActionEvent actionEvent) {
        try {
            String s = attendanceStuModel.updateAttendStu(new DtoAttendenceStu(attendanceID.getText(), datePicker.getValue().toString(), adminID.getText(), cmbStuID.getValue(), cmbStuName.getValue(), btnPresent.isSelected() ? true : false, cmbclassID.getValue()));
            new Alert(Alert.AlertType.INFORMATION, s).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        reLord();


    }

    public void deleteAttend(ActionEvent actionEvent) {
        try {
            String s = attendanceStuModel.deleteAttendStu(attendanceID.getText());
            new Alert(Alert.AlertType.INFORMATION, s).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        reLord();

    }

    public void clickPresent(ActionEvent actionEvent) {
        lblStatus.setStyle(lblStatus.getStyle() + ";-fx-background-color: #4ED7F1;");


    }

    public void clickAbsent(ActionEvent actionEvent) {
        lblStatus.setStyle(lblStatus.getStyle() + ";-fx-background-color: #FF8282;");

    }

    public void lordTable() {
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

    public void lordclassIDs(MouseEvent mouseEvent) {
        try {
            cmbclassID.setItems(attendanceStuModel.getclassIDs());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void rowClicked(MouseEvent mouseEvent) {
        DtoAttendenceStu dtoAttendenceStu = tableView.getSelectionModel().getSelectedItem();
        if (dtoAttendenceStu != null) {
            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            adminID.setText(dtoAttendenceStu.getAdminID());
            attendanceID.setText(dtoAttendenceStu.getAttendID());
            cmbclassID.setValue(dtoAttendenceStu.getClassID());
            cmbStuID.setValue(dtoAttendenceStu.getStudentID());
            cmbStuName.setValue(dtoAttendenceStu.getName());
            datePicker.setValue(LocalDate.parse(dtoAttendenceStu.getDate()));
            if (dtoAttendenceStu.getStatus() == true) {
                btnPresent.setSelected(true);
                clickPresent(new ActionEvent());
            } else {
                btnAbsent.setSelected(true);
                clickAbsent(new ActionEvent());
            }

        }

    }

    public void paneClicked(MouseEvent mouseEvent) {
        reLord();
    }

    public void boxClicked(MouseEvent mouseEvent) {
        reLord();
    }

    public void serchAttedStu(MouseEvent mouseEvent) {
        serachPane.setVisible(true);


    }

    public void setNumbers() {
        try {
            lblNumPresent.setText(attendanceStuModel.presentNumSet());
            lblNumAbsent.setText(attendanceStuModel.absentNumSet());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearText() {
        txtSerch.clear();
        cmbStuName.setValue(null);
        cmbStuID.setValue(null);

    }

    public void serachclickPresent(ActionEvent actionEvent) {
        SlblStatus.setStyle(lblStatus.getStyle() + ";-fx-background-color: #4ED7F1;");
    }

    public void serachclickAbsent(ActionEvent actionEvent) {
        SlblStatus.setStyle(lblStatus.getStyle() + ";-fx-background-color: #FF8282;");

    }

    public void btnSearch(ActionEvent actionEvent) {
        System.out.println("worked");
//        if (scmbStuID.getValue() != null && cmbStuName.getValue() != null && sCmbclassID.getValue() != null && sDatePicker.getValue() != null && (SbtnAbsent.isSelected() || SbtnPresent.isSelected())) {
            DtoAttendenceStu dtoAttendenceStu = new DtoAttendenceStu();
            dtoAttendenceStu.setStudentID(scmbStuID.getValue() != null ? scmbStuID.getValue() : null);
            dtoAttendenceStu.setName(scmbStuName.getValue() != null ? scmbStuName.getValue() : null);
            dtoAttendenceStu.setClassID(sCmbclassID.getValue() != null ? sCmbclassID.getValue() : null);
            dtoAttendenceStu.setDate(sDatePicker.getValue() != null ? sDatePicker.getValue().toString() : null);
            dtoAttendenceStu.setStatus(SbtnPresent.isSelected());
            try {
                tableView.setItems(attendanceStuModel.serchAttendStu(dtoAttendenceStu));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


    }

        public void SlordclassIDs (MouseEvent mouseEvent){
            try {
                sCmbclassID.setItems(attendanceStuModel.getclassIDs());
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }

        public void SlordStuNames (MouseEvent mouseEvent){
            lordStuIDsAndNames("s", "Name", this.sCmbclassID);
        }

        public void SlordStuIds (MouseEvent mouseEvent){
            lordStuIDsAndNames("s", "ID", this.sCmbclassID);

        }

        public void lordStuIDsAndNames (String type, String fetchDataType, ComboBox < String > classIDtype){
            if (classIDtype.getValue() != null) {
                ComboBox<String> comboBoxName = type.equals("s") ? scmbStuName : cmbStuName;
                ComboBox<String> comboBoxID = type.equals("s") ? scmbStuID : cmbStuID;

                try {
                    comboBoxName.setOnAction(null);
                    comboBoxID.setOnAction(null);

                    if (fetchDataType.equals("Name")) {
                        ObservableList<String> names = attendanceStuModel.getStuNames(classIDtype.getValue());
                        ObservableList<String> ids = attendanceStuModel.getIDs(classIDtype.getValue());

                        comboBoxName.setItems(names);
                        comboBoxID.setItems(ids);

                        comboBoxName.setOnAction(event -> {
                            String selectedName = comboBoxName.getValue();
                            if (selectedName != null) {
                                try {
                                    String studentID = attendanceStuModel.setAutoStuName(selectedName);
                                    if (comboBoxID.getItems().contains(studentID)) {
                                        comboBoxID.setValue(studentID);
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                                }
                            }
                        });

                    } else if (fetchDataType.equals("ID")) {
                        ObservableList<String> ids = attendanceStuModel.getIDs(classIDtype.getValue());
                        ObservableList<String> names = attendanceStuModel.getStuNames(classIDtype.getValue());

                        comboBoxID.setItems(ids);
                        comboBoxName.setItems(names);

                        comboBoxID.setOnAction(event -> {
                            String selectedID = comboBoxID.getValue();
                            if (selectedID != null) {
                                try {
                                    String studentName = attendanceStuModel.setAutoStuName(selectedID);
                                    if (comboBoxName.getItems().contains(studentName)) {
                                        comboBoxName.setValue(studentName);
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                                }
                            }
                        });
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }

            } else {
                classIDtype.setStyle(cmbclassID.getStyle() + ";-fx-border-color: #CB0404;");
                classWarning.setVisible(true);
            }
        }

    }