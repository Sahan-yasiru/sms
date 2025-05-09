package lk.ijse.main.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.main.demo.db.DbController;
import lk.ijse.main.demo.dto.DtoAdmin;
import lk.ijse.main.demo.getID.IDGenerator;
import lk.ijse.main.demo.util.CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SettingsModel {



    public String AdminUpdate(DtoAdmin dtoAdmin) throws SQLException {
        if (dtoAdmin.getPassword().isEmpty() || dtoAdmin.getAdminID().isEmpty() || dtoAdmin.getUserName().isEmpty()) {
            return "Recodes are Empty";
        }else {

            String sql = "UPDATE Admin SET User_name=?,Password=? WHERE Admin_ID=?";
            Boolean b=CRUD.executeQuery(sql,dtoAdmin.getUserName(),dtoAdmin.getPassword(),dtoAdmin.getAdminID());

            return b==true ? "Success" : "Failed";
        }
    }

    public String deleteAdmin(String adminID) throws SQLException {
        if (adminID.isEmpty()) {
            return "Record is Empty";
        }else {
            String sql = "DELETE FROM Admin WHERE Admin_ID=?";
            Boolean b = CRUD.executeQuery(sql, adminID);
            return b == true ? "Success" : "Failed";
        }
    }

    public String getNumberOfAdmin() throws SQLException {
            String sql = "SELECT COUNT(Admin_ID) AS Number_of FROM Admin";

            ResultSet resultSet = CRUD.executeQuery(sql);
            String result = "";
            if (resultSet.next()) {
                result = resultSet.getString("Number_of");
            }
            return result;

    }

    public ObservableList<DtoAdmin> lordTable() throws SQLException {

            String sql = "SELECT * FROM Admin";
            ResultSet resultSet = CRUD.executeQuery(sql);
            ObservableList<DtoAdmin> dtoAdmins = FXCollections.observableArrayList();

            while (resultSet.next()) {
                DtoAdmin dtoAdmin = new DtoAdmin(resultSet.getString("Admin_ID"), resultSet.getString("User_name"), resultSet.getString("Password"));
                dtoAdmins.add(dtoAdmin);
            }
            return dtoAdmins;
    }

    public ObservableList<DtoAdmin> searchUser(String anything)  throws SQLException {
        String sql = "SELECT  * FROM Admin WHERE Admin_ID =? or User_name =? or Password=?";

            ResultSet resultSet= CRUD.executeQuery(sql,anything,anything,anything);
            ObservableList<DtoAdmin> dtoAdmins = FXCollections.observableArrayList();

            while (resultSet.next()) {
                dtoAdmins.add(new DtoAdmin(resultSet.getString("Admin_ID"), resultSet.getString("User_name"), resultSet.getString("Password")));
            }
            return dtoAdmins;


    }
}
