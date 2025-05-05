package lk.ijse.main.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lk.ijse.main.demo.model.LoginPageModel;

public class LoginController  {

    public TextField txtuserName;
    public PasswordField txtPassword;
    public AnchorPane loginPane;
    private LoginPageModel loginPageModel;
    private static String userName;


    public void login(ActionEvent actionEvent){

        loginPageModel=new LoginPageModel();
        try {
            Boolean b = loginPageModel.chackLogin(txtuserName.getText(), txtPassword.getText());
            if (b) {
                userName=txtuserName.getText();
                lunchSidebar();
            } else {
                clear();
                new Alert(Alert.AlertType.ERROR, "Incorrect Password").show();

            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage());
            clear();
        }
    }

    public void clear(){
        txtPassword.setText("");
        txtuserName.setText("");
    }
    public void lunchSidebar(){
        try {
            Parent dashboardRoot = FXMLLoader.load(getClass().getResource("/view/sidebar.fxml"));
            Scene dashboardScene = new Scene(dashboardRoot);

            Stage stage = (Stage) loginPane.getScene().getWindow();
            stage.setScene(dashboardScene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.centerOnScreen();

        }catch (Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR,"page not found");
            alert.show();
        }


    }
    public void lordAddUser(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/crateuserpage.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(scene);
            scene.setFill(Color.TRANSPARENT);
            stage.centerOnScreen();
            stage.alwaysOnTopProperty();
            stage.show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage());
        }

    }
    public static String getLabel(){
        return userName;

    }
}
