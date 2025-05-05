package lk.ijse.main.demo.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.main.demo.dto.DtoTimeTable;
import lk.ijse.main.demo.model.DashboardModel;

import javax.swing.text.Element;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBordController implements Initializable {

    private DashboardModel dashboardModel;
    @FXML
    private Label lblDate;
    @FXML
    private Label txtNumStu;
    @FXML
    private Label txtNumTea;
    @FXML
    private Label txtNumSub;
    @FXML
    private Label txtNumClass;
    @FXML
    private TableView<DtoTimeTable> tableView;
    @FXML
    private TableColumn<DtoTimeTable,String> colDate;
    @FXML
    private TableColumn<DtoTimeTable,String> colSubject;
    @FXML
    private TableColumn<DtoTimeTable,String> colStartTime;
    @FXML
    private TableColumn<DtoTimeTable,String> colEndTime;
    @FXML
    private  ImageView imageView;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dashboardModel=new DashboardModel();
        txtNumStu.setText(dashboardModel.getNumber("Student_ID","Student"));
        txtNumSub.setText(dashboardModel.getNumber("Subject_ID","Subject"));
        txtNumTea.setText(dashboardModel.getNumber("Teacher_ID","Teacher"));
        txtNumClass.setText(dashboardModel.getNumber("Class_ID","Class"));
        lblDate.setText(dashboardModel.getDate());
        lordTable();

    }
    public void lordTable(){
        colDate.setCellValueFactory(new PropertyValueFactory<>("dayOfWeek"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        try {
            ObservableList<DtoTimeTable> dtoTimeTables = dashboardModel.getTimeDatableData();
            tableView.setItems(dtoTimeTables);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage());
        }
    }
}
