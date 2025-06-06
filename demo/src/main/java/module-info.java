module lk.ijse.main.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.jfr;
    requires com.gluonhq.charm.glisten;
    requires mysql.connector.j;
    requires com.google.protobuf;
    requires java.mail;

    opens lk.ijse.main.demo.controller to javafx.fxml;
    opens lk.ijse.main.demo.dto to javafx.base;
    exports lk.ijse.main.demo;
    opens lk.ijse.main.demo.toggleButton to javafx.fxml;
}