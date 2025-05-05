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
    private TextField txtAdminIDBox2;
    @FXML
    private TextField txtUserNameBox2;
    @FXML
    private TextField txtPasswordBox2;
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
            labelAdminID.setText(idGenerator.getID("Admin_ID", "Admin"));

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
            new Alert(Alert.AlertType.INFORMATION, e.getMessage());
        }

    }

    public void adminUpdate(ActionEvent actionEvent) {
        try {
            dtoAdmin = new DtoAdmin(txtAdminIDBox2.getText(), txtUserNameBox2.getText(), txtPasswordBox2.getText());
            String result = settingsModel.AdminUpdate(dtoAdmin);
            new Alert(Alert.AlertType.INFORMATION, result).show();
            reLord();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void adminDelete(ActionEvent actionEvent) {
        try {
            String result = settingsModel.deleteAdmin(txtAdminIDBox2.getText());
            new Alert(Alert.AlertType.INFORMATION, result).show();
            reLord();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage());
        }


    }

    public void clear() {
        txtAdminIDBox2.clear();
        txtPassword.clear();
        txtPasswordBox2.clear();
        txtUserName.clear();
        txtUserNameBox2.clear();
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


}
