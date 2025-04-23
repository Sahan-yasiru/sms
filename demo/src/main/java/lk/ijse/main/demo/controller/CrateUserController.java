package lk.ijse.main.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.main.demo.dto.DtoAdmin;
import lk.ijse.main.demo.getID.IDGenerator;
import lk.ijse.main.demo.model.AddUserModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CrateUserController implements Initializable {

    @FXML
    private PasswordField txtPassWold;
    @FXML
    private TextField txtUsername;
    @FXML
    private Label labelAdminID;
    private AddUserModel addUserModel;
    private IDGenerator idGenerator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lordID();
        clear();
    }

    public void lordID(){
        try {
            idGenerator=new IDGenerator();
            labelAdminID.setText(idGenerator.getID("Admin_ID","Admin"));
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void addUser(ActionEvent actionEvent)  {
        try {
            addUserModel =new AddUserModel();
            DtoAdmin dtoAdmin=new DtoAdmin(labelAdminID.getText(),txtUsername.getText(),txtPassWold.getText());
            String outPut=addUserModel.addUser(dtoAdmin);
            new Alert(Alert.AlertType.INFORMATION,outPut).show();
            lordID();
            clear();
        } catch (Exception e) {
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
           clear();

        }

    }
    public void clear(){
        txtPassWold.setText("");
        txtUsername.setText("");
    }
}
