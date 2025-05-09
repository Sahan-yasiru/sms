package lk.ijse.main.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AttendanceMainPageController implements Initializable {

    @FXML
    private AnchorPane tempPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lordAttendPages("attendStuPage.fxml");
    }

    public void lordTeaAttend(ActionEvent actionEvent) {
            lordAttendPages("attendTeaPage.fxml");
    }

    public void lordStuAttend(ActionEvent actionEvent) {
            lordAttendPages("attendStuPage.fxml");
    }
    public void lordAttendPages(String location){
        try {
            System.out.println("lorded");
            tempPane.getChildren().clear();
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/" + location));
            tempPane.getChildren().add(pane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }
}
