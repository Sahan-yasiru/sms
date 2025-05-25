package lk.ijse.main.demo.controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import lk.ijse.main.demo.dto.DtoAttendenceTea;
import lk.ijse.main.demo.getID.IDGenerator;
import lk.ijse.main.demo.model.AttendTeaModel;
import lk.ijse.main.demo.model.AttendanceStuModel;
import lk.ijse.main.demo.toggleButton.ToggleSwitch;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class AttendanceTeaController implements Initializable {

    public javafx.scene.control.Label lblStatus;
    public javafx.scene.control.Label adminID;
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
    private ComboBox<String> cmbTeaID;
    @FXML
    private TableView<DtoAttendenceTea> tableView;
    @FXML
    private TableColumn<DtoAttendenceTea, String> colAttendID, colDate, colAdminID, colTeacherID, colClassID;
    @FXML
    private TableColumn<DtoAttendenceTea, ToggleSwitch> colStatus;
    @FXML
    private TableColumn<DtoAttendenceTea, Void> colMark = new TableColumn<>("Mark");
    private AttendTeaModel attendTeaModel;
    private IDGenerator idGenerator = new IDGenerator();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        attendTeaModel = new AttendTeaModel();
        try {
            adminID.setText(AttendanceStuModel.getAdminName(LoginController.getLabel()));
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
            cmbclassID.setItems(attendTeaModel.lordClassIDS());
            cmbTeaID.setItems(attendTeaModel.lordTeaIDs());
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
            tableView.setItems(attendTeaModel.loadTable());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void reLord() {
        btnDelete.setDisable(true);
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        try {
            attendanceID.setText(idGenerator.getID("AT", "Attend_ID", "Attendance_Tea"));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        lordTable();
        clear();


    }

    public void clear() {
        cmbclassID.setValue(null);
        cmbTeaID.setValue(null);
        datePicker.setValue(null);

    }

    public void tableClicked(MouseEvent mouseEvent) {
        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
        DtoAttendenceTea dtoAttendenceTea = tableView.getSelectionModel().getSelectedItem();
        if (dtoAttendenceTea != null) {
            attendanceID.setText(dtoAttendenceTea.getAttendID());
            adminID.setText(dtoAttendenceTea.getAdminID());
            cmbTeaID.setValue(dtoAttendenceTea.getTeacherID());
            cmbclassID.setValue(dtoAttendenceTea.getClassID());
            datePicker.setValue(LocalDate.parse(dtoAttendenceTea.getDate()));
            if (dtoAttendenceTea.getStatus() == false) {
                setAttendLabel(false);
            } else {
                setAttendLabel(true);

            }
            try {
                System.out.println(dtoAttendenceTea);
                attendTeaModel.setAttendance(dtoAttendenceTea.getAttendID(), dtoAttendenceTea.getToggleSwitch().getSwitchedOn());
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
            if (datePicker.getValue() == null || cmbTeaID.getValue() == null || cmbclassID.getValue() == null) {
                Control[] controls = {datePicker, cmbTeaID, cmbclassID};
                for (Control control : controls) {
                    control.setStyle(cmbclassID.getStyle() + ";-fx-border-color: #CB0404;");
                }
            } else if (chackDate(datePicker.getValue())) {
                try {
                    String string = attendTeaModel.updateAtted(new DtoAttendenceTea(attendanceID.getText(),
                            datePicker.getValue().toString(), adminID.getText(), cmbTeaID.getValue(),
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
            String result = attendTeaModel.deleteAttedTea(attendanceID.getText());
            new Alert(Alert.AlertType.INFORMATION, result).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        reLord();


    }

    public void saveAtted(ActionEvent actionEvent) {
        if (datePicker.getValue() == null || cmbTeaID.getValue() == null || cmbclassID.getValue() == null) {
            Control[] controls = {datePicker, cmbTeaID, cmbclassID};
            for (Control control : controls) {
                control.setStyle(cmbclassID.getStyle() + ";-fx-border-color: #CB0404;");
            }
        } else if (chackDate(datePicker.getValue())) {
            try {
                String string = attendTeaModel.saveAttedTea(new DtoAttendenceTea(attendanceID.getText(),
                        datePicker.getValue().toString(), adminID.getText(), cmbTeaID.getValue(),
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

    public void lordTeacherIDs(MouseEvent mouseEvent) {
        try {
            cmbTeaID.setItems(attendTeaModel.lordTeaIDs());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void lordClassIDs(MouseEvent mouseEvent) {
        try {
            cmbclassID.setItems(attendTeaModel.lordClassIDS());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public boolean chackDate(LocalDate date) {
        return !date.isAfter(LocalDate.now());
    }

    public void teaSearch() {
        FilteredList<DtoAttendenceTea> filterDate = new FilteredList<>(tableView.getItems(), e -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterDate.setPredicate(dto -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterText = newValue.toLowerCase();
                return dto.getAttendID().toLowerCase().contains(filterText) ||
                        dto.getClassID().toString().contains(filterText) ||
                        dto.getAttendID().toLowerCase().contains(filterText) ||
                        dto.getAdminID().toLowerCase().contains(filterText) ||
                        dto.getDate().toLowerCase().contains(filterText);

            });
        });
        SortedList<DtoAttendenceTea> sortedList = new SortedList<>(filterDate);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

    }

}




