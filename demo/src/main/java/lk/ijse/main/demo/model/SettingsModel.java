package lk.ijse.main.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.main.demo.db.DbController;
import lk.ijse.main.demo.dto.DtoAdmin;
import lk.ijse.main.demo.getID.IDGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SettingsModel {
    private Connection connection;

    public SettingsModel() throws SQLException {
        connection = DbController.getInstance().getConnection();
    }

    public String AdminUpdate(DtoAdmin dtoAdmin) {
        if (dtoAdmin.getPassword().isEmpty() || dtoAdmin.getAdminID().isEmpty() || dtoAdmin.getUserName().isEmpty()) {
            return "Recodes are Empty";
        }
        try {
            String sql = "UPDATE Admin SET User_name=?,Password=? WHERE Admin_ID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dtoAdmin.getUserName());
            preparedStatement.setString(2, dtoAdmin.getPassword());
            preparedStatement.setString(3, dtoAdmin.getAdminID());

            return preparedStatement.executeUpdate() > 0 ? "Success" : "Failed";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String deleteAdmin(String adminID) {
        if (adminID.isEmpty()) {
            return "Record is Empty";
        }
        try {
            String sql = "DELETE FROM Admin WHERE Admin_ID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, adminID);

            return preparedStatement.executeUpdate() > 0 ? "Success" : "Failed";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public String getNumberOfAdmin() {
        try {
            String sql = "SELECT COUNT(Admin_ID) AS Number_of FROM Admin";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            String result = "";
            if (resultSet.next()) {
                result = resultSet.getString("Number_of");
            }
            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public ObservableList<DtoAdmin> lordTable() throws SQLException {
        try {
            String sql = "SELECT * FROM Admin";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList<DtoAdmin> dtoAdmins = FXCollections.observableArrayList();

            while (resultSet.next()) {
                DtoAdmin dtoAdmin = new DtoAdmin(resultSet.getString("Admin_ID"), resultSet.getString("User_name"), resultSet.getString("Password"));
                dtoAdmins.add(dtoAdmin);
            }
            return dtoAdmins;
        } catch (Exception e) {
            throw new SQLException();
        }
    }

    public ObservableList<DtoAdmin> searchUser(String anything)  throws SQLException {
        String sql = "SELECT  * FROM Admin WHERE Admin_ID =? or User_name =? or Password=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, anything);
            preparedStatement.setString(2, anything);
            preparedStatement.setString(3, anything);

            ResultSet resultSet= preparedStatement.executeQuery();
            ObservableList<DtoAdmin> dtoAdmins = FXCollections.observableArrayList();

            while (resultSet.next()) {
                dtoAdmins.add(new DtoAdmin(resultSet.getString("Admin_ID"), resultSet.getString("User_name"), resultSet.getString("Password")));
            }
            return dtoAdmins;


    }
}
