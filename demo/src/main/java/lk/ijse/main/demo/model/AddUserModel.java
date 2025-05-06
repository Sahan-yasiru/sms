package lk.ijse.main.demo.model;

import javafx.collections.ObservableList;
import lk.ijse.main.demo.db.DbController;
import lk.ijse.main.demo.dto.DtoAdmin;
import lk.ijse.main.demo.util.CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUserModel {

    public String addUser(DtoAdmin dtoAdmin) throws SQLException {
        if (dtoAdmin.getAdminID().isEmpty() || dtoAdmin.getUserName().isEmpty() || dtoAdmin.getPassword().isEmpty()) {
            return "Records are Empty";
        } else {

                String sql = "INSERT INTO Admin VALUES (?,?,?);";
                Boolean b=CRUD.executeQuery(sql,dtoAdmin.getAdminID(),dtoAdmin.getUserName(),dtoAdmin.getPassword());

                return b == true ? "Success" : "failed";

        }

    }
}
