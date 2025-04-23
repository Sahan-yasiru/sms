package lk.ijse.main.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.main.demo.db.DbController;

import java.awt.image.DataBuffer;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main  extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent parent=FXMLLoader.load(getClass().getResource("/view/loginpage.fxml"));
        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}