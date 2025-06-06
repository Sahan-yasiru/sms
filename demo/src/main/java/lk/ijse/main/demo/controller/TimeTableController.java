package lk.ijse.main.demo.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import lk.ijse.main.demo.dto.DtoTimeTable;
import lk.ijse.main.demo.getID.IDGenerator;
import lk.ijse.main.demo.model.TimeTableModel;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class TimeTableController implements Initializable {
    @FXML
    private Label lblTBID;
    @FXML
    private ComboBox<String> cmbSubjectID, cmbDayofweek;
    @FXML
    private TextField txtstartTime, txtendTime;
    @FXML
    private TableView<DtoTimeTable> tableView;
    @FXML
    private TableColumn<DtoTimeTable, String> colTBID, colSubID, colStartTime, colEndTime, ColDay;
    @FXML
    private Button btnSave, btnUpdate, btnDelete, btnAdd;
    @FXML
    private HBox editBox;
    private TimeTableModel timeTableModel = new TimeTableModel();
    IDGenerator idGenerator = new IDGenerator();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editBox.setVisible(false);
        setActions();
        clear();
        reLord();

    }

    public void setActions() {
        btnSave.setOnAction(event -> {
            tableView.setFocusTraversable(false);
            tableView.setMouseTransparent(true);
            btnAdd.setText("Add");
            clear();
            saveTB();
        });
        btnUpdate.setOnAction(event -> {
            tableView.setFocusTraversable(true);
            tableView.setMouseTransparent(false);
            btnAdd.setText("Update");
            updateTB();
        });
        btnDelete.setOnAction(event -> {
            deleteTB();
        });
        cmbDayofweek.setItems(FXCollections.observableArrayList(List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")));
        try {
            cmbSubjectID.setItems(timeTableModel.getsubjectIDs());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteTB() {
        int selectIndex = cmbSubjectID.getSelectionModel().getSelectedIndex();
        if (selectIndex >= 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete the item ?");
            if(alert.showAndWait().get()==ButtonType.OK) {
                DtoTimeTable dtoTimeTable = tableView.getSelectionModel().getSelectedItem();
                if(dtoTimeTable!=null) {
                    try {
                        String b=timeTableModel.deleteTimeTB(dtoTimeTable);
                        new Alert(Alert.AlertType.INFORMATION,b,ButtonType.OK).show();
                        reLord();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        new Alert(Alert.AlertType.INFORMATION,e.getMessage(),ButtonType.OK).show();
                    }
                }
            }

        }
    }

    public void updateTB() {
        int selectIndex = cmbSubjectID.getSelectionModel().getSelectedIndex();
        if (selectIndex >= 0) {
            editBox.setVisible(true);
        }

    }

    public void reLord() {
        tableView.setFocusTraversable(true);
        tableView.setMouseTransparent(false);
        try {
            lblTBID.setText(idGenerator.getID("T", "Time_Table_ID", "Time_Table"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        lordTable();
        editBox.setVisible(false);
        clear();
    }

    public void saveTB() {
        editBox.setVisible(true);

    }

    public boolean chackEmpty() {
        Boolean flag = true;
        Pattern timePattern = Pattern.compile("(?i)^(1[0-2]|[1-9]):[0-5][0-9]\\s?$");
        Boolean[] chacks = {cmbSubjectID.getValue() == null, cmbDayofweek.getValue() == null, txtstartTime.getText().isEmpty(), txtendTime.getText().isEmpty()};
        Control[] controls = {cmbSubjectID, cmbDayofweek, txtstartTime, txtendTime};
        for (int i = 0; i < chacks.length; i++) {
            if (chacks[i]) {
                flag = false;
                controls[i].setStyle(controls[i].getStyle() + "-fx-border-color: #ff0000;");
            }
        }
        if (flag) {
            System.out.println(flag);
            boolean startValid = timePattern.matcher(txtstartTime.getText().trim()).matches();
            boolean endValid = timePattern.matcher(txtendTime.getText().trim()).matches();
            if (!(startValid && endValid)) {
                flag = false;
                System.out.println("iNSEIDE" + flag);
                txtstartTime.setStyle(txtstartTime.getStyle() + "-fx-border-color: #ff0000;");
                txtendTime.setStyle(txtendTime.getStyle() + "-fx-border-color: #ff0000;");
            }
        }
        return flag;
    }

    public void lordTable() {
        String[] colNames = {"timeTableID", "subjectID", "startTime", "endTime", "dayOfWeek"};
        TableColumn[] tableColumns = {colTBID, colSubID, colStartTime, colEndTime, ColDay};
        for (int i = 0; i < tableColumns.length; i++) {
            tableColumns[i].setCellValueFactory(new PropertyValueFactory<>(colNames[i]));
        }
        try {
            tableView.setItems(timeTableModel.getTimeTBData());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }

    }

    public void clear() {
        txtstartTime.setText("");
        txtendTime.setText("");
        cmbDayofweek.setValue(null);
        cmbSubjectID.setValue(null);
    }

    public void paneclicked(MouseEvent mouseEvent) {
        editBox.setVisible(false);
        reLord();
        tableView.getSelectionModel().clearSelection();

    }

    public void adddata(javafx.event.ActionEvent actionEvent) {
        if (chackEmpty()) {
            try {
                String result;
                if (btnAdd.getText().equals("Add")) {
                    result = timeTableModel.saveTimeTB(new DtoTimeTable(lblTBID.getText(), cmbSubjectID.getValue(), txtstartTime.getText(), txtendTime.getText(), cmbDayofweek.getValue(), null));
                } else {
                    result = timeTableModel.updateTimeTB(new DtoTimeTable(lblTBID.getText(), cmbSubjectID.getValue(), txtstartTime.getText(), txtendTime.getText(), cmbDayofweek.getValue(), null));
                }
                new Alert(Alert.AlertType.INFORMATION, result).show();
                reLord();
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
            }
        }
    }

    public void tableClicked(MouseEvent mouseEvent) {
        DtoTimeTable dtoTimeTable = tableView.getSelectionModel().getSelectedItem();
        if (dtoTimeTable != null) {
            lblTBID.setText(dtoTimeTable.getTimeTableID());
            cmbSubjectID.setValue(dtoTimeTable.getSubjectID());
            cmbDayofweek.setValue(dtoTimeTable.getDayOfWeek());
            txtstartTime.setText(dtoTimeTable.getStartTime());
            txtendTime.setText(dtoTimeTable.getEndTime());
        }
    }
}
