package lk.ijse.main.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable {

    @FXML
    private AnchorPane temp;
    @FXML
    private Button btnDashbord;
    @FXML
    private Label lblWelcome;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lblWelcome.setText(" Welcome "+LoginController.getLabel());
        lordPage("dashboradpage.fxml");
    }

    public void lordDashBord(ActionEvent actionEvent) {

        lordPage("dashboradpage.fxml");
    }

    public void lordPage(String location) {
        try {
            temp.getChildren().clear();
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/" + location));
            temp.getChildren().add(pane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }

    }

    public void lordSettings(ActionEvent actionEvent) {
        lordPage("settingpage.fxml");
    }


    public void lordStudent(ActionEvent actionEvent) {
        lordPage("subjectpage.fxml");
    }
    public void lordAttendance(ActionEvent actionEvent){
        lordPage("attendancepage.fxml");
    }
}
