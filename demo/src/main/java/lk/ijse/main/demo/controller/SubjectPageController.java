package lk.ijse.main.demo.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.main.demo.dto.DtoSubject;
import lk.ijse.main.demo.getID.IDGenerator;
import lk.ijse.main.demo.model.SubjectPageModel;

import java.net.URL;
import java.util.ResourceBundle;

public class SubjectPageController implements Initializable {
    @FXML
    private TableView<DtoSubject> tableView;
    @FXML
    private TableColumn<DtoSubject,String> colSubjectID;
    @FXML
    private TableColumn<DtoSubject,String> colSubName;
    @FXML
    private Label lblNumber;
    @FXML
    private Label lblSubID;
    @FXML
    private TextField txtSubName;
    @FXML
    private TextField txtUpdateSubID;
    @FXML
    private TextField txtUpdateSubName;
    @FXML
    private  TextField SearchBar;

    private SubjectPageModel subjectPageModel;
    private IDGenerator idGenerator;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        subjectPageModel=new SubjectPageModel();
        idGenerator=new IDGenerator();
        reLode();


    }
    public void getID(){
        try {
            lblSubID.setText(idGenerator.getID("Subject_ID","Subject"));
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    public void btnSave(ActionEvent actionEvent) {
        try {
            String result=subjectPageModel.subjectSave(new DtoSubject(lblSubID.getText(),txtSubName.getText()));
            reLode();
            new Alert(Alert.AlertType.INFORMATION,result).show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }
    public void btnUpdate(ActionEvent actionEvent){
        try {
            String result=subjectPageModel.updateSubject(new DtoSubject(txtUpdateSubID.getText(),txtUpdateSubName.getText()));
            reLode();
            new Alert(Alert.AlertType.INFORMATION,result).show();

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    public void btnDelete(ActionEvent actionEvent) {
        try {
            String result=subjectPageModel.deleteSubject(new DtoSubject(txtUpdateSubID.getText(),null));
            reLode();
            new Alert(Alert.AlertType.INFORMATION,result).show();
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    public void lordTable(){
        colSubjectID.setCellValueFactory(new PropertyValueFactory<>("subjectID"));
        colSubName.setCellValueFactory(new PropertyValueFactory<>("Name"));

        try {
            ObservableList<DtoSubject> dtoSubjects =subjectPageModel.getSubjectData();
            tableView.setItems(dtoSubjects);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    public void reLode(){
        lordTable();
        clear();
        getID();
        setNumber();

    }
    public void clear(){
        txtSubName.clear();
        txtUpdateSubID.clear();
        txtUpdateSubName.clear();
        SearchBar.clear();

    }
    public void setNumber(){
        try {
            String result=subjectPageModel.getNumber();
            lblNumber.setText(result);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void reFreshTable(MouseEvent mouseEvent) {
        reLode();
    }

    public void searchSubject(KeyEvent keyEvent) {
        try {
            ObservableList<DtoSubject> dtoSubjects = subjectPageModel.searchSubject(SearchBar.getText());
            tableView.setItems(dtoSubjects);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }



    }
}
