module lk.ijse.main.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens lk.ijse.main.demo.controller to javafx.fxml;
    opens lk.ijse.main.demo.dto to javafx.base;
    exports lk.ijse.main.demo;
}