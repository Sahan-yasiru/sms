package lk.ijse.main.demo.controller;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import lk.ijse.main.demo.dto.DtoAttendenceStu;
import lk.ijse.main.demo.getID.IDGenerator;
import lk.ijse.main.demo.model.AttendStuModel;
import lk.ijse.main.demo.toggleButton.ToggleSwitch;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AttendanceStuController implements Initializable {

    public Label lblStatus;
    public Label adminID;
    public Label attendanceID;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSave, btnUpdate, btnDelete;
    @FXML
    private AnchorPane pane;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> cmbclassID;
    @FXML
    private RadioButton btnPresent;
    @FXML
    private RadioButton btnAbsent;
    @FXML
    private ComboBox<String> cmbStuID;
    @FXML
    private TableView<DtoAttendenceStu> tableView;
    @FXML
    private TableColumn<DtoAttendenceStu, String> colAttendID, colDate, colAdminID, colStudentID, colClassID;
    @FXML
    private TableColumn<DtoAttendenceStu, ToggleSwitch> colStatus;
    private AttendStuModel  attendStuModel;
    private IDGenerator idGenerator = new IDGenerator();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        attendStuModel = new AttendStuModel();
        try {
            adminID.setText(attendStuModel.getAdminName(LoginController.getLabel()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        chanageDateFormat();
        lordCoboBoxes();
        setAttendLabel(true);
        reLord();
        teaSearch();

    }

    public void setAttendLabel(boolean b) {
        if (b) {
            btnPresent.setSelected(true);
            lblStatus.setStyle(lblStatus.getStyle() + "-fx-background-color: #4ED7F1;");
        } else {
            btnAbsent.setSelected(true);
            lblStatus.setStyle(lblStatus.getStyle() + "-fx-background-color: #FF8282;");
        }
        btnPresent.setOnAction(event -> {
            lblStatus.setStyle(lblStatus.getStyle() + "-fx-background-color: #4ED7F1;");
        });
        btnAbsent.setOnAction(event -> {
            lblStatus.setStyle(lblStatus.getStyle() + "-fx-background-color: #FF8282;");
        });

    }

    public void lordCoboBoxes() {
        try {
            cmbclassID.setItems(attendStuModel.lordClassIDS());
            cmbStuID.setItems(attendStuModel.lordStuIDs());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void chanageDateFormat() {
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
    }

    public void boxClicked(MouseEvent mouseEvent) {

    }

    public void lordTable() {
        String[] columNames = {"attendID", "date", "adminID", "StudentID", "classID"};
        TableColumn[] columns = {colAttendID, colDate, colAdminID, colStudentID, colClassID};
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
                    DtoAttendenceStu data = getTableView().getItems().get(getIndex());
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
            tableView.setItems(attendStuModel.loadTable());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void reLord() {
        btnDelete.setDisable(true);
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        try {
            attendanceID.setText(idGenerator.getID("AT", "Attend_ID", "Attendance_Stu"));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        lordTable();
        clear();


    }

    public void clear() {
        cmbclassID.setValue(null);
        cmbStuID.setValue(null);
        datePicker.setValue(null);

    }

    public void tableClicked(MouseEvent mouseEvent) {
        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
        DtoAttendenceStu dtoAttendenceStu = tableView.getSelectionModel().getSelectedItem();
        if (dtoAttendenceStu != null) {
            attendanceID.setText(dtoAttendenceStu.getAttendID());
            adminID.setText(dtoAttendenceStu.getAdminID());
            cmbStuID.setValue(dtoAttendenceStu.getStudentID());
            cmbclassID.setValue(dtoAttendenceStu.getClassID());
            datePicker.setValue(LocalDate.parse(dtoAttendenceStu.getDate()));
            if (dtoAttendenceStu.getStatus() == false) {
                setAttendLabel(false);
            } else {
                setAttendLabel(true);

            }
            try {
                System.out.println(dtoAttendenceStu);
                attendStuModel.setAttendance(dtoAttendenceStu.getAttendID(), dtoAttendenceStu.getToggleSwitch().getSwitchedOn());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void paneClicked(MouseEvent mouseEvent) {
        reLord();

    }

    public void updateAtted(ActionEvent actionEvent) {
        {
            if (datePicker.getValue() == null || cmbStuID.getValue() == null || cmbclassID.getValue() == null) {
                Control[] controls = {datePicker, cmbStuID, cmbclassID};
                for (Control control : controls) {
                    control.setStyle(cmbclassID.getStyle() + ";-fx-border-color: #CB0404;");
                }
            } else if (chackDate(datePicker.getValue())) {
                try {
                    String string = attendStuModel.updateAtted(new DtoAttendenceStu(attendanceID.getText(),
                            datePicker.getValue().toString(), adminID.getText(), cmbStuID.getValue(),
                            cmbclassID.getValue(), btnPresent.isSelected() ? new ToggleSwitch(true) : new ToggleSwitch(false)));
                    new Alert(Alert.AlertType.INFORMATION, string).show();

                } catch (Exception e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }

            } else {
                datePicker.setStyle(datePicker.getStyle() + ";-fx-border-color: #CB0404;");
                new Alert(Alert.AlertType.ERROR, "Enter Date Correct !").show();
            }
            reLord();


        }


    }

    public void deleteAttend(ActionEvent actionEvent) {
        try {
            String result = attendStuModel.deleteAttedStu(attendanceID.getText());
            new Alert(Alert.AlertType.INFORMATION, result).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        reLord();


    }

    public void saveAtted(ActionEvent actionEvent) {
        if (datePicker.getValue() == null || cmbStuID.getValue() == null || cmbclassID.getValue() == null) {
            Control[] controls = {datePicker, cmbStuID, cmbclassID};
            for (Control control : controls) {
                control.setStyle(cmbclassID.getStyle() + ";-fx-border-color: #CB0404;");
            }
        } else if (chackDate(datePicker.getValue())) {
            try {
                String string = attendStuModel.saveAttedStu(new DtoAttendenceStu(attendanceID.getText(),
                        datePicker.getValue().toString(), adminID.getText(), cmbStuID.getValue(),
                        cmbclassID.getValue(), btnPresent.isSelected() ? new ToggleSwitch(true) : new ToggleSwitch(false)));
                new Alert(Alert.AlertType.INFORMATION, string).show();

            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        } else {
            datePicker.setStyle(datePicker.getStyle() + ";-fx-border-color: #CB0404;");
            new Alert(Alert.AlertType.ERROR, "Enter Date Correct !").show();
        }
        reLord();


    }

    public void lordStudentIDs(MouseEvent mouseEvent) {
        try {
            cmbStuID.setItems(attendStuModel.lordStuIDs());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void lordClassIDs(MouseEvent mouseEvent) {
        try {
            cmbclassID.setItems(attendStuModel.lordClassIDS());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public boolean chackDate(LocalDate date) {
        return !date.isAfter(LocalDate.now());
    }

    public void teaSearch() {
        lordTable();
        FilteredList<DtoAttendenceStu> filterDate = new FilteredList<>(tableView.getItems(), e -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterDate.setPredicate(dto -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterText = newValue.toLowerCase();
                return dto.getAttendID().toLowerCase().contains(filterText) ||
                        dto.getClassID().toString().contains(filterText) ||
                        dto.getAdminID().toLowerCase().contains(filterText) ||
                        dto.getStudentID() .toLowerCase().contains(filterText) ||
                        dto.getDate().toLowerCase().contains(filterText);

            });
        });
        SortedList<DtoAttendenceStu> sortedList = new SortedList<>(filterDate);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

    }

}




