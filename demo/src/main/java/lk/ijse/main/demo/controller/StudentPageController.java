package lk.ijse.main.demo.controller;

import com.google.protobuf.Internal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.main.demo.dto.DtoStudent;
import lk.ijse.main.demo.dto.DtoSubject;
import lk.ijse.main.demo.getID.IDGenerator;
import lk.ijse.main.demo.model.StudetModel;
import lk.ijse.main.demo.model.SubjectPageModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class StudentPageController implements Initializable {
    @FXML
    private TextField txtTelNo, txtStuName, txtAdress;
    @FXML
    private ComboBox<String> cmbClassID;
    @FXML
    private ComboBox<Integer> cmbGrade;
    @FXML
    private TableView<DtoStudent> tableView;
    @FXML
    private Button saveButton;
    @FXML
    private Button UpdateButton, deleteBtn;
    @FXML
    private Button DeleteBtn;
    @FXML
    private TextField SearchBar;
    @FXML
    private Label lblStudentID;
    @FXML
    private TableColumn<DtoStudent, String> colStudetID, colClassID, colName, colAddress;
    @FXML
    private TableColumn<DtoStudent, Integer> colGrade, colTelNo;
    @FXML
    private Label lblNumber;
    private StudetModel studetModel;
    private IDGenerator idGenerator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studetModel = new StudetModel();
        idGenerator = new IDGenerator();
        reLode();
        clear();
        deleteBtn.setOnAction(actionEvent -> {
            txtAdress.clear();
            SearchBar.clear();
            txtTelNo.clear();
            txtStuName.clear();
            cmbClassID.setValue(null);
            cmbGrade.setValue(null);
        });
        searchSubject();

    }

    public void getID() {
        try {
            lblStudentID.setText(idGenerator.getID("S", "Student_ID", "Student"));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnSave(ActionEvent actionEvent) {
        if (txtTelNo.getText().isEmpty() || txtStuName.getText().isEmpty() || txtAdress.getText().isEmpty() || cmbClassID.getValue().isEmpty() || cmbGrade.getValue() == 0) {
            ArrayList<Control> controls = new ArrayList<>(Arrays.asList(txtTelNo, txtStuName, txtAdress, cmbClassID, cmbGrade));
            controls.forEach(e -> {
                e.setStyle(e.getStyle() + " -fx-border-color: red;");
            });

        } else {
            Boolean b = false;
            Integer tel = 0;
            try {
                tel = Integer.parseInt(txtTelNo.getText());
                b = true;
                b= txtTelNo.getText().length() == 10;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (b) {
                try {
                    studetModel.studentSave(new DtoStudent(lblStudentID.getText(), tel, cmbClassID.getValue(), txtStuName.getText(), cmbGrade.getValue(), txtAdress.getText()));
                    reLode();
                    clear();
                } catch (SQLException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            } else {
                txtTelNo.setStyle(txtTelNo.getStyle() + " -fx-border-color: red;");
            }
        }
    }

    public void btnUpdate(ActionEvent actionEvent) {
        if (txtTelNo.getText().isEmpty() || txtStuName.getText().isEmpty() || txtAdress.getText().isEmpty() || cmbClassID.getValue().isEmpty() || cmbGrade.getValue() == 0) {
            ArrayList<Control> controls = new ArrayList<>(Arrays.asList(txtTelNo, txtStuName, txtAdress, cmbClassID, cmbGrade));
            controls.forEach(e -> {
                e.setStyle(e.getStyle() + " -fx-border-color: red;");
            });

        } else {
            Integer tel = 0;
            try {
                tel = Integer.parseInt(txtTelNo.getText());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                txtTelNo.setStyle(txtTelNo.getStyle() + " -fx-border-color: red;");

            }
            try {
                studetModel.updateStudent(new DtoStudent(lblStudentID.getText(), tel, cmbClassID.getValue(), txtStuName.getText(), cmbGrade.getValue(), txtAdress.getText()));
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
            DtoStudent dtoStudent = new DtoStudent();
            dtoStudent.setStudentID(lblStudentID.getText());
            String result = studetModel.deleteStu(dtoStudent);
            new Alert(Alert.AlertType.INFORMATION, result).show();
            reLode();
            clear();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    public void lordTable() {
        colStudetID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colTelNo.setCellValueFactory(new PropertyValueFactory<>("telNO"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colClassID.setCellValueFactory(new PropertyValueFactory<>("classID"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));


        try {
            ObservableList<DtoStudent> dtoStudents = studetModel.getStudentData();
            tableView.setItems(dtoStudents);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void reLode() {
        ArrayList<Control> controls = new ArrayList<>(Arrays.asList(txtTelNo, txtStuName, txtAdress, cmbClassID, cmbGrade));
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
            txtAdress.clear();
            SearchBar.clear();
            txtTelNo.clear();
            txtStuName.clear();
            cmbClassID.setValue(null);
            cmbGrade.setValue(null);
    }

    public void setNumber() {
        try {
            String result = studetModel.getNumber();
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
        FilteredList<DtoStudent> filteredList=new FilteredList<>(tableView.getItems(),e-> true);
        SearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(dtoStudent -> {
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String filterText=newValue.toLowerCase();
                return dtoStudent.getName().toLowerCase().contains(filterText)||
                        dtoStudent.getStudentID().toLowerCase().contains(filterText) ||
                        dtoStudent.getName().toLowerCase().contains(filterText)||
                        dtoStudent.getClassID().toLowerCase().contains(filterText) ||
                        Integer.toString(dtoStudent.getTelNO()).toLowerCase().contains(filterText) ||
                        Integer.toString(dtoStudent.getGrade()).toLowerCase().contains(filterText);
            });
        });
        SortedList<DtoStudent> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

    }


    public void tableClicked(MouseEvent mouseEvent) {
        clear();
        DtoStudent dtoStudent = tableView.getSelectionModel().getSelectedItem();

        if (dtoStudent != null) {
            saveButton.setDisable(true);
            UpdateButton.setDisable(false);
            DeleteBtn.setDisable(false);
            lblStudentID.setText(dtoStudent.getStudentID());
            txtStuName.setText(dtoStudent.getName());
            txtAdress.setText(dtoStudent.getAddress());
            txtTelNo.setText(dtoStudent.getTelNO() + "");
            cmbGrade.setValue(dtoStudent.getGrade());
            cmbClassID.setValue(dtoStudent.getClassID());
        }


    }

    public void lordClassID(MouseEvent mouseEvent) {
        try {
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
}
