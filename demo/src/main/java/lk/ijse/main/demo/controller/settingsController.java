package lk.ijse.main.demo.controller;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import jdk.jfr.SettingControl;
import lk.ijse.main.demo.dto.DtoAdmin;
import lk.ijse.main.demo.getID.IDGenerator;
import lk.ijse.main.demo.model.AddUserModel;
import lk.ijse.main.demo.model.SettingsModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class settingsController implements Initializable {

    @FXML
    private AnchorPane anPane;
    @FXML
    private TableColumn<DtoAdmin, String> ColAdmin_ID;
    @FXML
    private ChoiceBox<String> cboxType;
    @FXML
    private TableColumn<DtoAdmin, String> colType;
    @FXML
    private TableColumn<DtoAdmin, String> colUser_Name;
    @FXML
    private TableColumn<DtoAdmin, String> colPassword;
    @FXML
    private TableView<DtoAdmin> tableView;
    @FXML
    private HBox hboxCurrentPass;
    @FXML
    private HBox hboxType;
    @FXML
    private HBox boxsaveandUpdate;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    @FXML
    private Label txtnum;
    @FXML
    private Label labelAdminID;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField SearchBar;
    private IDGenerator idGenerator;
    private AddUserModel addUserModel;
    private SettingsModel settingsModel;
    private DtoAdmin dtoAdmin;
    private static boolean adminType;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            settingsModel = new SettingsModel();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        chackAdminType();
        ColAdmin_ID.setCellValueFactory(new PropertyValueFactory<>("adminID"));
        colUser_Name.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colType.setCellValueFactory(new PropertyValueFactory<>("adminType"));
        reLord();
    }

    public void chackAdminType() {
        try {
            Boolean b = settingsModel.chackAdminType(LoginController.getLabel());
            if (b) {
                adminType = true;

            } else {
                adminType = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            adminType = false;
        }
    }

    public void lordAdminID() {
        try {
            idGenerator = new IDGenerator();
            labelAdminID.setText(idGenerator.getID("A", "Admin_ID", "Admin"));

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage());
            throw new RuntimeException();
        }
    }

    public void adminSave(ActionEvent actionEvent) {
        try {
            dtoAdmin = new DtoAdmin(labelAdminID.getText(), txtUserName.getText(), txtPassword.getText(), cboxType.getValue());
            addUserModel = new AddUserModel();
            String result = addUserModel.addUser(dtoAdmin);
            new Alert(Alert.AlertType.INFORMATION, result).show();
            reLord();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void adminUpdate(ActionEvent actionEvent) {

        try {
            if (labelAdminID.getText().isEmpty() || txtUserName.getText().isEmpty() || txtPassword.getText().isEmpty() || adminType == true ? cboxType.getValue() != null : true) {
                dtoAdmin = new DtoAdmin(labelAdminID.getText(), txtUserName.getText(), txtPassword.getText(), cboxType.getValue());
                String result = settingsModel.AdminUpdate(dtoAdmin, adminType);
                new Alert(Alert.AlertType.INFORMATION, result).show();
                reLord();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Records are empty").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void adminDelete(ActionEvent actionEvent) {
        try {
            String result = settingsModel.deleteAdmin(labelAdminID.getText());
            new Alert(Alert.AlertType.INFORMATION, result).show();
            reLord();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void clear() {
        txtUserName.clear();
        SearchBar.clear();
        txtPassword.clear();
        setNumber();
    }

    public void setNumber() {
        try {
            txtnum.setText(settingsModel.getNumberOfAdmin());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void reLord() {
        setNumber();
        lordTable();
        clear();
        lordAdminID();
        if (!adminType) {
            hboxType.setDisable(true);
            boxsaveandUpdate.setDisable(true);
        } else {
            boxsaveandUpdate.setDisable(false);
            hboxType.setDisable(false);
        }
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSave.setDisable(false);

    }

    public void lordTable() {
            try {
                ObservableList<DtoAdmin> dtoAdmins = settingsModel.lordTable(adminType);
                tableView.setItems(dtoAdmins);
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

    }

    public void lordtableAgin(MouseEvent mouseEvent) {
        reLord();
    }

    public void searchUser(KeyEvent keyEvent) {
        try {
            ObservableList<DtoAdmin> dtoAdmins = settingsModel.searchUser(SearchBar.getText());
            tableView.setItems(dtoAdmins);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage());
        }
    }


    public void tableClicked(MouseEvent mouseEvent) {
        DtoAdmin dtoAdmin1 = tableView.getSelectionModel().getSelectedItem();

        if (dtoAdmin1 != null) {
            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            labelAdminID.setText(dtoAdmin1.getAdminID());
            txtUserName.setText(dtoAdmin1.getUserName());
            try {
                txtPassword.setText(settingsModel.getpassowold(labelAdminID.getText()));
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            cboxType.setValue(dtoAdmin1.getAdminType());
        }

    }

    public void lordAdminTypes(MouseEvent mouseEvent) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.add("SuperAdmin");
        observableList.add("Admin");
        cboxType.setItems(observableList);

    }
}
