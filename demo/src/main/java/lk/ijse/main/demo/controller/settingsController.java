package lk.ijse.main.demo.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import jdk.jfr.SettingControl;
import lk.ijse.main.demo.dto.DtoAdmin;
import lk.ijse.main.demo.getID.IDGenerator;
import lk.ijse.main.demo.model.AddUserModel;
import lk.ijse.main.demo.model.SettingsModel;

import java.net.URL;
import java.util.ResourceBundle;

public class settingsController implements Initializable {

    @FXML
    private AnchorPane anPane;
    @FXML
    private TableColumn<DtoAdmin, String> ColAdmin_ID;
    @FXML
    private TableColumn<DtoAdmin, String> colUser_Name;
    @FXML
    private TableColumn<DtoAdmin, String> colPassword;
    @FXML
    private TableView<DtoAdmin> tableView;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            settingsModel = new SettingsModel();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        ColAdmin_ID.setCellValueFactory(new PropertyValueFactory<>("adminID"));
        colUser_Name.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        reLord();
    }

    public void lordAdminID() {
        try {
            idGenerator = new IDGenerator();
            labelAdminID.setText(idGenerator.getID("A","Admin_ID", "Admin"));

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage());
            throw new RuntimeException();
        }
    }

    public void adminSave(ActionEvent actionEvent) {
        try {
            dtoAdmin = new DtoAdmin(labelAdminID.getText(), txtUserName.getText(), txtPassword.getText());
            addUserModel = new AddUserModel();
            String result = addUserModel.addUser(dtoAdmin);
            new Alert(Alert.AlertType.INFORMATION, result).show();
            reLord();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    public void adminUpdate(ActionEvent actionEvent) {
        try {
            dtoAdmin = new DtoAdmin(labelAdminID.getText(), txtUserName.getText(), txtPassword.getText());
            String result = settingsModel.AdminUpdate(dtoAdmin);
            new Alert(Alert.AlertType.INFORMATION, result).show();
            reLord();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void adminDelete(ActionEvent actionEvent) {
        System.out.println("hi");
        try {
            String result = settingsModel.deleteAdmin(labelAdminID.getText());
            new Alert(Alert.AlertType.INFORMATION, result).show();
            reLord();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage());
        }


    }

    public void clear() {
        txtPassword.clear();
        txtUserName.clear();
        SearchBar.clear();

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
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSave.setDisable(false);
    }

    public void lordTable() {
        try {
            ObservableList<DtoAdmin> dtoAdmins = settingsModel.lordTable();
            tableView.setItems(dtoAdmins);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void lordtableAgin(MouseEvent mouseEvent) {

        reLord();
    }

    public void searchUser(KeyEvent keyEvent) {
        try {
            ObservableList<DtoAdmin> dtoAdmins=settingsModel.searchUser(SearchBar.getText());
            tableView.setItems(dtoAdmins);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage());
        }
    }


    public void tableClicked(MouseEvent mouseEvent) {
        DtoAdmin dtoAdmin1=tableView.getSelectionModel().getSelectedItem();

        if(dtoAdmin1!= null){
            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            labelAdminID.setText(dtoAdmin1.getAdminID());
            txtUserName.setText(dtoAdmin1.getUserName());
            txtPassword.setText(dtoAdmin1.getPassword());
        }

    }
}
