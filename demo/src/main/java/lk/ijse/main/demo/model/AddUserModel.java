package lk.ijse.main.demo.model;

import javafx.collections.ObservableList;
import lk.ijse.main.demo.db.DbController;
import lk.ijse.main.demo.dto.DtoAdmin;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddUserModel {
    private Connection connection;

    public String addUser(DtoAdmin dtoAdmin) {
        if (dtoAdmin.getAdminID().isEmpty() || dtoAdmin.getUserName().isEmpty() || dtoAdmin.getPassword().isEmpty()) {
            return "Records are Empty";
        } else {
            try {
                connection = DbController.getInstance().getConnection();
                String sql = "INSERT INTO Admin VALUES (?,?,?);";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, dtoAdmin.getAdminID());
                preparedStatement.setString(2, dtoAdmin.getUserName());
                preparedStatement.setString(3, dtoAdmin.getPassword());

                return preparedStatement.executeUpdate() > 0 ? "Success" : "failed";
            } catch (Exception e) {
                return e.getMessage();

            }
        }

    }
}
